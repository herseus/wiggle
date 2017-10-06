package com.keepsa.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ConstantUtils {
	public static Map<String, BigDecimal> ExRate = new HashMap<String, BigDecimal>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("GBP", new BigDecimal(8.94));
			put("EUR", new BigDecimal(7.73));
			put("USD", new BigDecimal(6.89));
			put("CAD", new BigDecimal(5.10));
			put("JPY", new BigDecimal(0.06));
		}
	};

	public static String EURCNY = "EURCNY";
	
	public static String GBPCNY = "GBPCNY";
	
	public static String JPYCNY = "JPYCNY";
	
	public static String USDCNY = "USDCNY";
	
	public static String CADCNY = "CADCNY";
}
