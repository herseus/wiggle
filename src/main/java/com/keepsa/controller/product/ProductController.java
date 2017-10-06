package com.keepsa.controller.product;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.keepsa.pojo.ProductBaseInfoVo;
import com.keepsa.pojo.ResponseVo;
import com.keepsa.service.ProductServiceImpl;
import com.keepsa.utils.ResponseUtils;
import com.keepsa.utils.RestUtils;

@Controller
@RequestMapping(value = "/product")
public class ProductController {
	@Resource
	private ProductServiceImpl productService;

	/**
	 * Get product details and the related vendors details Support query for one
	 * skus
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getProductInfo")
	public void getProductInfo(HttpServletRequest request, HttpServletResponse response) {
		String paramString = RestUtils.getRequestParameter(request);
		String sku = null;

		if (StringUtils.isNotEmpty(paramString)) {
			paramString = paramString.replaceAll("=", "\":\"").replaceAll("\\s*", "");
			paramString = "{\"" + paramString + "\"}";
			JSONObject paramObject = JSONObject.parseObject(paramString);
			sku = paramObject.getString("sku");
		}

		ResponseVo responseVo = ResponseUtils.getSuccessResponseVo(productService.getProductInfo(sku));

		RestUtils.setResponse(response, responseVo);

	}

	@RequestMapping(value = "/addProductInfo")
	public void addProductInfo(HttpServletRequest request, HttpServletResponse response) {
		String paramString = RestUtils.getRequestParameter(request);
		String sku = null;
		ProductBaseInfoVo productBaseInfoVo = null;

		if (StringUtils.isNotEmpty(paramString)) {
			int indexOfLink = paramString.indexOf("vendorLink=");
			String link = paramString.substring(indexOfLink + 11);
			paramString = paramString.substring(0, indexOfLink + 11);
			paramString = paramString.replaceAll("&", "\",\"").replaceAll("=", "\":\"").replaceAll("\\s*", "");
			paramString = paramString + link;
			paramString = "{\"" + paramString + "\"}";
			JSONObject paramObject = JSONObject.parseObject(paramString);
			productBaseInfoVo = JSONObject.parseObject(paramString, ProductBaseInfoVo.class);
		}

		ResponseVo responseVo = ResponseUtils.getSuccessResponseVo(productService.addProductInfo(productBaseInfoVo));

		RestUtils.setResponse(response, responseVo);

	}

	/**
	 * Get detail info for one product
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getProductDetailInfo")
	public void getProductDetailInfo(HttpServletRequest request, HttpServletResponse response) {
		String paramString = RestUtils.getRequestParameter(request);
		String sku = null;

		if (StringUtils.isNotEmpty(paramString)) {
			paramString = paramString.replaceAll("&", "\",\"").replaceAll("=", "\":\"").replaceAll("\\s*", "");
			paramString = "{\"" + paramString + "\"}";
			JSONObject paramObject = JSONObject.parseObject(paramString);
			sku = paramObject.getString("sku");
		}

		ResponseVo responseVo = ResponseUtils.getSuccessResponseVo(productService.getProductDetailInfo(sku));

		RestUtils.setResponse(response, responseVo);

	}

	@RequestMapping(value = "/importProductFile")
	public void importProductFile(HttpServletRequest request, HttpServletResponse response) {
		// Get the form data
		String paramString = RestUtils.getRequestParameter(request);
		String content = paramString;
		
		ResponseVo responseVo = ResponseUtils.getSuccessResponseVo(productService.importProductFile(content));
		RestUtils.setResponse(response, responseVo);
	}
}
