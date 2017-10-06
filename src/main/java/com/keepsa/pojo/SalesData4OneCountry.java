package com.keepsa.pojo;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

public class SalesData4OneCountry {
	private String countryName = StringUtils.EMPTY;
	private Integer numOfOrders = 0;
	private Integer numOfItems = 0;
	private BigDecimal grossSales = BigDecimal.ZERO;
	private BigDecimal grossProfit = BigDecimal.ZERO;
	private BigDecimal grossCost = BigDecimal.ZERO;
	private BigDecimal grossFirstTripFee = BigDecimal.ZERO;
	private BigDecimal grossFBAFee = BigDecimal.ZERO;
	private BigDecimal grossCommission = BigDecimal.ZERO;

	public Integer getNumOfOrders() {
		return numOfOrders;
	}

	public void setNumOfOrders(Integer numOfOrders) {
		this.numOfOrders = numOfOrders;
	}

	public BigDecimal getGrossFirstTripFee() {
		return grossFirstTripFee;
	}

	public void setGrossFirstTripFee(BigDecimal grossFirstTripFee) {
		this.grossFirstTripFee = grossFirstTripFee;
	}

	public BigDecimal getGrossFBAFee() {
		return grossFBAFee;
	}

	public void setGrossFBAFee(BigDecimal grossFBAFee) {
		this.grossFBAFee = grossFBAFee;
	}

	public BigDecimal getGrossCommission() {
		return grossCommission;
	}

	public void setGrossCommission(BigDecimal grossCommission) {
		this.grossCommission = grossCommission;
	}

	public Integer getNumOfItems() {
		return numOfItems;
	}

	public void setNumOfItems(Integer numOfItems) {
		this.numOfItems = numOfItems;
	}

	public BigDecimal getGrossSales() {
		return grossSales;
	}

	public void setGrossSales(BigDecimal grossSales) {
		this.grossSales = grossSales;
	}

	public BigDecimal getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(BigDecimal grossProfit) {
		this.grossProfit = grossProfit;
	}

	public BigDecimal getGrossCost() {
		return grossCost;
	}

	public void setGrossCost(BigDecimal grossCost) {
		this.grossCost = grossCost;
	}

	/*
	 * Operations for addition
	 */
	public void addToNumOfOrders(Integer num) {
		numOfOrders += num;
	}

	public void addToNumOfItems(Integer num) {
		numOfItems += num;
	}

	public void addToGrossSales(BigDecimal sales) {
		grossSales = grossSales.add(sales);
	}

	public void addToGrossProfit(BigDecimal profit) {
		grossProfit = grossProfit.add(profit);
	}

	public void addToGrossCost(BigDecimal cost) {
		grossCost = grossCost.add(cost);
	}
	
	public void addToGrossFirstTripFee(BigDecimal firstTripFee) {
		grossFirstTripFee = grossFirstTripFee.add(firstTripFee);
	}
	
	public void addToGrossFBAFee(BigDecimal fbaFee) {
		grossFBAFee = grossFBAFee.add(fbaFee);
	}
	
	public void addToGrossCommission(BigDecimal commission) {
		grossCommission = grossCommission.add(commission);
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	public SalesData4OneCountry(String countryName) {
		this.countryName = countryName;
	}

}
