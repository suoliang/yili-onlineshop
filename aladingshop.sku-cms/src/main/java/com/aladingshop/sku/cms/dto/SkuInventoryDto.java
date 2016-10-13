package com.aladingshop.sku.cms.dto;

import java.util.Date;

/***
 * 库存商品的查询条件
 * 
 * @author xupeijun
 * 
 */
public class SkuInventoryDto {
	
	
	/** 商品外键编号*/
	private String skuCode;
	
	/** 商品编号*/
	private String skuNo;
	
	/** 商品条形码*/
	private String barCode;
	
	/** 产品编号*/
	private String productCode;
	
	/** 商品名称*/
	private String skuName;
	
	/** 颜色*/
	private String skuColor;
	
	/** 尺寸*/
	private String skuSize;
	
	private String createTimeFrom;
	
	private String createTimeTo;
	
	private Date createTime;

	/** 商品可用量*/
	private Integer availableQty;
	
	/**门店编码*/
	private String storeCode;
	/**门店名称*/
	private String storeName;
	
	

	
	
	

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getSkuColor() {
		return skuColor;
	}

	public void setSkuColor(String skuColor) {
		this.skuColor = skuColor;
	}

	public String getSkuSize() {
		return skuSize;
	}

	public void setSkuSize(String skuSize) {
		this.skuSize = skuSize;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getCreateTimeFrom() {
		return createTimeFrom;
	}

	public void setCreateTimeFrom(String createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}

	public String getCreateTimeTo() {
		return createTimeTo;
	}

	public void setCreateTimeTo(String createTimeTo) {
		this.createTimeTo = createTimeTo;
	}

	public Integer getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(Integer availableQty) {
		this.availableQty = availableQty;
	}

}
