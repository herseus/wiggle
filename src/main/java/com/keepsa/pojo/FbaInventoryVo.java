package com.keepsa.pojo;

public class FbaInventoryVo {
	private String targetSku;
	private String asin;
	private String conditionType;
	private String warehouseConditionCode;
	private Integer quantityAvailable;

	public String getTargetSku() {
		return targetSku;
	}

	public void setTargetSku(String targetSku) {
		this.targetSku = targetSku;
	}

	public String getAsin() {
		return asin;
	}

	public void setAsin(String asin) {
		this.asin = asin;
	}

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public String getWarehouseConditionCode() {
		return warehouseConditionCode;
	}

	public void setWarehouseConditionCode(String warehouseConditionCode) {
		this.warehouseConditionCode = warehouseConditionCode;
	}

	public Integer getQuantityAvailable() {
		return quantityAvailable;
	}

	public void setQuantityAvailable(Integer quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}

}
