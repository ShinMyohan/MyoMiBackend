package com.myomi.order.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myomi.control.Controller;

@WebServlet("/order/*")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public OrderController() {
		super();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String contextPath = request.getContextPath(); // MyoMiBackend
		String servletPath = request.getServletPath(); // order
		String uri = request.getRequestURI(); // MyoMiBackend/order
		String url = request.getRequestURL().toString();

		String[] arr = uri.split("/");
		String subPath = arr[arr.length - 1];
		String envFileName = "order.properties";
		envFileName = getServletContext().getRealPath(envFileName);
		Properties env = new Properties();
		env.load(new FileInputStream(envFileName));
		String className = env.getProperty(subPath);
		try {
			Class clazz = Class.forName(className);
			Object obj = clazz.getDeclaredConstructor().newInstance();
			Controller controller = (Controller) obj;
			String result = controller.execute(request, response);
			response.getWriter().print(result);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
		}
	}
}