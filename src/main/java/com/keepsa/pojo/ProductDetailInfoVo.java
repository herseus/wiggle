package com.keepsa.pojo;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

/**
 * Table product
 * 
 * @author huangzejun
 *
 */
public class ProductDetailInfoVo {
	private String sku = StringUtils.EMPTY;
	private String category = StringUtils.EMPTY;
	private String title = StringUtils.EMPTY;
	private String customsApplicationTitle = StringUtils.EMPTY;
	private String productParameter = StringUtils.EMPTY;
	private BigDecimal cost = BigDecimal.ZERO;
	private BigDecimal firstTripFee = BigDecimal.ZERO;
	private BigDecimal length = BigDecimal.ZERO;
	private BigDecimal width = BigDecimal.ZERO;
	private BigDecimal height = BigDecimal.ZERO;
	private BigDecimal productWeight = BigDecimal.ZERO;
	private BigDecimal packageWeight = BigDecimal.ZERO;
	private BigDecimal volumeWeight = BigDecimal.ZERO;
	private String sizeName = StringUtils.EMPTY;
	private String colorName = StringUtils.EMPTY;
	private String vendorName = StringUtils.EMPTY;
	private String vendorLink = StringUtils.EMPTY;
	private String englishTitle = StringUtils.EMPTY;
	private String bulletPoint1 = StringUtils.EMPTY;
	private String bulletPoint2 = StringUtils.EMPTY;
	private String bulletPoint3 = StringUtils.EMPTY;
	private String bulletPoint4 = StringUtils.EMPTY;
	private String bulletPoint5 = StringUtils.EMPTY;
	private String productDescription = StringUtils.EMPTY;
	private String searchTerms = StringUtils.EMPTY;
	private String productStatus = StringUtils.EMPTY;
	private String mainImageUrl = StringUtils.EMPTY;
	private String otherImageUrl1 = StringUtils.EMPTY;
	private String otherImageUrl2 = StringUtils.EMPTY;
	private String otherImageUrl3 = StringUtils.EMPTY;
	private String otherImageUrl4 = StringUtils.EMPTY;
	private String otherImageUrl5 = StringUtils.EMPTY;
	private String otherImageUrl6 = StringUtils.EMPTY;
	private String otherImageUrl7 = StringUtils.EMPTY;
	private String otherImageUrl8 = StringUtils.EMPTY;
	private BigDecimal priceUs = BigDecimal.ZERO;
	private BigDecimal priceCa = BigDecimal.ZERO;
	private BigDecimal priceDe = BigDecimal.ZERO;
	private BigDecimal priceFr = BigDecimal.ZERO;
	private BigDecimal priceIt = BigDecimal.ZERO;
	private BigDecimal priceEs = BigDecimal.ZERO;
	private BigDecimal priceUk = BigDecimal.ZERO;
	private BigDecimal priceJp = BigDecimal.ZERO;
	private String hsCode = StringUtils.EMPTY;
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
	public String getCustomsApplicationTitle() {
		return customsApplicationTitle;
	}
	public void setCustomsApplicationTitle(String customsApplicationTitle) {
		this.customsApplicationTitle = customsApplicationTitle;
	}
	public String getProductParameter() {
		return productParameter;
	}
	public void setProductParameter(String productParameter) {
		this.productParameter = productParameter;
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
	public BigDecimal getPackageWeight() {
		return packageWeight;
	}
	public void setPackageWeight(BigDecimal packageWeight) {
		this.packageWeight = packageWeight;
	}
	public BigDecimal getVolumeWeight() {
		return volumeWeight;
	}
	public void setVolumeWeight(BigDecimal volumeWeight) {
		this.volumeWeight = volumeWeight;
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
	public String getEnglishTitle() {
		return englishTitle;
	}
	public void setEnglishTitle(String englishTitle) {
		this.englishTitle = englishTitle;
	}
	public String getBulletPoint1() {
		return bulletPoint1;
	}
	public void setBulletPoint1(String bulletPoint1) {
		this.bulletPoint1 = bulletPoint1;
	}
	public String getBulletPoint2() {
		return bulletPoint2;
	}
	public void setBulletPoint2(String bulletPoint2) {
		this.bulletPoint2 = bulletPoint2;
	}
	public String getBulletPoint3() {
		return bulletPoint3;
	}
	public void setBulletPoint3(String bulletPoint3) {
		this.bulletPoint3 = bulletPoint3;
	}
	public String getBulletPoint4() {
		return bulletPoint4;
	}
	public void setBulletPoint4(String bulletPoint4) {
		this.bulletPoint4 = bulletPoint4;
	}
	public String getBulletPoint5() {
		return bulletPoint5;
	}
	public void setBulletPoint5(String bulletPoint5) {
		this.bulletPoint5 = bulletPoint5;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getSearchTerms() {
		return searchTerms;
	}
	public void setSearchTerms(String searchTerms) {
		this.searchTerms = searchTerms;
	}
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	public String getMainImageUrl() {
		return mainImageUrl;
	}
	public void setMainImageUrl(String mainImageUrl) {
		this.mainImageUrl = mainImageUrl;
	}
	public String getOtherImageUrl1() {
		return otherImageUrl1;
	}
	public void setOtherImageUrl1(String otherImageUrl1) {
		this.otherImageUrl1 = otherImageUrl1;
	}
	public String getOtherImageUrl2() {
		return otherImageUrl2;
	}
	public void setOtherImageUrl2(String otherImageUrl2) {
		this.otherImageUrl2 = otherImageUrl2;
	}
	public String getOtherImageUrl3() {
		return otherImageUrl3;
	}
	public void setOtherImageUrl3(String otherImageUrl3) {
		this.otherImageUrl3 = otherImageUrl3;
	}
	public String getOtherImageUrl4() {
		return otherImageUrl4;
	}
	public void setOtherImageUrl4(String otherImageUrl4) {
		this.otherImageUrl4 = otherImageUrl4;
	}
	public String getOtherImageUrl5() {
		return otherImageUrl5;
	}
	public void setOtherImageUrl5(String otherImageUrl5) {
		this.otherImageUrl5 = otherImageUrl5;
	}
	public String getOtherImageUrl6() {
		return otherImageUrl6;
	}
	public void setOtherImageUrl6(String otherImageUrl6) {
		this.otherImageUrl6 = otherImageUrl6;
	}
	public String getOtherImageUrl7() {
		return otherImageUrl7;
	}
	public void setOtherImageUrl7(String otherImageUrl7) {
		this.otherImageUrl7 = otherImageUrl7;
	}
	public String getOtherImageUrl8() {
		return otherImageUrl8;
	}
	public void setOtherImageUrl8(String otherImageUrl8) {
		this.otherImageUrl8 = otherImageUrl8;
	}
	public BigDecimal getPriceUs() {
		return priceUs;
	}
	public void setPriceUs(BigDecimal priceUs) {
		this.priceUs = priceUs;
	}
	public BigDecimal getPriceCa() {
		return priceCa;
	}
	public void setPriceCa(BigDecimal priceCa) {
		this.priceCa = priceCa;
	}
	public BigDecimal getPriceDe() {
		return priceDe;
	}
	public void setPriceDe(BigDecimal priceDe) {
		this.priceDe = priceDe;
	}
	public BigDecimal getPriceFr() {
		return priceFr;
	}
	public void setPriceFr(BigDecimal priceFr) {
		this.priceFr = priceFr;
	}
	public BigDecimal getPriceIt() {
		return priceIt;
	}
	public void setPriceIt(BigDecimal priceIt) {
		this.priceIt = priceIt;
	}
	public BigDecimal getPriceEs() {
		return priceEs;
	}
	public void setPriceEs(BigDecimal priceEs) {
		this.priceEs = priceEs;
	}
	public BigDecimal getPriceUk() {
		return priceUk;
	}
	public void setPriceUk(BigDecimal priceUk) {
		this.priceUk = priceUk;
	}
	public BigDecimal getPriceJp() {
		return priceJp;
	}
	public void setPriceJp(BigDecimal priceJp) {
		this.priceJp = priceJp;
	}
	public String getHsCode() {
		return hsCode;
	}
	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}

	
}
