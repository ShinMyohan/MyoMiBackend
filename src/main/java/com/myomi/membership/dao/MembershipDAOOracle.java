package com.myomi.membership.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

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
		//selectList() 안에 들어가는주소(?)는 namespace="com.myomi.product.dao.ProductDAO" 와동일해야합니다!
		//namespace 주소.<select> 에서 부여한 id   namespace.id
		List<MembershipVo> list = session.selectList("com.myomi.membership.dao.MembershipDAO.selectMembershipList");      
		session.close();
		return list;   
		
//		ProductVo vo = session.selectOne("com.relo.mybatis.orders.OrdersDao.selectOrdersDetail", oNum);

//	session.insert("com.relo.mybatis.orders.OrdersDao.insertOrders", map);
//
//	session.update("com.relo.mybatis.product.ProductDao.update8", aNum);
//
//	session.delete("com.relo.mybatis.catch.CatchDao.deleteCatch", aNum);
	}
	
	public static void main(String[] args) throws FindException{
		MembershipDAOOracle dao = new MembershipDAOOracle();
		System.out.println(dao.selectAll());
		System.out.println("============");

	}

}