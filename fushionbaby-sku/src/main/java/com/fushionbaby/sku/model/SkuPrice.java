package com.fushionbaby.sku.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author cyla
 * 
 */
public class SkuPrice {

	private Long id;
	/** 商品编码*/
	private String skuCode;
	/** 成本价 */
	private BigDecimal costPrice;
	/** 预售价,类似于商品未到仓库 但是可以出售了 */
	private BigDecimal prePrice;
	/** 现价 */
	private BigDecimal currentPrice;
	/** 团购价，团购价,拼团参团价格 */
	private BigDecimal togetherPrice;
	/** 市场价 */
	private BigDecimal marketPrice;
	/** 零售价/吊牌价 */
	private BigDecimal retailPrice;
	/** 阿拉丁会员家*/
	private BigDecimal aladingPrice;

	private Date version;
	/** 创建用户id*/
	private Long createId;
	/** 创建时间*/
	private Date createTime;
	/** 更新id*/
	private Long updateId;
	/** 更新时间*/
	private Date updateTime;
	/**门店编码*/
	private String storeCode;
	

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getPrePrice() {
		return prePrice;
	}

	public void setPrePrice(BigDecimal prePrice) {
		this.prePrice = prePrice;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public BigDecimal getTogetherPrice() {
		return togetherPrice;
	}

	public void setTogetherPrice(BigDecimal togetherPrice) {
		this.togetherPrice = togetherPrice;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	
	

	public BigDecimal getAladingPrice() {
		return aladingPrice;
	}

	public void setAladingPrice(BigDecimal aladingPrice) {
		this.aladingPrice = aladingPrice;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
