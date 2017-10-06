package com.keepsa.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.keepsa.dao.ExRateDaoImpl;
import com.keepsa.pojo.ExRateVo;
import com.keepsa.utils.DateUtils;

@Service
public class FinanceServiceImpl {
	@Resource
	private ExRateDaoImpl exRateDao;

	/**
	 * Query exchange rate from Yahoo
	 */
	public ExRateVo queryExRateFromYahoo() {
		String result = "";
		BufferedReader in = null;
		ExRateVo exRateVo = null;
		try {
			String urlNameString = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22EURCNY%22%2C%22GBPCNY%22%2C%22JPYCNY%22%2C%22USDCNY%22%2C%22CADCNY%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
//			for (String key : map.keySet()) {
//				System.out.println(key + "--->" + map.get(key));
//			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			exRateVo = getExRateVoFromYQLString(result);

		} catch (Exception e) {
//			System.out.println("发送GET请求出现异常！" + e);
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
			}
		}

		return exRateVo;
	}

	private ExRateVo getExRateVoFromYQLString(String str) {
		ExRateVo exRateVo = null;

		try {
			exRateVo = new ExRateVo();
			JSONObject rootObject = JSONObject.parseObject(str);
			JSONObject queryObject = rootObject.getJSONObject("query");
			JSONObject resultsObject = queryObject.getJSONObject("results");
			JSONArray rateArray = resultsObject.getJSONArray("rate");
			JSONObject rateObject = null;
			String exRateName = null;
			BigDecimal exRate = null;

			for (Object rate : rateArray) {
				rateObject = (JSONObject) rate;
				exRateName = rateObject.getString("id");
				exRate = rateObject.getBigDecimal("Rate");

				switch (exRateName) {
				case "EURCNY":
					exRateVo.setEURCNY(exRate);
					break;
				case "GBPCNY":
					exRateVo.setGBPCNY(exRate);
					break;
				case "JPYCNY":
					exRateVo.setJPYCNY(exRate);
					break;
				case "USDCNY":
					exRateVo.setUSDCNY(exRate);
					break;
				case "CADCNY":
					exRateVo.setCADCNY(exRate);
					break;
				default:
					break;
				}
			}

		} catch (Exception ex) {

		}
		return exRateVo;
	}

	public ExRateVo queryExRate() {
		ExRateVo exRateVo = exRateDao.queryExRate();
		
//		Yahoo API is not accessible
//		if (null != exRateVo
//				&& DateUtils.isToday(exRateVo.getUpdateTime().split(" ")[0])) {
//			return exRateVo;
//		}
//
//		exRateVo = queryExRateFromYahoo();
//		exRateDao.updateExRate(exRateVo);

		return exRateVo;
	}
	
	
}
