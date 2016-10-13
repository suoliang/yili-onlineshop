package com.aladingshop.store.sku.model;

import java.util.Date;

public class StoreSkuInventory {

	private Long id;
	/** 门店编码code */
	private String storeCode;
	/** 商品code*/
	private String skuCode;
	/** 产品编码code */
	private String productCode;
	/** 商品可用量(库存量) */
	private Integer availableQty;
	/** 尺寸（对于服装类商品，最终SKU=商品+尺寸，因此库存信息一定需要此信息） */
	private String skuSize;
	/** 商品颜色*/
	private String skuColor;
	/** 更新id */
	private Long updateId;
	/** 更新时间 */
	private Date updateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public Integer getAvailableQty() {
		return availableQty;
	}
	public void setAvailableQty(Integer availableQty) {
		this.availableQty = availableQty;
	}
	public String getSkuSize() {
		return skuSize;
	}
	public void setSkuSize(String skuSize) {
		this.skuSize = skuSize;
	}
	public String getSkuColor() {
		return skuColor;
	}
	public void setSkuColor(String skuColor) {
		this.skuColor = skuColor;
	}
	public Long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
