/**
 * 
 */
package com.fushionbaby.sku.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mengshaobo
 *商品限时抢购
 */
public class SkuTimelimit {
	
	private Long id;
	/**商品编号*/
	private String skuCode;
	/** 商品名称*/
	private String skuName;
	/**状态*/
	private String skuStatus;
	/**限购数量*/
	private Integer placeNum;
	/**下架时间*/
	private Date offshelvestime;
	/** 上架时间*/
	private Date onshelvestime;
	/** 过期后的价格*/
	private BigDecimal limitPrice;
	/**创建时间*/
	private Date createTime;
	/** 修改时间*/
	private Date updateTime;
	/** 创建Id*/
	private Long createId;
	/** 修改Id*/
	private Long updateId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}


	public String getSkuStatus() {
		return skuStatus;
	}

	public void setSkuStatus(String skuStatus) {
		this.skuStatus = skuStatus;
	}

	public Integer getPlaceNum() {
		return placeNum;
	}

	public void setPlaceNum(Integer placeNum) {
		this.placeNum = placeNum;
	}

	public Date getOffshelvestime() {
		return offshelvestime;
	}

	public void setOffshelvestime(Date offshelvestime) {
		this.offshelvestime = offshelvestime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(BigDecimal limitPrice) {
		this.limitPrice = limitPrice;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public Date getOnshelvestime() {
		return onshelvestime;
	}

	public void setOnshelvestime(Date onshelvestime) {
		this.onshelvestime = onshelvestime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	
	
	
}
