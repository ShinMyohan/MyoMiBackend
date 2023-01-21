package com.myomi.coupon.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.myomi.coupon.vo.CouponVo;
import com.myomi.exception.FindException;
import com.myomi.resource.Factory;
import com.myomi.user.vo.UserVo;

public class CouponDAOOracle implements CouponDAO {
	private SqlSessionFactory sqlSessionFactory;
	public CouponDAOOracle() {
		sqlSessionFactory = Factory.getSqlSessionFactory();
	}
	
	@Override
	public List<Map<String,Object>> selectCouponByUserId(String userId) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> couponList
					= session.selectList("CouponMapper.selectCouponByUserId", userId);
		
		if(couponList == null) {
			System.out.println("조회결과 없음");
		} else {
			for(Map<String, Object> coupon : couponList) {
				System.out.println("coupon dao" + coupon);
			}
		}
		session.close();
		return couponList;
	}
	
	// 쿠폰 사용 또는 만료됐을 경우 status Update
	@Override
	public void updateCouponStatus(CouponVo cVo) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		session.update("CouponMapper.updateCouponStatus", cVo);
		System.out.println(cVo);
		session.commit();
		session.close();
	}


	public static void main(String[] Args) throws FindException {
		CouponDAOOracle dao = new CouponDAOOracle();
//		dao.selectOrderByUserId("user2");

		// 쿠폰 업데이트
		CouponVo cVo = new CouponVo();
		UserVo uVo = new UserVo();
		uVo.setId("user1");
		cVo.setUser(uVo);
		cVo.setNum(1);
		cVo.setStatus(2);
		dao.updateCouponStatus(cVo);
	}
}