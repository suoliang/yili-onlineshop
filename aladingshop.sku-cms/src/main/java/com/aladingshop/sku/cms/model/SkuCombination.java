/**
 * 
 */
package com.aladingshop.sku.cms.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mengshaobo
 *优惠商品组合定义
 */
public class SkuCombination {
	private Long id;
	/** 商品组合描述*/
	private String descreption;
	/** 总折扣*/
	private BigDecimal discount;
	/** 组合状态: n 不可用 y 可用*/
	private String status;
	/** 累计销量*/
	private Long saledAmount;
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
	public String getDescreption() {
		return descreption;
	}
	public void setDescreption(String descreption) {
		this.descreption = descreption;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getSaledAmount() {
		return saledAmount;
	}
	public void setSaledAmount(Long saledAmount) {
		this.saledAmount = saledAmount;
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
