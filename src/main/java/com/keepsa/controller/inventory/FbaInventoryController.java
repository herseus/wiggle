package com.keepsa.controller.inventory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.keepsa.pojo.ResponseVo;
import com.keepsa.service.FbaInventoryServiceImpl;
import com.keepsa.utils.ResponseUtils;
import com.keepsa.utils.RestUtils;

/**
 * 读取FBA库存文件
 * 
 * @author huangzejun
 *
 */
@Controller
@RequestMapping(value = "/fbaInventory")
public class FbaInventoryController {
	@Resource
	private FbaInventoryServiceImpl fbaInventoryService;

	/**
	 * Please download **Manage FBA Inventory** report
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/readFbaInventoryFile")
	public void readFbaInventoryFile(HttpServletRequest request, HttpServletResponse response) {
		// Get the form data
		String paramString = RestUtils.getRequestParameter(request);
		String content = paramString;
		fbaInventoryService.readFbaInventoryFile(content);
		ResponseVo responseVo = ResponseUtils.getSuccessResponseVo(null);
		RestUtils.setResponse(response, responseVo);
	}

}
