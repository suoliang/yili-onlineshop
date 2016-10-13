package com.aladingshop.store.sku.model;

import java.util.Date;

public class StoreSkuImage {
	/** 门店商品图片信息id */
	private Long id;
	/** 门店编码code*/
	private String storeCode;
	/** 商品code */
	private String skuCode;
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
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	
	
	
	
}
