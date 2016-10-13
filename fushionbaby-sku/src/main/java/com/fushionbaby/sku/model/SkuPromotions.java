/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月8日下午7:55:32
 */
package com.fushionbaby.sku.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月8日下午7:55:32
 */
public class SkuPromotions {
	
	private Long id;
	/** 商品编号*/
	private String skuCode;
	
	/** 活动商品状态*/
	private String skuPromotionsStatus;
	
	/** 特价*/
	private BigDecimal specialPrice;
	
	/** 活动编号*/
	private String pmCode;
	
	/** 显示顺序*/
	private Integer sort;
	
	/** 限制数量*/
	private Integer limitCount;
	
	private Date createTime;
	
	private Long createId;
	
	

	
	public Integer getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
	}

	public String getPmCode() {
		return pmCode;
	}

	public void setPmCode(String pmCode) {
		this.pmCode = pmCode;
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

	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	
	
	
	
}
