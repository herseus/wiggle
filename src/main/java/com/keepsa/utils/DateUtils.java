package com.keepsa.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class DateUtils {
	public static Boolean isToday(String str) {
		try {
			DateTime today = new DateTime();
			DateTime someday = new DateTime(str);
			if (Days.daysBetween(today, someday).getDays() == 0) {
				return true;
			}
		} catch (Exception e) {
			
		}
		return false;
	}
	
	public static String getTodayLiteral() {
		return (new DateTime()).toString("yyyy-MM-dd");
	}
}
