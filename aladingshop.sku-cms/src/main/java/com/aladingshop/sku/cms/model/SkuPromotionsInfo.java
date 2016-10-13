package com.aladingshop.sku.cms.model;

import java.math.BigDecimal;
import java.util.Date;

public class SkuPromotionsInfo {
	private Long pmid;
	/** 活动编码 */
	private String promotionsCode;
	/** 活动名称 */
	private String promotionsName;
	private Date startDate;
	private Date endDate;
	/** 活动时间范围*/
	private String timeRange;
	/** 是否重复购买*/
	private String isRepeatBuy;
	/** 活动统一价格*/
	private BigDecimal salesPrice;
	private Date createTime;
	private Long createId;
	private Date updateTime;
	private Long updateId;
	public Long getPmid() {
		return pmid;
	}
	public void setPmid(Long pmid) {
		this.pmid = pmid;
	}
	public String getPromotionsName() {
		return promotionsName;
	}
	public void setPromotionsName(String promotionsName) {
		this.promotionsName = promotionsName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getTimeRange() {
		return timeRange;
	}
	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
	}
	public String getIsRepeatBuy() {
		return isRepeatBuy;
	}
	public void setIsRepeatBuy(String isRepeatBuy) {
		this.isRepeatBuy = isRepeatBuy;
	}
	public BigDecimal getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	public String getPromotionsCode() {
		return promotionsCode;
	}
	public void setPromotionsCode(String promotionsCode) {
		this.promotionsCode = promotionsCode;
	}
	
	
	
}
