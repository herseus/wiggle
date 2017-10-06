package com.keepsa.pojo;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

/**
 * Base info for one product, default values set as following.
 */
public class ProductBaseInfoVo {
	private String sku = StringUtils.EMPTY;
	private String category = StringUtils.EMPTY;
	private String title = StringUtils.EMPTY;
	private BigDecimal cost = BigDecimal.ZERO;
	private BigDecimal firstTripFee = BigDecimal.ZERO;
	private BigDecimal length = BigDecimal.ZERO;
	private BigDecimal width = BigDecimal.ZERO;
	private BigDecimal height = BigDecimal.ZERO;
	private BigDecimal productWeight = BigDecimal.ZERO;
	private String sizeName = StringUtils.EMPTY;
	private String colorName = StringUtils.EMPTY;
	private Object mainImage = StringUtils.EMPTY;
	private String vendorName = StringUtils.EMPTY;
	private String vendorLink = StringUtils.EMPTY;
	private String fnsku = StringUtils.EMPTY;

	public String getFnsku() {
		return fnsku;
	}

	public void setFnsku(String fnsku) {
		this.fnsku = fnsku;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getProductWeight() {
		return productWeight;
	}

	public void setProductWeight(BigDecimal productWeight) {
		this.productWeight = productWeight;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public Object getMainImage() {
		return mainImage;
	}

	public void setMainImage(Object mainImage) {
		this.mainImage = mainImage;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorLink() {
		return vendorLink;
	}

	public void setVendorLink(String vendorLink) {
		this.vendorLink = vendorLink;
	}

	public BigDecimal getFirstTripFee() {
		return firstTripFee;
	}

	public void setFirstTripFee(BigDecimal firstTripFee) {
		this.firstTripFee = firstTripFee;
	}

}
