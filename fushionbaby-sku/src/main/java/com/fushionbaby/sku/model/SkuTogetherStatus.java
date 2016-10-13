/**
 * 
 */
package com.fushionbaby.sku.model;

import java.util.Date;

/**
 * @author mengshaobo 商品基本信息
 */
public class SkuTogetherStatus {

	/** id */
	private Long id;
	/** 商品团购Id */
	private Long skuTogetherId;
	/** 团购状态 */
	private String status;
	/** 团购最少人数*/
	private Long minNumber;
	/** 团购最多人数 */
	private Long maxNumber;
	/** 当前实际下单人数*/
	private Long currentActualNumber;
	/** 显示下单人数*/
	private Long showNumber;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	/** 创建人Id*/
	private Long createId;
	/** 修改人Id*/
	private Long updateId;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getSkuTogetherId() {
		return skuTogetherId;
	}
	public void setSkuTogetherId(Long skuTogetherId) {
		this.skuTogetherId = skuTogetherId;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getMinNumber() {
		return minNumber;
	}
	public void setMinNumber(Long minNumber) {
		this.minNumber = minNumber;
	}
	public Long getMaxNumber() {
		return maxNumber;
	}
	public void setMaxNumber(Long maxNumber) {
		this.maxNumber = maxNumber;
	}
	public Long getCurrentActualNumber() {
		return currentActualNumber;
	}
	public void setCurrentActualNumber(Long currentActualNumber) {
		this.currentActualNumber = currentActualNumber;
	}
	public Long getShowNumber() {
		return showNumber;
	}
	public void setShowNumber(Long showNumber) {
		this.showNumber = showNumber;
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
	public Long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	

}
