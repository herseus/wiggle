package com.keepsa.pojo;

import java.math.BigDecimal;

public class ExRateMapper {
	private ExRateVo exRateVo;
	public ExRateVo getExRateVo() {
		return exRateVo;
	}

	public void setExRateVo(ExRateVo exRateVo) {
		this.exRateVo = exRateVo;
	}

	public ExRateMapper(ExRateVo exRateVo) {
		this.exRateVo = exRateVo;
	}
	
	public BigDecimal getExRateToCNY(String country) {
		switch (country) {
		case "DE":
		case "FR":
		case "IT":
		case "ES":
			return exRateVo.getEURCNY();
		case "GB":
			return exRateVo.getGBPCNY();
		case "JP":
			return exRateVo.getJPYCNY();
		case "US":
			return exRateVo.getUSDCNY();
		case "CA":
			return exRateVo.getCADCNY();
		default:
			return BigDecimal.ZERO;
		}
	}
}
