package com.myomi.order.service;

import java.util.List;
import java.util.Map;

import com.myomi.exception.FindException;
import com.myomi.order.dao.OrderDAO;
import com.myomi.order.dao.OrderDAOOracle;
import com.myomi.order.vo.DeliveryVo;
import com.myomi.order.vo.OrderVo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderService {
	OrderDAO dao = new OrderDAOOracle();

	public List<Map<String, Object>> findMyOrder(int num) throws FindException {
		return dao.selectMyOrder(num);
	}

	public void addDelivery(DeliveryVo dVo) {
		dao.insertDelivery(dVo);
	}

	// 결제버튼 누를 시 업데이트
	public void modifyOrder(OrderVo oVo) {
		dao.updateOrder(oVo);
	}
}
