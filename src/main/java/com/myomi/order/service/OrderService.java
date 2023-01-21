package com.myomi.order.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.myomi.coupon.vo.CouponVo;
import com.myomi.exception.FindException;
import com.myomi.order.dao.OrderDAO;
import com.myomi.order.dao.OrderDAOOracle;
import com.myomi.order.vo.DeliveryVo;
import com.myomi.order.vo.OrderVo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderService {
	OrderDAO dao = new OrderDAOOracle();
	OrderVo oVo = new OrderVo();
	CouponVo cVo = new CouponVo();
	DeliveryVo dVo = new DeliveryVo();

	public List<Map<String, Object>> findMyOrder(int num) throws FindException {
		return dao.selectMyOrder(num);
	}

	// 배송정보 입력
	public void addDelivery(JSONObject jsonObject) {
		dVo.setOrderNum(Integer.parseInt(jsonObject.get("num").toString()));
		dVo.setName(jsonObject.get("name").toString());
		dVo.setTel(jsonObject.get("tel").toString());
		dVo.setAddr(jsonObject.get("addr").toString());
		dVo.setDeliveryMsg(jsonObject.get("deliveryMsg").toString());

		dao.insertDelivery(dVo);
	}

	// 결제버튼 누를 시 업데이트
	public void modifyOrder(JSONObject jsonObject) {
		oVo.setNum(Integer.parseInt(jsonObject.get("num").toString()));
		oVo.setOptions(Integer.parseInt(jsonObject.get("options").toString()));
		oVo.setMsg(jsonObject.get("msg").toString());
		// 쿠폰을 사용하지 않으면 프론트에서 0으로 받아옴
		int couponNum = Integer.parseInt(jsonObject.get("couponNum").toString());
		if (couponNum != 0) {
			cVo.setNum(couponNum);
			oVo.setCoupon(cVo);
		} else {
			cVo.setNum(0);
		}
		oVo.setUsedPoint(Integer.parseInt(jsonObject.get("usedPoint").toString()));
		oVo.setTotalPrice(Integer.parseInt(jsonObject.get("totalPrice").toString()));
		oVo.setSavePoint(Integer.parseInt(jsonObject.get("savePoint").toString()));
		
		dao.updateOrder(oVo);
	}
}
