package com.keepsa.enumeration;

/**
 * Amazon订单项
 * 
 * @author huangzejun
 *
 */
public enum AmazonOrderColIndexEnum {
	OrderId(0),
	MerchantOrderId(1),
	PurchaseDate(2),
	LastUpdatedDate(3),
	OrderStatus(4),
	FulfillmentChannel(5),
	SalesChannel(6),
	OrderChannel(7),
	Url(8),
	ShipServiceLevel(9),
	ProductName(10),
	Sku(11),
	Asin(12),
	ItemStatus(13),
	Quantity(14),
	Currency(15),
	ItemPrice(16),
	ItemTax(17),
	ShippingPrice(18),
	ShippingTax(19),
	GiftWrapPrice(20),
	GiftWrapTax(21),
	ItemPromotionDiscount(22),
	ShipPromotionDiscount(23),
	ShipCity(24),
	ShipState(25),
	ShipPostalCode(26),
	ShipCountry(27),
	PromotionIds(28);

	private Integer index;
	
	private AmazonOrderColIndexEnum(Integer index) {
		this.index = index;
	}
	public Integer getIndex() {
		return index;
	}
}
