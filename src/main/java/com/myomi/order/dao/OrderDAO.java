package com.myomi.order.dao;

import java.util.List;
import java.util.Map;

import com.myomi.exception.FindException;
import com.myomi.order.vo.DeliveryVo;
import com.myomi.order.vo.OrderVo;

public interface OrderDAO {

	// 상품과 주문자 정보 가져오기
	public List<Map<String, Object>> selectMyOrder(int num) throws FindException;

	// 배송정보 입력하기
	public void insertDelivery(DeliveryVo delivery);

	// 주문 시 결제정보 업데이트
	public void updateOrder(OrderVo oVo);
}