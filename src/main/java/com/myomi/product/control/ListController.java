package com.myomi.product.control;

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
import com.myomi.product.service.ProductService;

public class ListController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		ObjectMapper mapper = new ObjectMapper();
		ProductService pService = new ProductService();
		try {
			List<Map<String, Object>> list = pService.findAllProduct();
			String jsonStr = mapper.writeValueAsString(list);
			return jsonStr;
		} catch (FindException e) {
			e.printStackTrace();
			Map<String, String> map = new HashMap<>();
			map.put("msg", e.getMessage());
			String jsonStr = mapper.writeValueAsString(map);
			return jsonStr;
		}
//		String category = request.getParameter("category");
//		
//		ObjectMapper mapper = new ObjectMapper();
//		ProductService pService = new ProductService();
//		try {
//			List<Map<String, Object>> list = pService.findProductByCategory(category);
//			String jsonStr = mapper.writeValueAsString(list);
//			return jsonStr;
//		} catch (FindException e) {
//			e.printStackTrace();
//			Map<String, String> map = new HashMap<>();
//			map.put("msg", e.getMessage());
//			String jsonStr = mapper.writeValueAsString(map);
//			return jsonStr;
//		}
	}

}
