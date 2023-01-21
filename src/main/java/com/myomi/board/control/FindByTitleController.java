package com.myomi.board.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myomi.board.service.BoardService;
import com.myomi.control.Controller;
import com.myomi.exception.FindException;

public class FindByTitleController implements Controller {
	
    //완성 
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");

		ObjectMapper mapper = new ObjectMapper();
		BoardService service = new BoardService();
		
		String title = request.getParameter("title");
		String title2 = "%"+title+"%";
		
		try {
			List<Map<String, Object>> list = service.findByTitle(title2);
			System.out.println(list);
			String jsonStr = mapper.writeValueAsString(list);
			return jsonStr;

		} catch (FindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map<String, Object> map = new HashMap<>();
			map.put("msg", e.getMessage());
			String jsonStr = mapper.writeValueAsString(map);
			return jsonStr;

		}
	}

}
