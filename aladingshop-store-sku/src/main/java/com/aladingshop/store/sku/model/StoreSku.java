package com.aladingshop.store.sku.model;

import java.util.Date;

public class StoreSku {

	/** 门店商品id */
	private Long id;
	/** 门店编码code */
	private String storeCode;
	/** 商品code*/
	private String skuCode;
	/** 是否是门店自营的商品（y表示是，n表示否） */
	private String isSupport;
	/** 更新id */
	private Long updateId;
	/** 更新时间 */
	private Date updateTime;
	/** 商品状态，默认是1.未上架2.已上架3.已下架*/
	private String status;
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
	public String getIsSupport() {
		return isSupport;
	}
	public void setIsSupport(String isSupport) {
		this.isSupport = isSupport;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
