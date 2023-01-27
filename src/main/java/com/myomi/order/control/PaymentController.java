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
import com.myomi.order.service.OrderService;

public class PaymentController implements Controller {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 기본 설정
		response.setContentType("application/json;charset=UTF-8");
//		response.addHeader("Access-Control-Allow-Origin", "*");
		// json 한글깨짐 현상
		request.setCharacterEncoding("UTF-8");

		OrderService service = new OrderService();
		ObjectMapper mapper = new ObjectMapper();

		// request를 읽어서 Json 형태로 받아옴
		String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		try {
			// Json형식을 웹이나 다른곳에서 받아왔을 때 parse하는 코드
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(collect);
			
			System.out.println(jsonObject);
			// service 메서드 호출
			service.modifyOrder(jsonObject);
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
