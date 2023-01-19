package com.myomi.order.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.myomi.exception.FindException;
import com.myomi.order.vo.OrderDetailVo;

public interface OrderDAO {
	
	// 상품과 주문자 정보 가져오기
	public List<Map<String, Object>> selectOneOrder(int num) throws FindException;

	// 주문 상품 상세 가져오기
	public List<OrderDetailVo> selectOrderDetail(int num) throws FindException;
	
	// 배송정보 입력하기
	public void insertDelivery(int num, String name, String tel, String addr, String deliveryMsg, Date receiveDate) throws FindException;
	
	// 주문 시 결제정보 업데이트
	public void updateOrder(int num, String userId, int options, String msg, int couponNum, int usedPoint,
			Date payCreatedDate, int totalPrice, int savePoint) throws FindException;
}