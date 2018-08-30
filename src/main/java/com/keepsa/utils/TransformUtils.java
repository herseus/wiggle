package com.keepsa.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * Utility methods for transformation.
 * 
 * Like: transform sku listed in Amazon to plain sku
 * 
 * @author huangzejun
 *
 */
public class TransformUtils {
	public static String removeCountryCodeAndFBMSuffixFromSku(String sku) {
		String targetSku = sku;
		// If the sku is generated by Amazon(e.g. GL-A0G1-NIJK), just return.
		if (targetSku.matches(".{2}-.{4}-.{4}")) {
			return targetSku;
		}

		targetSku = targetSku.replace("-FBM", "");
		if (ifContainsCountryAbbr(targetSku)) {
			targetSku = targetSku.substring(0, 1) + targetSku.substring(3);

		}
		return targetSku;
	}

	private static Boolean ifContainsCountryAbbr(String sku) {
		String subString = sku.substring(1, 3);
		if (subString.equalsIgnoreCase("UK") || subString.equalsIgnoreCase("DE") || subString.equalsIgnoreCase("FR")
				|| subString.equalsIgnoreCase("IT") || subString.equalsIgnoreCase("ES")
				|| subString.equalsIgnoreCase("US") || subString.equalsIgnoreCase("CA")
				|| subString.equalsIgnoreCase("JP")) {
			return true;
		}

		return false;
	}

	public static String transformUTCTimeToNormalTime(String str) {
		return str.replaceAll("T", " ").substring(0, 19);
	}

	public static String getAmazonAddress(String shipAddress1, String shipAddress2, String shipAddress3,
			String shipPostalCode, String shipCity, String shipState, String shipCountryCode) {
		String amazonAddress = shipAddress1 + "\n" + (StringUtils.isBlank(shipAddress2) ? "" : (shipAddress2 + "\n"))
				+ (StringUtils.isBlank(shipAddress3) ? "" : (shipAddress3 + "\n")) + shipPostalCode + " " + shipCity
				+ " " + shipState + "\n" + getShipCountryFullName(shipCountryCode);

		return amazonAddress;
	}

	private static String getShipCountryFullName(String shipCoutryCode) {
		switch (shipCoutryCode) {
		case "GB":
			return "United Kingdom";
		case "DE":
			return "Germany";
		case "FR":
			return "France";
		case "IT":
			return "Italy";
		case "ES":
			return "Spain";
		case "US":
			return "United States";
		case "CA":
			return "Canada";
		case "MX":
			return "Mexico";
		case "JP":
			return "Japan";
		case "AT":
			return "Austria";

		default:
			return StringUtils.EMPTY;
		}
	}
	
	/**
	 * Get buffered reader from string
	 * 
	 * @param str
	 * @return
	 */
	public static BufferedReader getBufferedReaderFromString(String str) {
		InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(str.getBytes()));
		BufferedReader bufferedReader = new BufferedReader(isr);
		return bufferedReader;
	}
	
	public static BufferedReader getBufferedReaderFromFilePath(String filePath) throws UnsupportedEncodingException, FileNotFoundException {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "ISO-8859-1");
		BufferedReader bufferedReader = new BufferedReader(isr);
		return bufferedReader;
	}
	
	public static String decodeBase64String(String str) {
		try {
			return new String(Base64.decodeBase64(str.getBytes()));
		} catch (Exception e) {
			return null;
		}
	}
}
