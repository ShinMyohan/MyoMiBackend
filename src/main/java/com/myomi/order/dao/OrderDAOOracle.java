package com.myomi.order.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.myomi.coupon.vo.CouponVo;
import com.myomi.exception.FindException;
import com.myomi.order.vo.OrderDetailVo;
import com.myomi.resources.Factory;
import com.myomi.user.vo.UserVo;

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
	public List<Map<String, Object>> selectOneOrder(int num) throws FindException {
				SqlSession session = sqlSessionFactory.openSession();
				List<Map<String, Object>> list = session.selectList("OrderMapper.selectOneOrder", num);
				

				if(list == null) {
					System.out.println("조회결과 없음");
				} else {
					for(Map<String, Object> prod : list) {
						System.out.println(prod);
					}
				}
				session.close();
				return list;
	}
	
	
	// 주문 디테일 확인
	@Override
	public List<OrderDetailVo> selectOrderDetail(int num) throws FindException {
				SqlSession session = sqlSessionFactory.openSession();
				List<OrderDetailVo> list = session.selectList("OrderMapper.selectDetailOrder", num);
				
				if(list == null) {
					System.out.println("조회결과 없음");
				} else {
					for(OrderDetailVo prod : list) {
						System.out.println(prod.toString());
					}
				}
				
				session.close();
				return list;
	}
	
	// 배송정보 인서트
	@Override
	public void insertDelivery (int orderNum, String name, String tel, String addr, String deliveryMsg, Date receiveDate) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		
		Map<String, Object> insertDelivery = new HashMap<>();
		insertDelivery.put("num", orderNum);
		insertDelivery.put("name", name);
		insertDelivery.put("tel", tel);
		insertDelivery.put("addr", addr);
		if(deliveryMsg != null) {
		insertDelivery.put("delivery_msg", deliveryMsg);}
		insertDelivery.put("receive_date", receiveDate);
		
		session.insert("OrderMapper.insertDelivery", insertDelivery);
		session.commit();
		
		if(insertDelivery.isEmpty()) {
			System.out.println("조회결과 없음");
		} else {
				System.out.println(insertDelivery);
			}
	}

	// 주문정보에 주문정보 업데이트하기
	public void updateOrder(int num, String userId, int options, String msg, int couponNum, int usedPoint,
			Date payCreatedDate, int totalPrice, int savePoint) throws FindException {
		SqlSession session = sqlSessionFactory.openSession();
		UserVo user = new UserVo();
		CouponVo coupon = new CouponVo();
		
		Map<String, Object> updateOrder = new HashMap<>();
		updateOrder.put("num", num);
		updateOrder.put("user_id", userId);
		updateOrder.put("options", options);
		updateOrder.put("msg", msg);
		if(couponNum != 0) { // 0값을 받아와도 if처리를 해주면 null로 들어감
		updateOrder.put("coupon_num", couponNum);} // 0이면 알아서 null로 들어감
		updateOrder.put("used_point", usedPoint);
		updateOrder.put("pay_created_date", payCreatedDate);
		updateOrder.put("total_price", totalPrice);
		updateOrder.put("save_point", savePoint);

		session.update("OrderMapper.updateOrder", updateOrder);
		session.commit();

	}

	public static void main(String Args[]) throws FindException {
		OrderDAOOracle dao = new OrderDAOOracle();
		dao.selectOneOrder(1);
		dao.selectOrderDetail(1);
		dao.insertDelivery(2, "name", "tel", "addr", null, new Date()); 
		dao.updateOrder(1, "user1", 0, "msg", 0, 0, new Date(), 24800, 248);
	}
}
