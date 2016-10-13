package com.fushionbaby.sku.model;

import java.util.Date;

/**
 * 分类和品牌关联表
 * @author mengshaobo
 *
 */
public class SkuCategoryBrandRelation {
	private Long id;
	/** 分类序号*/
	private String categoryCode;
	/** 品牌序号*/
	private String brandCode;
	/** 创建时间*/
	private Date createTime;
	/** 修改时间*/
	private Date updateTime;
	/**门店编码*/
	private String storeCode;
	

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

	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
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
