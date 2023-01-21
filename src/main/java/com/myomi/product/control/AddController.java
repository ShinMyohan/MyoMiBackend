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
import com.myomi.seller.vo.SellerVo;
import com.myomi.user.vo.UserVo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("UTF-8");
		
		UserVo uVo = new UserVo();
		SellerVo sVo = new SellerVo();
		ProductVo pVo = new ProductVo();
		ObjectMapper mapper = new ObjectMapper();
		
		ProductService pService = new ProductService();
		
		String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		try {
			JSONParser jp = new JSONParser();
			JSONObject jo = (JSONObject) jp.parse(collect);
			
			uVo.setId(jo.get("seller").toString());
			sVo.setUser(uVo);
			pVo.setSeller(sVo);
			pVo.setCategory(jo.get("category").toString());
			pVo.setName(jo.get("name").toString());
			pVo.setOriginPrice(Integer.parseInt(jo.get("originPrice").toString()));
			pVo.setPercentage(Integer.parseInt(jo.get("percentage").toString()));
			pVo.setWeek(Integer.parseInt(jo.get("week").toString()));
			pVo.setStatus(Integer.parseInt(jo.get("status").toString()));
			pVo.setDetail(jo.get("detail").toString());
			pVo.setFee(Integer.parseInt(jo.get("fee").toString()));
			
			pService.addProduct(pVo);
			
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
