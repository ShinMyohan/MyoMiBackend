package com.myomi.board.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;


@WebServlet("/attach/*")
public class AttachServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String []arr = uri.split("/");
		String subPath = arr[arr.length-1];
		if("upload".equals(subPath)) {
			upload(request,response);
		}else if("download".equals(subPath)) {
			download(request,response);
		}

	}

	private void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		String opt = request.getParameter("opt");
		String fileName = request.getParameter("no");
		String saveDirectory = "/Users/hailey/Desktop/255/attach";
		String subDirectory = fileName.substring(0,1);
		File dir = new File(saveDirectory, subDirectory);
		File[] files = dir.listFiles();
		for (File f : files) {
			if(f.getName().split("_")[1].equals(fileName)) {
				//contains: 갖고있는지 확인 equals:정확히 일치하는지 확인 
				
				//찾은 경우 : response의 outputstream 담아준다 
				response.setHeader("Content-type", "image/jpg");
				response.setHeader("Content-length", ""+f.length());
				String contentType = Files.probeContentType(f.toPath()); //파일의 형식
				response.setHeader("Content-type", contentType);//응답형식
				
				if("attachment".equals(opt)) {
					response.setHeader("content-disposition", "attachment;filename=" + f.getName()); //다운로드되는 방법
				}else { //바로 응답 
					response.setHeader("content-disposition", "inline;filename=" + f.getName());  //바로 응답하는 방법 
				}
				
				FileInputStream fis = new FileInputStream(f);
				OutputStream os = response.getOutputStream();
				int readValue =-1;
				while((readValue = fis.read()) != -1) {
					os.write(readValue);
					//응답할 내용의 파일 형태, 응답 길이를 지정해주어야함 
					//바로 응답이 되게하는 경우, 다운로드가 되게 하는 경우 
				}
			}
		}
		//찾지 못한 경우 
	}


	private void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		//	    InputStream is = request.getInputStream();

		//	    int readValue = -1;
		//	    while((readValue = is.read())!= -1) { //전송된 데이터를 한바이트씩 읽어오겠다 
		//	    	System.out.print((char)readValue);
		//	    }
		//	 

		//첨부파일이 저장될 폴
		String saveDirectory = "/Users/hailey/Desktop/255/attach";
		String tempDirectory = "/Users/hailey/Desktop/255/attachtemp";
		int maxPostSize = 100*1024;
		String encoding = "UTF-8";
		MultipartRequest mr = new MultipartRequest(request, tempDirectory, maxPostSize, encoding);
		String t = mr.getParameter("t");

		//파일 첨부 
		//File file = mr.getFile("f");  cos.jar을 이용해서 여러 파일을 찾으려면 이렇게 하면 안됨
		File[] files = new File(tempDirectory).listFiles();
		for (File file: files) {  //여러 파일들을 하나씩 꺼냄 
			String fileName = file.getName();
			long fileLength = file.length();

			System.out.println("fileName="+fileName+ ", fileLength="+fileLength );

			//폴더 생성 
			String subDirectory = fileName.substring(0,1);
			File dir = new File(saveDirectory, subDirectory);
			if(!dir.exists()) {
				dir.mkdir();
			}

			//파일 옮기기
			File origin = new File(tempDirectory, fileName); //원본


			File copy = new File(dir,UUID.randomUUID()+"_"+fileName); //복사본 
			//UUID - 유니버셜 유니크 아이디를 사용하면 중복되지 않는 일련의 번호를 얻을 수 있음 
			// - 중복되지않는 파일 번호를 부여할 수 있음

			//원본읽기 
			FileInputStream fis = null;
			fis = new FileInputStream(origin);

			//복사본 붙여넣기 
			FileOutputStream fos = null;
			fos = new FileOutputStream(copy);
			int readValue =-1;  //있으면 0, 없으면 -1 함수마다 반환값을 봐야함 
			while((readValue = fis.read()) != -1) {
				fos.write(readValue);
			}

			fos.close();
			fis.close();


			//원본 삭제 
			origin.delete();
		}
	}

}
