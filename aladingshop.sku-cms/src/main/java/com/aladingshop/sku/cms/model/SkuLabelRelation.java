/**
 * 
 */
package com.aladingshop.sku.cms.model;

import java.util.Date;

/**
 * @author mengshaobo 分区中的商品关系
 */
public class SkuLabelRelation {
	public SkuLabelRelation() {
	}

	public SkuLabelRelation(String labelCode, String skuCode, String disabled,String storeCode) {
		this.labelCode = labelCode;
		this.skuCode = skuCode;
		this.disabled = disabled;
		this.storeCode = storeCode;
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
	
	private String storeCode;

	private Date createTime;
	
	
	

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
