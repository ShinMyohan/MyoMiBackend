package com.myomi.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.myomi.exception.FindException;

import com.myomi.order.vo.DeliveryVo;
import com.myomi.order.vo.OrderVo;
import com.myomi.resource.Factory;
/**
 * @author seli
 *
 */
public class OrderDAOOracle implements OrderDAO {
	private SqlSessionFactory sqlSessionFactory;

	public OrderDAOOracle() {
		sqlSessionFactory = Factory.getSqlSessionFactory();
	}

	// 주문 불러오기
	@Override
	public List<Map<String, Object>> selectMyOrder(int num) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		List<Map<String, Object>> list = session.selectList("OrderMapper.selectOneOrder", num);

		if (list == null) {
			System.out.println("조회결과 없음");
		} else {
			for (Map<String, Object> prod : list) {
				System.out.println(prod);
			}
		}
		session.close();
		return list;
	}

	// 배송정보 인서트
	@Override
	public void insertDelivery(DeliveryVo delivery) {
		SqlSession session = sqlSessionFactory.openSession();

		session.insert("OrderMapper.insertDelivery", delivery);
		session.commit();
		session.close();
	}

	// 주문정보에 주문정보 업데이트하기
	public void updateOrder(OrderVo oVo) {
		System.out.println("oVo : "+ oVo);
		
		SqlSession session = sqlSessionFactory.openSession();

		session.update("OrderMapper.updateOrder", oVo);
		session.commit();
		session.close();

	}
}

