package com.fushionbaby.sku.model;

import java.util.Date;

/**
 * 
 * @author King suoliang 商品搭配表
 * 
 */
public class SkuLinkSkusRelation {
	private Long id;

	private String skuCode;

	private String linkSkuCode;

	private Long adminId;

	private Date createTime;
	
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

	public String getLinkSkuCode() {
		return linkSkuCode;
	}

	public void setLinkSkuCode(String linkSkuCode) {
		this.linkSkuCode = linkSkuCode;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}