package com.aladingshop.store.sku.model;

import java.math.BigDecimal;
import java.util.Date;

public class StoreSkuPrice {

	/** 门店商品id */
	private Long id;
	/** 门店编码code */
	private String storeCode;
	/** 商品code*/
	private String skuCode;
	/** 成本价 */
	private BigDecimal costPrice;
	/** 市场价 */
	private BigDecimal marketPrice;
	/** 零售价/吊牌价 */
	private BigDecimal retailPrice;
	/** 阿拉丁会员家*/
	private BigDecimal aladingPrice;
	/** 阿拉丁会员家*/
	private BigDecimal currentPrice;
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
	public BigDecimal getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public BigDecimal getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}
	public BigDecimal getAladingPrice() {
		return aladingPrice;
	}
	public void setAladingPrice(BigDecimal aladingPrice) {
		this.aladingPrice = aladingPrice;
	}
	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}
	
}
