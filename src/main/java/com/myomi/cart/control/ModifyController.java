package com.myomi.cart.control;

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
import com.myomi.cart.service.CartService;
import com.myomi.control.Controller;
import com.myomi.exception.FindException;

public class ModifyController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("UTF-8");
		
		int num = Integer.parseInt(request.getParameter("num"));
		ObjectMapper mapper = new ObjectMapper();
		CartService cService = new CartService();

		String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		try {
			JSONParser jp = new JSONParser();
			JSONObject jo = (JSONObject) jp.parse(collect);
			jo.put("num", num);
			cService.modifyCart(jo);
			String jsonStr = mapper.writeValueAsString(jo);
			return jsonStr;
		} catch (ParseException e) {
			e.printStackTrace();
			Map<String, String>map = new HashMap<>();
			map.put("msg",  e.getMessage());
			String jsonStr = mapper.writeValueAsString(map);
			return jsonStr;
		} catch (FindException e) {
			e.printStackTrace();
			Map<String, String>map = new HashMap<>();
			map.put("msg",  e.getMessage());
			String jsonStr = mapper.writeValueAsString(map);
			return jsonStr;
		}
	}
}
