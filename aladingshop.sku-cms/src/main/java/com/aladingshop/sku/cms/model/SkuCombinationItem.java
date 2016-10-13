/**
 * 
 */
package com.aladingshop.sku.cms.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mengshaobo
 *优惠组合包含商品
 */
public class SkuCombinationItem {
	private Long id;
	/** 组合编号*/
	private Long combinationId;
	/**商品编号*/
	private String skuCode;
	/** 商品当前售价*/
	private BigDecimal skuPrice;
	/** 组合内排序*/
	private Integer sortOrder;
	/** 组合商品状态:  n 不可用 y 可用*/
	private String status;
	/** 添加日期*/
	private Date createTime;
	/** 修改日期*/
	private Date updateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCombinationId() {
		return combinationId;
	}
	public void setCombinationId(Long combinationId) {
		this.combinationId = combinationId;
	}

	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public BigDecimal getSkuPrice() {
		return skuPrice;
	}
	public void setSkuPrice(BigDecimal skuPrice) {
		this.skuPrice = skuPrice;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
