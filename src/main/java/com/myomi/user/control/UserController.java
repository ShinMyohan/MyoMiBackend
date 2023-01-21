package com.myomi.user.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myomi.control.Controller;

@WebServlet("/user/*")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		String uri = request.getRequestURI();
		String url = request.getRequestURL().toString();
		
		System.out.println("contextPath=" + contextPath);
		System.out.println("servletPath=" + servletPath);
		System.out.println("uri=" + uri); //uri=/myback/product/list, uri=/myback/product/info
		System.out.println("url=" + url);
		
		String []arr = uri.split("/");
		String subPath = arr[arr.length-1];  //
		
		//결합도를 떨어뜨리기 위해 외부파일을 이용.
		//다른 클래스 불러옴
		String envFileName = "user.properties";
		envFileName = getServletContext().getRealPath(envFileName);
		Properties env = new Properties();
		env.load(new FileInputStream(envFileName));
		String className = env.getProperty(subPath);
		try {
			Class clazz = Class.forName(className);
			//이름에 해당하는 클래스를 찾아서 jvm 메모리 위쪽으로 올려줌
			
			//getDeclaredConstructor 메소드는 public, protected, private, default 상관없이 class 안의 모든 생성자에 접근 가능하다.
			Object obj = clazz.getDeclaredConstructor().newInstance();
			//요청할때마다 새로운 객체가 생겨남 -> 메모리위험
			
			Controller controller = (Controller)obj;  //controller타입으로 캐스팅 //다운캐스팅
			String result = controller.execute(request, response);
			response.getWriter().print(result);
			
			//if else를 쓰게 되면 버전이 업그레이드 될때마다 수정해주어야 하기 때문에
			//컴파일 등 문제가 있을 수 있어서 프로퍼티스 이용해서 그걸 방지해준다.
			//uri를통해 얻어낸 프로퍼티 이름을 찾고, 그 값을 이용해 jvm메모리 위쪽으로 올리고
			//객체 생성 execute객체 생성 그 결고 값을 받아와서 응답
			
			//싱글톤 패턴으로 만들기 위해서 static예약어 필요함
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		if("list".equals(subPath)) {
//			list(request, response);
//		}else if("info".equals(subPath)) {
//			info(request, response);
//		}
		
	}
//	private void info(HttpServletRequest request, HttpServletResponse response)
//		    throws IOException, ServletException{
//		response.getWriter().print("상품상세정보입니다");
//	}
//	private void list(HttpServletRequest request, HttpServletResponse response)
//	    throws IOException, ServletException{
//		response.setContentType("application/json;charset=UTF-8");
//		response.addHeader("Access-Control-Allow-Origin", "*");
//		PrintWriter out = response.getWriter();
//		String cp = request.getParameter("currentPage");
//		//http://localhost:8888/myback/productlist
//		//http://localhost:8888/myback/productlist?currentPage=
//		int currentPage = 1;
//		if(cp != null && !"".equals(cp)) {
//			currentPage = Integer.parseInt(cp);
//		}		
//		ObjectMapper mapper = new ObjectMapper();
//		ProductService service = new ProductService();
//		try {
//			//List<Product> list = service.findAll(currentPage);
//			//String jsonStr = mapper.writeValueAsString(list);
//			//Map<String, Object> map = service.findAll(currentPage);
//			//String jsonStr = mapper.writeValueAsString(map);
//			
//			PageBean<Product> pb = service.findAll(currentPage);
//			String jsonStr = mapper.writeValueAsString(pb);
//			out.print(jsonStr);
//		} catch (FindException e) {
//			e.printStackTrace();
//			Map<String, String> map = new HashMap<>();
//			map.put("msg", e.getMessage());
//			String jsonStr = mapper.writeValueAsString(map);
//			out.print(jsonStr);
//		}
//	}
}
