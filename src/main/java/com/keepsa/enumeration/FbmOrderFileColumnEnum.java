package com.keepsa.enumeration;

public enum FbmOrderFileColumnEnum {
	OrderId("Order Id", 0),
	PurchaseDate("Purchase Date", 1),
	Sku("Sku", 2),
	Image("Image", 3),
	Title("Title", 4),
	Qty("Qty", 5),
	Status("Status", 6),
	RecipientName("Recipient Name", 7),
	ShipAddress("Ship Address", 8),
	ShipCity("Ship City", 9),
	ShipState("Ship State", 10),
	ShipPostalCode("Ship Postal Code", 11),
	ShipCountry("Ship Country", 12),
	ShipPhoneNumber("Ship Phone Number", 13),
	CustomsName("Customs Name", 14),
	HSCode("HS Code", 15),
	Cost("Cost(USD)", 16),
	Weight("Weight(g)", 17),
	DeliveryMethod("运输方式", 18),
	TrackingId("Tracking Id", 19);
	
	private String name;
	private Integer index;

	private FbmOrderFileColumnEnum(String name, Integer index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public Integer getIndex() {
		return index;
	}

}
