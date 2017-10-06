package com.keepsa.enumeration;

public enum ResponseCodeEnum {
	SUCCESS(10001), FAILURE(10002), EXCEPTION(10003);

	private Integer code;

	private ResponseCodeEnum(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

}
