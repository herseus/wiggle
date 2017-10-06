package com.keepsa.enumeration;

public enum FBMOrderStatusEnum {
	Unshipped(1),

	Shipped(2);

	private Integer status;

	private FBMOrderStatusEnum(Integer status) {
		this.setStatus(status);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
