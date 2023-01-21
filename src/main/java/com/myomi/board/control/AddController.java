package com.myomi.board.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myomi.board.service.BoardService;
import com.myomi.board.vo.BoardVo;
import com.myomi.control.Controller;
import com.myomi.exception.FindException;
import com.myomi.user.vo.UserVo;
import com.oreilly.servlet.MultipartRequest;

public class AddController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");

		ObjectMapper mapper = new ObjectMapper();

		UserVo uVo = new UserVo ();
		BoardVo bVo = new BoardVo();
		
		String id  = request.getParameter("id");
		uVo.setId(id);
		String category = request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		BoardService service = new BoardService();
		try {
			
			service.addBoard(new BoardVo(0,uVo,category,title,content,new Date(),0,null));
			
			
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
