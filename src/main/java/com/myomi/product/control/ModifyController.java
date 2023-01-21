package com.myomi.product.control;

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
import com.myomi.exception.FindException;
import com.myomi.product.service.ProductService;
import com.myomi.product.vo.ProductVo;

public class ModifyController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("UTF-8");
		
		int prodNum = Integer.parseInt(request.getParameter("prodNum"));
		ProductVo pVo = new ProductVo();
		ObjectMapper mapper = new ObjectMapper();
		
		ProductService pService = new ProductService();
		
		String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		try {
			JSONParser jp = new JSONParser();
			JSONObject jo = (JSONObject) jp.parse(collect);
			
			pVo.setNum(prodNum);
			pVo.setCategory(jo.get("category").toString());
			pVo.setName(jo.get("name").toString());
			pVo.setStatus(Integer.parseInt(jo.get("status").toString()));
			pVo.setDetail(jo.get("detail").toString());

			pService.modifyProduct(pVo);
			
			String jsonStr = mapper.writeValueAsString(jo);
			return jsonStr;
		} catch (FindException e) {
			e.printStackTrace();
			Map<String, String>map = new HashMap<>();
			map.put("msg",  e.getMessage());
			String jsonStr = mapper.writeValueAsString(map);
			return jsonStr;
		} catch (ParseException e) {
			e.printStackTrace();
			Map<String, String>map = new HashMap<>();
			map.put("msg",  e.getMessage());
			String jsonStr = mapper.writeValueAsString(map);
			return jsonStr;
		}
	}

}
