package com.myomi.membership.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.myomi.board.vo.BoardVo;
import com.myomi.exception.FindException;
import com.myomi.membership.vo.MembershipVo;
import com.myomi.resource.Factory;

public class MembershipDAOOracle implements MembershipDAO {
	private SqlSessionFactory sqlSessionFactory;
	public MembershipDAOOracle() {
		sqlSessionFactory = Factory.getSqlSessionFactory();
	}
	
	@Override
	public List<MembershipVo> selectAll() throws FindException {
		SqlSession session = sqlSessionFactory.openSession();

		List<MembershipVo> list = session.selectList("MembershipMapper.selectAll");   
		
		if(list == null) {
			System.out.println("조회결과 없음");
		} else {
			for(MembershipVo mVo : list) {
				System.out.println(mVo.toString());
			}
		}
		session.close();
		return list;   
	}

	
	public static void main(String[] args) throws FindException{
		MembershipDAOOracle dao = new MembershipDAOOracle();
       
		dao.selectAll();
	
	}

}