package com.fushionbaby.sku.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @description 活动信息表
 * @author 孟少博
 * @date 2015年9月9日上午11:18:27
 */
public class SkuPromotionsInfo {

	/**
	 * 活动 编号
	 */
	private Long pmid;
	
	/** 活动名称*/
	private String promotionsName;
	
	/** 活动编号*/
	private String promotionsCode;
	
	/** 开始日期*/
	private Date startDate;
	
	/** 结束日期*/
	private Date endDate;
	
	/** 时间范围*/
	private String timeRange;
	
	/** 是否重复*/
	private String isRepeatBuy;
	
	/** 活动价格*/
	private BigDecimal salesPrice;
	
	private Date createTime;
	
	private Date updateTime;

	
	
	public String getPromotionsCode() {
		return promotionsCode;
	}

	public void setPromotionsCode(String promotionsCode) {
		this.promotionsCode = promotionsCode;
	}

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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
	
}
