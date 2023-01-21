package com.myomi.order.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myomi.control.Controller;
import com.myomi.coupon.vo.CouponVo;
import com.myomi.order.service.OrderService;
import com.myomi.order.vo.OrderVo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentController implements Controller {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 기본 설정
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		// json 한글깨짐 현상
		request.setCharacterEncoding("UTF-8");

		OrderVo oVo = new OrderVo();
		CouponVo cVo = new CouponVo();
		OrderService service = new OrderService();
		ObjectMapper mapper = new ObjectMapper();

		// request를 읽어서 Json 형태로 받아옴
		String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		try {
			// Json형식을 웹이나 다른곳에서 받아왔을 때 parse하는 코드
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(collect);

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

			System.out.println("oVO : " + oVo);
			// service 메서드 호출
			service.modifyOrder(oVo);

			String jsonStr = mapper.writeValueAsString(jsonObject);
			return jsonStr;
		} catch (ParseException e) {
			e.printStackTrace();
			Map<String, String> map = new HashMap<>();
			map.put("msg", e.getMessage());
			String jsonStr = mapper.writeValueAsString(map);
			return jsonStr;
		}
	}
}
