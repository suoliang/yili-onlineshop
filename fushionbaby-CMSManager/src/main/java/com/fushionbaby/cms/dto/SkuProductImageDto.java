package com.fushionbaby.cms.dto;

import java.util.Date;


/**
 * 
 * @author mengshaobo
 *
 */
public class SkuProductImageDto {
	/** id*/
	private Long id;
	
	/** 产品编号*/
	private String productCode;

	/** 图片地址*/
	private String imgPath;

	/** 排序*/
	private int sortOrder;
	
	/** 版本号*/
	private String version;

	/** 是否显示*/
	private String isDisable;
	
	/** 创建时间*/
	private Date createTime;
	
	/** 创建人*/
	private Long createId;
	
	/** 修改时间*/
	private Date updateTime;
	
	/** 修改人*/
	private Long updateId;
	
	private String productName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(String isDisable) {
		this.isDisable = isDisable;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
}
