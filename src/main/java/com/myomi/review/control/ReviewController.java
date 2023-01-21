package com.myomi.review.control;

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

/**
 * Servlet implementation class ProductController
 */
@WebServlet("/review/*")
public class ReviewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		String contextPath = request.getContextPath();
		String servletPath=request.getServletPath();
		String uri = request.getRequestURI();
		String url = request.getRequestURL().toString();
		//System.out.println("contextPath="+ contextPath);
		//System.out.println("servletPath=" + servletPath);
		//System.out.println("uri=" + uri);
		//System.out.println("url=" + url);
		
		String []arr = uri.split("/");
		String subPath=arr[arr.length-1]; //arr[2]
		
		//결합도를 떨어뜨리기 위해 외부파일 이용. 다른 클래스(infoController,ListController)불러옴
		String envFileName = "review.properties";
		envFileName = getServletContext().getRealPath(envFileName);
		//톰캣서버에 배포되어 있는 파일을 가져와야 하기 때문에
		//실제 톰캣 주소를 가져와야함
		Properties env = new Properties();
		env.load(new FileInputStream(envFileName));
		//프로퍼티스 안에 있는 내용을 로드해야하기때문에 인풋스트림 사용
		String className=env.getProperty(subPath);
		try {
			Class clazz = Class.forName(className);
			//이름에 해당하는 클래스를 찾아서 jvm 메모리 위쪽으로 올려줌
			
			//Object obj = clazz.newInstance();
			//getDeclaredConstructor 메소드는 public, protected, private, default 상관없이 class 안의 모든 생성자에 접근 가능하다.
			Object obj = clazz.getDeclaredConstructor().newInstance();
			//요청시마다 새로운 객체가 생겨남. 메모리 위험
			
			Controller controller = (Controller)obj;//controller타입으로 캐스팅
			String result = controller.execute(request, response);
			response.getWriter().print(result);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if("list".equals(subPath)) {
//			list(request, response);
//		}else if("info".equals(subPath)) {
//			info(request,response);
//		}
 catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
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
	}
//	private void info(HttpServletRequest request, HttpServletResponse response)
//			throws IOException, ServletException{
//		response.getWriter().print("상품상세정보입니다");
//	}
//		private void list(HttpServletRequest request, HttpServletResponse response)
//			throws IOException, ServletException{
//			response.setContentType("application/json;charset=UTF-8");
//			response.addHeader("Access-Control-Allow-Origin", "*");
//			PrintWriter out = response.getWriter();
//			String cp = request.getParameter("currentPage");
//			//http://localhost:8888/myback/productlist
//			//http://localhost:8888/myback/productlist?currentpage=
//			int currentPage = 1;
//			if(cp != null && !"".equals(cp)) {
//				currentPage = Integer.parseInt(cp);
//			}
//			
//			ObjectMapper mapper = new ObjectMapper();
//			ProductService service = new ProductService();
//			try {
//				//List<Product> list = service.findAll(currentPage);
//				//String jsonStr = mapper.writeValueAsString(list);
//				//Map<String,Object> map = service.findAll(currentPage);
//				//String jsonStr = mapper.writeValueAsString(map);
//				PageBean<Product> pb = service.findAll(currentPage);
//				//map을 json 문자열형태로 한번에 바꿔줌. 잭슨에서 나온거임. 
//				String jsonStr = mapper.writeValueAsString(pb);
//				out.print(jsonStr);
//			} catch (FindException e) {
//				e.printStackTrace();
//				Map<String,String> map = new HashMap<>();
//				map.put("msg", e.getMessage());
//				String jsonStr = mapper.writeValueAsString(map);
//				out.print(jsonStr);
//			}
//		}
		}
//		
		
		
		

	


