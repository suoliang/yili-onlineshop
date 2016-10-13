/**
 * 
 */
package com.fushionbaby.sku.model;

import java.util.Date;

/**
 * @author mengshaobo 分区中的商品关系
 */
public class SkuLabelRelation {
	public SkuLabelRelation() {
	}

	public SkuLabelRelation(String labelCode, String skuCode, String disabled) {
		this.labelCode = labelCode;
		this.skuCode = skuCode;
		this.disabled = disabled;
	}

	/** id */
	private Long id;
	/** 标签编号 */
	private String labelCode;
	/** 商品 */
	private String skuCode;
	
	
	/** 状态 */
	private String disabled;
	/** 显示顺序 */
	private Integer showOrder;

	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
