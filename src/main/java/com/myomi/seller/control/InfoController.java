package com.myomi.seller.control;

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
import com.myomi.exception.AddException;
import com.myomi.seller.service.SellerService;

public class InfoController implements Controller {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("UTF-8");

		SellerService service = new SellerService();
		ObjectMapper mapper = new ObjectMapper();

		String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

		try {
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(collect);

			try {
				service.addSellerInfo(jsonObject);
			} catch (AddException e) {
				e.printStackTrace();
			}
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
