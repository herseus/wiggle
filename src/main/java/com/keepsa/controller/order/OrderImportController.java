package com.keepsa.controller.order;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.keepsa.pojo.ResponseVo;
import com.keepsa.service.OrderServiceImpl;
import com.keepsa.utils.RestUtils;

/**
 * #1 全量导入数据(By Order Date)
 * #2 增量导入昨天的数据(By Last Updated Date)
 * 
 * @author huangzejun
 *
 */
@Controller
@RequestMapping(value="/order")
public class OrderImportController {
	@Resource
	private OrderServiceImpl orderService;
	
	/**
	 * Initialization of the orders table.
	 * This method should only be called once and not any more!
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/insertOrder")
	public void insertOrder(HttpServletRequest request, HttpServletResponse response) {
		String paramString = RestUtils.getRequestParameter(request);
		String filePath = null;
		ResponseVo responseVo = new ResponseVo();
		if (StringUtils.isNotEmpty(paramString)) {
			JSONObject paramObject = JSONObject.parseObject(paramString);
			filePath = paramObject.getString("filePath");
			
		}
		
		filePath = "C:\\Users\\huangzejun\\Desktop\\InitialOrders.txt";
		responseVo = orderService.importOrder(filePath);
		RestUtils.setResponse(response, responseVo);
	}
	
	/**
	 * This method can be called many times.
	 * It will update existing orders and add new orders.
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/insertOrUpdateOrder")
	public void insertOrUpdateOrder(HttpServletRequest request, HttpServletResponse response) {
		String paramString = RestUtils.getRequestParameter(request);
		String filePath = null;
		ResponseVo responseVo = new ResponseVo();
		if (StringUtils.isNotEmpty(paramString)) {
			JSONObject paramObject = JSONObject.parseObject(paramString);
			filePath = paramObject.getString("filePath");
			
		}
		
		filePath = "C:\\Users\\huangzejun\\Desktop\\order.txt";
		responseVo = orderService.insertOrUpdateOrder(filePath);
		RestUtils.setResponse(response, responseVo);
		
	}
}
