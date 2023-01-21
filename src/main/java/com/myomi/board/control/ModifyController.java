package com.myomi.board.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myomi.board.service.BoardService;
import com.myomi.board.vo.BoardVo;
import com.myomi.control.Controller;
import com.myomi.exception.FindException;

public class ModifyController implements Controller {

	//완성 
    @Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");

		ObjectMapper mapper = new ObjectMapper();
		
		String category = request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int num = Integer.parseInt(request.getParameter("num"));
		
    	BoardService service = new BoardService();
    	try {
			service.modifyBoard(new BoardVo (num,null,category,title,content,null,0,null));
	
    	} catch (FindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map<String, Object> map = new HashMap<>();
			map.put("msg", e.getMessage());
			String jsonStr = mapper.writeValueAsString(map);
			return jsonStr;
		}
		return null;
	}
}