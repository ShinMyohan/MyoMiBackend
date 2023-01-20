package com.myomi.point.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.myomi.exception.FindException;
import com.myomi.resource.Factory;
import com.myomi.review.dao.ReviewDAOOracle;

public class PointDAOOracle implements PointDao {
	private SqlSessionFactory sqlSessionFactory;
	public PointDAOOracle() {
		sqlSessionFactory = Factory.getSqlSessionFactory();
	}
	@Override
	public List<Map<String, Object>> selectPoint(String userId) throws FindException {
		// TODO Auto-generated method stub
		// 1. 데이터 베이스 접속
		SqlSession session = sqlSessionFactory.openSession();

		List<Map<String, Object>> list = session.selectList("PointMapper.selectPoint", userId);      
		
		// 3. 결과 판별
		if(list == null) {
			System.out.println("조회결과 없음");
		} else {
			for(Map<String, Object> review : list) {
				System.out.println(review.toString());
			}
		}
		
		// 4. DB 접속 해제
		session.close();
		return list; 
	}
	public static void main(String[] args) throws FindException{
		PointDAOOracle dao = new PointDAOOracle();
		//dao.selectOneReview(1);
		//dao.insertReview(0, 6, "user3", 3, "이게맞나2", "이게 맞아2", new Date(), 3);
	
		dao.selectPoint("user1");
	}
}
