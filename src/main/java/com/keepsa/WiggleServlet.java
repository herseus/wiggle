package com.keepsa;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class WiggleServlet implements Servlet {
	/*
	 * 建立第一个servlet： servlet是一个接口,是运行在服务器端的java骨架；
	 * 首先创建一个Servlet类的构造器，然后在WEB-INF下的we.xml配置和映射文件
	 * 
	 * Servlet容器：运行Servlet，JSP,Filter，等的软件环境 可以来创建Servlet，并调用Servlet的相关周期方法；
	 * 
	 * Servlet生命周期的方法，生命周期相关的方法:一下方法都是Servlet容器负责调用
	 * 1.构造器：只被调用一次，只有第一次请求Servlet时，创建Servlet实例，调用构造器，这说明Servlet是单实例的！
	 * 2.init的方法:只被调用一次，在创建好实例后立即被调用。用于初始化Servlet；
	 * 3.servlet:被多次调用，每次请求都会调用Servlet方法，实际用于调用请求的；
	 * 4.destroy方法:只被调用一次，在当前Servlet所在的WEB应用被卸载前调用，用于释放当前Servlet所占用的资源。
	 * 
	 * load-on-startup（配置参数）:可以指定Servlet被创建的时机; 配置在Servlet节点中； <servlet>
	 * <servlet-name>secondServlet</servlet-name>
	 * <servlet-class>com.lanqiao.javatest.SecondServlet</servlet-class>
	 * <load-on-startup>2</load-on-startup> </servlet> 其数值越小越被被早创建
	 * 
	 */
	public void destroy() {
		System.out.println("destroy");

	}

	/*
	 * ServletConfig:封装了Servlet的配置信息，并且可以获取ServletContext对象，其有四个方法；
	 * 配饰Servlet初始化参数，必须在load-on-startup的前边配置 1.获取初始化参数 getInitParameter(String
	 * name):获取指定参数名的初始化参数 2.getInitParameterNames():获取参数名组成的Enumeration 对象。
	 * 3.getServletName() ：获取servlet(在web.xml中起的名字)名字的方法
	 * 
	 * 4.getServletContext():获取servletContext；Context为上下文，获取servlet的上下文；
	 * servletContext：可以认为是WEB应用的大管家，可以从中获取到当前WEB应用的各个方面的信息：
	 * (1):获取当前WEB应用的初始化参数（被所有的servlet的使用的） 先配置再获取(在web.xml中配置)
	 * (2):获取当前WEB应用的某一文件的绝对路径 getRealPath(String Path)
	 * (3):获取当前WEB应用的名称：getContextPath() (4):获取当前WEB应用的某一文件对象的输入流
	 * getResourceAsStream("jdbc.properties")：文件是在src目录下的文件
	 * getResourceAsStream("/WEB-INF/classes/jdbc.properties")：第二种方法
	 */
	public ServletConfig getServletConfig() {
		System.out.println("getservletconfit");
		return null;
	}

	public String getServletInfo() {
		System.out.println("getservletinfo");
		return null;
	}

	public void init(ServletConfig servletConfig) throws ServletException {
		System.out.println("init");
		String user = servletConfig.getInitParameter("user");
		System.out.println("user:" + user);

		Enumeration<String> names = servletConfig.getInitParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String value = servletConfig.getInitParameter(name);
			System.out.println("name:" + name + "\t" + "value:" + value);
		}

		// 获取servlet(在web.xml中起的名字)名字的方法
		String servletName = servletConfig.getServletName();
		System.out.println(servletName);

		// 获取servletContext对象；
		ServletContext servletContext = servletConfig.getServletContext();
		String driver = servletContext.getInitParameter("driver");
		System.out.println("driver:" + driver);

		Enumeration<String> names2 = servletContext.getInitParameterNames();
		while (names2.hasMoreElements()) {
			String name = names2.nextElement();
			String value = servletContext.getInitParameter(name);
			System.out.println("name:" + name + "\t" + "value:" + value);
		}

		// 获取当前WEB应用的某一文件的绝对路径(一定是根目录下的：即是WebContent下的)
		String realPath = servletContext.getRealPath("/javaee.test");
		System.out.println(realPath);

		// 获取当前WEB应用的名称：getContextPath()
		String contextPath = servletContext.getContextPath();
		System.out.println(contextPath);

		// 获取当前WEB应用的某一文件对象的输入流,其中文件在src目录下建立的
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream is = classLoader.getResourceAsStream("jdbc.properties");
			System.out.println("1:" + is);

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 第二种方法：
		try {
			InputStream is2 = servletContext.getResourceAsStream("/WEB-INF/classes/jdbc.properties");
			System.out.println("2:" + is2);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		System.out.println("servlce");

	}


}