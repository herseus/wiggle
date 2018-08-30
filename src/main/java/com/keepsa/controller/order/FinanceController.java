package com.keepsa.controller.order;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.keepsa.pojo.ResponseVo;
import com.keepsa.service.FinanceServiceImpl;
import com.keepsa.service.OrderServiceImpl;
import com.keepsa.utils.RestUtils;

/**
 * Finance module
 * 
 * @author huangzejun
 *
 */
@Controller
@RequestMapping(value="/finance")
public class FinanceController {
	@Resource
	private OrderServiceImpl orderService;
	
	@Resource
	private FinanceServiceImpl financeService;
	
	@RequestMapping(value="/computeFinancialData")
	public void computeFinancialData(HttpServletRequest request, HttpServletResponse response) {
		String paramString = RestUtils.getRequestParameter(request);
		String fromDate = null;
		String toDate = null;
		// 1:Amazon, 2:Merchant
		Integer fulfillmentChannel = null;
		
		ResponseVo responseVo = new ResponseVo();
		if (StringUtils.isNotEmpty(paramString)) {
			paramString = paramString.replaceAll("&", "\",\"").replaceAll("=", "\":\"").replaceAll("\\s*", "");
			paramString = "{\"" + paramString + "\"}";
			JSONObject paramObject = JSONObject.parseObject(paramString);
			fromDate = paramObject.getString("fromDate");
			toDate = paramObject.getString("toDate");
			fulfillmentChannel = paramObject.getInteger("fulfillmentChannel");
			
		} else {
			fromDate = "2017-04-15";
			toDate = "2017-06-15";
			fulfillmentChannel = 1;
		}
		
		
		responseVo = orderService.computeOrderFinancialData(fromDate, toDate, fulfillmentChannel);
		
		RestUtils.setResponse(response, responseVo);
		
	}
	
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/importPayments")
	public void importPayments(HttpServletRequest request, HttpServletResponse response) {
		String paramString = RestUtils.getRequestParameter(request);
		String filePath = null;
		ResponseVo responseVo = new ResponseVo();
		if (StringUtils.isNotEmpty(paramString)) {
			JSONObject paramObject = JSONObject.parseObject(paramString);
			filePath = paramObject.getString("filePath");
			
		}
		
		filePath = "C:\\Users\\huangzejun\\Desktop\\payments.txt";
		responseVo = orderService.analyzePaymentDetail(filePath);
		RestUtils.setResponse(response, responseVo);
	}
	
	/**
	 * Update cost and first trip fee for each order in table orders
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateCostFirstTripFee")
	public void updateCostFirstTripFee(HttpServletRequest request, HttpServletResponse response) {
		ResponseVo responseVo = new ResponseVo();
		responseVo = orderService.updateCostFirstTripFee();
		RestUtils.setResponse(response, responseVo);
	}
	
	
	@RequestMapping(value="/queryExRate")
	public void queryExRate(HttpServletRequest request, HttpServletResponse response) {
		ResponseVo responseVo = new ResponseVo();
		
		responseVo.setData(financeService.queryExRate()); 
	        
		RestUtils.setResponse(response, responseVo);
		
	}
	
}
