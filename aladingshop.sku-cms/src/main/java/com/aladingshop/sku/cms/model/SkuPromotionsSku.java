package com.aladingshop.sku.cms.model;

import java.math.BigDecimal;
import java.util.Date;

public class SkuPromotionsSku {
	private Long id;
	/** 商品唯一标示码*/
	private String skuCode;
	/** 活动商品状态（启用，禁用）*/
	private String skuPromotionsStatus;
	/** 特价*/
	private BigDecimal specialPrice;
	/** 活动code*/
	private String pmCode;
	/** 显示顺序*/
	private Long sort;
	private Date createTime;
	private Long createId;
	private Integer limitCount;
	
	
	public Integer getLimitCount() {
		return limitCount;
	}
	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
	}
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
	public String getSkuPromotionsStatus() {
		return skuPromotionsStatus;
	}
	public void setSkuPromotionsStatus(String skuPromotionsStatus) {
		this.skuPromotionsStatus = skuPromotionsStatus;
	}
	public BigDecimal getSpecialPrice() {
		return specialPrice;
	}
	public void setSpecialPrice(BigDecimal specialPrice) {
		this.specialPrice = specialPrice;
	}
	public String getPmCode() {
		return pmCode;
	}
	public void setPmCode(String pmCode) {
		this.pmCode = pmCode;
	}
	public Long getSort() {
		return sort;
	}
	public void setSort(Long sort) {
		this.sort = sort;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	

	
}
