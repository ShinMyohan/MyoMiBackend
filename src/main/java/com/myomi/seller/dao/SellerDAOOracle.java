package com.myomi.seller.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.myomi.exception.AddException;
import com.myomi.resource.Factory;
import com.myomi.seller.vo.SellerVo;

public class SellerDAOOracle implements SellerDAO {
	private SqlSessionFactory sqlSessionFactory;
	
	public SellerDAOOracle() {
		sqlSessionFactory = Factory.getSqlSessionFactory();
	}

	// uVo 를 서비스에서 넣으면 id뿐만 아니라 전체 값이 들어와서 따로 설정
	@Override
	public void insertSellerInfo(SellerVo sVo) throws AddException {		
		SqlSession session = sqlSessionFactory.openSession();
		session.insert("SellerMapping.insertSellerInfo", sVo);
		session.commit();
		session.close();
	}
	
	@Override
	public void updateSellerStatus(SellerVo sVo) throws AddException {
		SqlSession session = sqlSessionFactory.openSession();
		session.insert("SellerMapping.updateSellerStatus", sVo);
		session.commit();
		session.close();
	}
}
