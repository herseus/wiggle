package com.keepsa.controller.order;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.keepsa.pojo.ResponseVo;
import com.keepsa.service.FbmOrderServiceImpl;
import com.keepsa.utils.ResponseUtils;
import com.keepsa.utils.RestUtils;

/**
 * Deal with FBM orders
 * 
 * @author huangzejun
 *
 */
@Controller
@RequestMapping(value = "/fbmorder")
public class FbmOrderController {
	@Resource
	private FbmOrderServiceImpl fbmOrderService;

	/**
	 * Please download Amazon Order Reports(plain text file, UTF-8 encoding)
	 * from ORDERS > Order > Reports > New Orders
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/uploadFbmOrders")
	public void uploadFbmOrders(HttpServletRequest request, HttpServletResponse response) {
		// Get the form data
		String paramString = RestUtils.getRequestParameter(request);
		// Skip the substring "content=data:text/plain;base64,"
		// String content =
		// TransformUtils.decodeBase64String(paramString.substring(31));

		/**
		 * The first line is like this:
		 * content=order-id	order-item-id	purchase-date	payments-date	buyer-email	buyer-name	buyer-phone-number	sku	product-name	quantity-purchased	currency	item-price	item-tax	shipping-price	shipping-tax	ship-service-level	recipient-name	ship-address-1	ship-address-2	ship-address-3	ship-city	ship-state	ship-postal-code	ship-country	ship-phone-number	delivery-start-date	delivery-end-date	delivery-time-zone	delivery-Instructions	sales-channel
	114-0149180-7966659	59686379847882	2017-09-23T14:18:23-07:00	2017-09-23T14:18:23-07:00	zjzhk7tmf7ts1q1@marketplace.amazon.com	Roger Willoughby	270-929-3817	AUSC2A13-02	Mens Summer Cotton No Embroidery Visor Bucket Hats Fisherman Hat Outdoor Climbing	1	USD	9.88	0.00	0.00	0.00	Standard	Roger Willoughby	728 Canterbury Rd			Owensboro	KY	42303	US	270-929-3817					Amazon.com

		 */
		String content = paramString;

		fbmOrderService.importOrderReports(content);
		ResponseVo responseVo = ResponseUtils.getSuccessResponseVo(null);
		RestUtils.setResponse(response, responseVo);
	}

	/**
	 * Generate unshipped FBM orders in Excel file
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/generateUnshippedFbmOrderExcelFile")
	public void generateUnshippedFbmOrderExcelFile(HttpServletRequest request, HttpServletResponse response) {
		ResponseVo responseVo = new ResponseVo();
		response.setCharacterEncoding("utf-8");
		fbmOrderService.generateUnshippedFbmOrderXlsxFile(response);
		RestUtils.setResponse(response, responseVo);
	}

	/**
	 * Set unshipped FBM orders to be shipped
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/setUnshippedFbmOrderShipped")
	public void setUnshippedFbmOrderShipped(HttpServletRequest request, HttpServletResponse response) {

		ResponseVo responseVo = fbmOrderService.setUnshippedFbmOrderShipped();
		RestUtils.setResponse(response, responseVo);
	}

	
	/**
	 * Query unshipped FBM orders
	 */
	@RequestMapping(value = "/queryUnshippedFbmOrders")
	public void queryUnshippedFbmOrders(HttpServletRequest request, HttpServletResponse response) {

		ResponseVo responseVo = fbmOrderService.queryUnshippedFbmOrders();
		RestUtils.setResponse(response, responseVo);
	}
	
	
}
