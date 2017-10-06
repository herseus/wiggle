package com.keepsa.utils;

public class ExceptionUtils {
	public static String getExceptionStackTrace(Exception e) {
		StringBuffer sb = new StringBuffer();
		sb.append("Exception: ").append(e.getMessage()).append("\n").append("The stack trace is:").append("\n");

		StackTraceElement[] elements = e.getStackTrace();

		for (StackTraceElement element : elements) {
			sb.append(element.toString()).append("\n");
		}

		return sb.toString();
	}
}
