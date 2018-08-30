package com.keepsa.controller.product;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.keepsa.pojo.ResponseVo;
import com.keepsa.service.ProductServiceImpl;
import com.keepsa.utils.RestUtils;

@Controller
@RequestMapping(value="/product")
public class PurchaseController {
	
	@Resource
	private ProductServiceImpl productService;
	
	/**
	 * Create purchase plan
	 */
	@RequestMapping(value="/createPurchasePlan")
	public void createPurchasePlan(HttpServletRequest request, HttpServletResponse response) {
		String paramString = RestUtils.getRequestParameter(request);
		String filePath = null;
		ResponseVo responseVo = new ResponseVo();
		if (StringUtils.isNotEmpty(paramString)) {
			JSONObject paramObject = JSONObject.parseObject(paramString);
			filePath = paramObject.getString("filePath");
			
		}
		
		filePath = "C:\\Users\\huangzejun\\Desktop\\purchase.txt";
		responseVo = productService.createPurchasePlan(filePath);
		RestUtils.setResponse(response, responseVo);
	}
}
