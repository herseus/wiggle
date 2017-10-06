package com.keepsa.pojo;

import java.math.BigDecimal;

public class ExRateVo {
	
	private BigDecimal EURCNY;
	private BigDecimal GBPCNY;
	private BigDecimal JPYCNY;
	private BigDecimal USDCNY;
	private BigDecimal CADCNY;
	private String updateTime;
	
	public BigDecimal getEURCNY() {
		return EURCNY;
	}
	public void setEURCNY(BigDecimal eURCNY) {
		EURCNY = eURCNY;
	}
	public BigDecimal getGBPCNY() {
		return GBPCNY;
	}
	public void setGBPCNY(BigDecimal gBPCNY) {
		GBPCNY = gBPCNY;
	}
	public BigDecimal getJPYCNY() {
		return JPYCNY;
	}
	public void setJPYCNY(BigDecimal jPYCNY) {
		JPYCNY = jPYCNY;
	}
	public BigDecimal getUSDCNY() {
		return USDCNY;
	}
	public void setUSDCNY(BigDecimal uSDCNY) {
		USDCNY = uSDCNY;
	}
	public BigDecimal getCADCNY() {
		return CADCNY;
	}
	public void setCADCNY(BigDecimal cADCNY) {
		CADCNY = cADCNY;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	

}
