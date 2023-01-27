package com.myomi.order.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myomi.control.Controller;
import com.myomi.exception.FindException;
import com.myomi.order.service.OrderService;

public class DetailController implements Controller {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 기본 설정
		response.setContentType("application/json;charset=UTF-8");
//		response.addHeader("Access-Control-Allow-Origin", "*");
		
		int orderNum = Integer.parseInt(request.getParameter("orderNum"));
		OrderService service = new OrderService();
		ObjectMapper mapper = new ObjectMapper();

		// 기본설정
		try {
			List<Map<String, Object>> order = service.findMyOrder(orderNum);
			String jsonStr = mapper.writeValueAsString(order);
			return jsonStr;
		} catch (FindException e) {
			e.printStackTrace();
			Map<String, String> map = new HashMap<>();
			map.put("msg", e.getMessage());
			String jsonStr = mapper.writeValueAsString(map);
			return jsonStr;
		}
	}
}