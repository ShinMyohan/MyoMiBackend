package com.myomi.point.control;
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

@WebServlet("/point/*")
public class PointController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public PointController() {
		super();
	}
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		String uri = request.getRequestURI();
		String url = request.getRequestURL().toString();
		String[] arr = uri.split("/");
		String subPath = arr[arr.length - 1]; // arr[2]

		// 결합도를 떨어뜨리기 위해 외부파일 이용. 다른 클래스(infoController,ListController)불러옴
		String envFileName = "review.properties";
		envFileName = getServletContext().getRealPath(envFileName);
		// 톰캣서버에 배포되어 있는 파일을 가져와야 하기 때문에
		// 실제 톰캣 주소를 가져와야함
		Properties env = new Properties();
		env.load(new FileInputStream(envFileName));
		// 프로퍼티스 안에 있는 내용을 로드해야하기때문에 인풋스트림 사용
		String className = env.getProperty(subPath);
		try {
			Class clazz = Class.forName(className);
			// 이름에 해당하는 클래스를 찾아서 jvm 메모리 위쪽으로 올려줌
			// getDeclaredConstructor 메소드는 public, protected, private, default 상관없이 class 안의
			// 모든 생성자에 접근 가능하다.
			Object obj = clazz.getDeclaredConstructor().newInstance();
			// 요청시마다 새로운 객체가 생겨남. 메모리 위험
			Controller controller = (Controller) obj;// controller타입으로 캐스팅
			String result = controller.execute(request, response);
			response.getWriter().print(result);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
}
