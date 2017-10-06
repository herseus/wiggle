package com.keepsa.enumeration;

public enum ExRateEnum {
	EURCNY("EURCNY"), GBPCNY("GBPCNY"), JPYCNY("JPYCNY"), USDCNY("USDCNY"), CADCNY("CADCNY");
	
	private String name;

	public String getName() {
		return name;
	}

	private ExRateEnum(String name) {
		this.name = name;
	}
}
