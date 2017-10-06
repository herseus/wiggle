package com.keepsa.controller.order;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.keepsa.pojo.ResponseVo;
import com.keepsa.service.OrderServiceImpl;
import com.keepsa.utils.RestUtils;

@Controller
@RequestMapping(value="/order")
public class OrderQueryController {
	@Resource
	private OrderServiceImpl orderService;
	
	/**
	 * 统计每个SKU前7天每天的销量
	 */
	@RequestMapping(value="/querySales4OneWeek")
	public void querySales4OneWeek(HttpServletRequest request, HttpServletResponse response) {
		String paramString = RestUtils.getRequestParameter(request);
		ResponseVo responseVo = new ResponseVo();
		
		
		
		responseVo = orderService.querySales4OneWeek();
		RestUtils.setResponse(response, responseVo);
	}
}
