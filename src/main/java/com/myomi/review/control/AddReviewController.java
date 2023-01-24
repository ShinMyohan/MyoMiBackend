package com.myomi.review.control;

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
import com.myomi.review.service.ReviewService;

public class AddReviewController implements Controller {
//이미지 첨부 미완이라 주석으로 해놓음
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		//첨부파일이 저장될 폴더
//		String saveDirectory = "C:\\255\\attach";
//		String tempDirectory = "C:\\255\\attachtemp";
//
//		int maxPostSize = 100*1024;
//		String encoding = "UTF-8";
		//파일첨부
//		MultipartRequest mr = new MultipartRequest(request, tempDirectory, maxPostSize, encoding);
//
//		File[] files = new File(tempDirectory).listFiles();

//		for(File file: files) {
//			String fileName = file.getName();
//			long fileLength = file.length();
//			System.out.println("fileName=" + fileName + ", fileLength=" + fileLength);
	
			//폴더생성
//			String subDirectory = fileName.substring(0, 1);//C G
//			File dir = new File(saveDirectory, subDirectory);
//			if(!dir.exists()) {
//				
//			}		
			//파일 옮기기
			//File origin = new File(tempDirectory, fileName);//원본

			
			//File copy = new File(dir, UUID.randomUUID() + "_" + fileName); //복사본
			
			//1)원본읽기
//			FileInputStream fis = null;
//			fis = new FileInputStream(origin);
			
			//2)복사본붙여넣기
//			FileOutputStream fos = null;
//			fos = new FileOutputStream(copy);
//			
//			int readValue = -1;
//			while((readValue = fis.read()) != -1) {
//				fos.write(readValue);
//			}
//			
//			fos.close();
//			fis.close();
			
			//3)원본삭제
//			origin.delete();
//		}
//		
//		
//		String t = mr.getParameter("t");
		ReviewService service = new ReviewService();
		ObjectMapper mapper = new ObjectMapper();
		String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		
		try {
			// Json형식을 웹이나 다른곳에서 받아왔을 때 parse하는 코드
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(collect);

			// service 메서드 호출
			service.addReview(jsonObject);
			String jsonStr = mapper.writeValueAsString(jsonObject);
			return jsonStr;
		} catch (ParseException | FindException e) {
			e.printStackTrace();
			Map<String, String> map = new HashMap<>();
			map.put("msg", e.getMessage());
			String jsonStr = mapper.writeValueAsString(map);
			return jsonStr;
		}

}
}
