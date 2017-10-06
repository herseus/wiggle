package com.keepsa.utils;

import com.keepsa.enumeration.ResponseCodeEnum;
import com.keepsa.pojo.ResponseVo;

public class ResponseUtils {
	public static ResponseVo getResponseVo(Boolean success, Integer code, String msg, Object data) {
		ResponseVo responseVo = new ResponseVo();
		responseVo.setSuccess(success);
		responseVo.setCode(code);
		responseVo.setMsg(msg);
		responseVo.setData(data);
		return responseVo;
	}
	
	public static ResponseVo getResponseVo(Boolean success, Integer code, String msg) {
		return getResponseVo(success, code, msg, null);
	}
	
	public static ResponseVo getSuccessResponseVo(Object data) {
		return getResponseVo(true, ResponseCodeEnum.SUCCESS.getCode(), "success", data);
	}
	
	public static ResponseVo getFailureResponseVo(Object data) {
		return getResponseVo(true, ResponseCodeEnum.SUCCESS.getCode(), "failure", data);
	}
}
