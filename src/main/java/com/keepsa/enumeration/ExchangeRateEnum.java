package com.keepsa.enumeration;

import java.math.BigDecimal;

public enum ExchangeRateEnum {
	GBP2RMB(new BigDecimal(8.94)),
	EUR2RMB(new BigDecimal(7.73)),
	USD2RMB(new BigDecimal(6.89)),
	CAD2RMB(new BigDecimal(5.10)),
	JPY2RMB(new BigDecimal(0.06));
	
	private BigDecimal exchangeRate;
	
	private ExchangeRateEnum(BigDecimal exRate) {
		this.exchangeRate = exRate;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
}
