package com.myomi.membership.control;

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

@WebServlet("/membership/*")
public class MembershipController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath(); 
		String uri = request.getRequestURI();
		String url = request.getRequestURL().toString();
		System.out.println("contextPath"+contextPath);
		System.out.println("servletPath"+servletPath);
		System.out.println("uri"+uri); 
	
		System.out.println("url"+url);
		String[] arr = uri.split("/");
		String subPath = arr[arr.length-1];
		
		String envFileName = "membership.properties";
		envFileName = getServletContext().getRealPath(envFileName);
		
		Properties env = new Properties();
		env.load(new FileInputStream(envFileName));
		String className = env.getProperty(subPath);
		try {
			Class clazz = Class.forName(className); 
			Object obj = clazz.getDeclaredConstructor().newInstance();
			
			Controller controller = (Controller)obj;
			String result = controller.execute(request, response);
			response.getWriter().print(result);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
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