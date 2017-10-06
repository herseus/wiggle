package com.keepsa.enumeration;

public enum FulfillmentChannelEnum {
	Amazon(1), Merchant(2), All(3);

	private Integer code;

	private FulfillmentChannelEnum(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}
}
