package com.keepsa.utils;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.keepsa.pojo.ResponseVo;

public class RestUtils {
	/**
	 * Get request parameter, url decoded
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestParameter(HttpServletRequest request) {
		if (null == request) {
			return null;
		}
		
		String method = request.getMethod();
		String param = null;
		
		if ("GET".equalsIgnoreCase(method)) {
			param = request.getQueryString();			
		} else {
			param = getBodyData(request);
		}
		
		try {
			param = URLDecoder.decode(param, "UTF-8");
		} catch (Exception e) {
			param = null;
		}
		
		return param;
	}
	
	private static String getBodyData(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader reader = null;
		
		try {
			reader = request.getReader();
			while (null != (line = reader.readLine())) {
				sb.append(line);
			}
		} catch (Exception ex) {
			
		}
		
		return sb.toString();
	}
	
	public static void setResponse(HttpServletResponse response, ResponseVo responseVo) {
		/*
		 * HttpServletResponse 默认编码字符集为ISO-8859-1，导致从DB中读取的中文乱码（UTF-8字符集编码）
		 * 需要设置为UTF-8
		 */
		response.setCharacterEncoding("utf-8");
		
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			out.append(JSONObject.toJSONString(responseVo));
		} catch (Exception ex) {
			
		} finally {
			if (null != out) {
				out.close();
			}
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		
	}
	
	
}
