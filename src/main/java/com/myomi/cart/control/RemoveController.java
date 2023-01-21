package com.myomi.cart.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myomi.cart.service.CartService;
import com.myomi.control.Controller;
import com.myomi.exception.FindException;

public class RemoveController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		// 체크박스에 선택 된 값을 String에 +로 담아줘서 removeCart 메서드에 파라미터로 담아 넘겨줍니다.
		String str = request.getParameter("num");
		
		ObjectMapper mapper = new ObjectMapper();
		CartService cService = new CartService();
		
		try {
			cService.removeCart(str);
		} catch (FindException e) {
			e.printStackTrace();
			Map<String, String>map = new HashMap<>();
			map.put("msg",  e.getMessage());
			String jsonStr = mapper.writeValueAsString(map);
			return jsonStr;
		}
		return "삭제 완료";
	}
}
