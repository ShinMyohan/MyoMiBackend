package com.myomi.coupon.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myomi.control.Controller;
import com.myomi.coupon.service.CouponService;
import com.myomi.exception.FindException;

public class ListController implements Controller {
    
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		// 기본 설정
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");

		String user = request.getParameter("user");
		CouponService service = new CouponService();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			List<Map<String,Object>> couponList = service.findCouponList(user);
			String jsonStr = mapper.writeValueAsString(couponList);
			return jsonStr;
		} catch(FindException e) {
			e.printStackTrace();
			Map<String, String> map = new HashMap<>();
			map.put("msg", e.getMessage());
			String jsonStr = mapper.writeValueAsString(map);
			return jsonStr;
		}
	}
}
