package com.keepsa.pojo;

import java.math.BigDecimal;

/**
 * VO for order table.
 * 
 * @author huangzejun
 *
 */
public class OrderVo {
	private String orderId;
	private String merchantOrderId; // optional for fbm
	private String purchaseDate;
	private String lastUpdatedDate;
	private String orderStatus;
	private String fulfillmentChannel; // FBA OR FBM
	private String salesChannel; // SOLD BY WHICH COUNTRY
	private String sku;
	private String targetSku;
	private String asin;
	private String itemStatus;
	private Integer quantity;
	private String currency;
	private BigDecimal itemPrice;
	private BigDecimal shippingPrice;
	private BigDecimal itemPromotionDiscount;
	private BigDecimal shipPromotionDiscount;
	private BigDecimal cost;
	private BigDecimal firstTripFee;
	private BigDecimal fbaFee;
	private BigDecimal commission;
	private BigDecimal shippingChargeback;
	private BigDecimal refundCommission; // only valid for refunded order
	private String shipCountry;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMerchantOrderId() {
		return merchantOrderId;
	}

	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getFulfillmentChannel() {
		return fulfillmentChannel;
	}

	public void setFulfillmentChannel(String fulfillmentChannel) {
		this.fulfillmentChannel = fulfillmentChannel;
	}

	public String getSalesChannel() {
		return salesChannel;
	}

	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

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

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public BigDecimal getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(BigDecimal shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public BigDecimal getItemPromotionDiscount() {
		return itemPromotionDiscount;
	}

	public void setItemPromotionDiscount(BigDecimal itemPromotionDiscount) {
		this.itemPromotionDiscount = itemPromotionDiscount;
	}

	public BigDecimal getShipPromotionDiscount() {
		return shipPromotionDiscount;
	}

	public void setShipPromotionDiscount(BigDecimal shipPromotionDiscount) {
		this.shipPromotionDiscount = shipPromotionDiscount;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getFirstTripFee() {
		return firstTripFee;
	}

	public void setFirstTripFee(BigDecimal firstTripFee) {
		this.firstTripFee = firstTripFee;
	}

	public BigDecimal getFbaFee() {
		return fbaFee;
	}

	public void setFbaFee(BigDecimal fbaFee) {
		this.fbaFee = fbaFee;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getShippingChargeback() {
		return shippingChargeback;
	}

	public void setShippingChargeback(BigDecimal shippingChargeback) {
		this.shippingChargeback = shippingChargeback;
	}

	public BigDecimal getRefundCommission() {
		return refundCommission;
	}

	public void setRefundCommission(BigDecimal refundCommission) {
		this.refundCommission = refundCommission;
	}

	public String getShipCountry() {
		return shipCountry;
	}

	public void setShipCountry(String shipCountry) {
		this.shipCountry = shipCountry;
	}

}
