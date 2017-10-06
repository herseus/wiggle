package com.keepsa.enumeration;

public enum FBMOrderColumnIndexEnum {
	OrderId(0),
	OrderItemId(1),
	PurchaseDate(2),
	PaymentsDate(3),
	BuyerEmail(4),
	BuyerName(5),
	BuyerPhoneNumber(6),
	Sku(7),
	ProductName(8),
	QuantityPurchased(9),
	Currency(10),
	ItemPrice(11),
	ItemTax(12),
	ShippingPrice(13),
	ShippingTax(14),
	ShipServiceLevel(15),
	RecipientName(16),
	ShipAddress1(17),
	ShipAddress2(18),
	ShipAddress3(19),
	ShipCity(20),
	ShipState(21),
	ShipPostalCode(22),
	ShipCountry(23),
	ShipPhoneNumber(24),
	DeliveryStartDate(25),
	DeliveryEndDate(26),
	DeliveryTimeZone(27),
	DeliveryInstructions(28),
	SalesChannel(29);

	private Integer index;

	private FBMOrderColumnIndexEnum(Integer index) {
		this.setIndex(index);
	}

	public Integer getIndex() {
		return index;
	}

	private void setIndex(Integer index) {
		this.index = index;
	}
}
