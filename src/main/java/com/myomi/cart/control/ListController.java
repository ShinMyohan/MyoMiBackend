package com.myomi.cart.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myomi.cart.service.CartService;
import com.myomi.control.Controller;
import com.myomi.exception.FindException;

public class ListController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		String userId = request.getParameter("userId");
		ObjectMapper mapper = new ObjectMapper();
		CartService cService = new CartService();
		try {
			List<Map<String, Object>> list = cService.findAllCart(userId);
			String jsonStr = mapper.writeValueAsString(list);
			return jsonStr;
		} catch (FindException e){
			e.printStackTrace();
			Map<String, String>map = new HashMap<>();
			map.put("msg",  e.getMessage());
			String jsonStr = mapper.writeValueAsString(map);
			return jsonStr;
		}
	}
}
