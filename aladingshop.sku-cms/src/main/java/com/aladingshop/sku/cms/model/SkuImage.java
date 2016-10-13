package com.aladingshop.sku.cms.model;

import java.util.Date;

/***
 * 商品的图片
 * 
 * @author xupeijun
 * 
 */
public class SkuImage {

	/**自增id*/
	private Long id;
    /**商品图片类型*/
	private Long imageTypeId;
    /**商品图片类型编码*/
	private String imageTypeCode;
    /**商品编码*/
	private String skuCode;
    /** 图片url*/
	private String imgUrl;
	/** 显示顺序*/
	private Integer sortOrder;
    /**创建时间*/
	private Date createTime;
	/**创建者id*/
	private Long createId;
	/** 更新时间*/
	private Date updateTime;
	/**更新者id*/
	private Long updateId;
	

	/** 门店编码*/
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

	public Long getImageTypeId() {
		return imageTypeId;
	}

	public void setImageTypeId(Long imageTypeId) {
		this.imageTypeId = imageTypeId;
	}

	public String getImageTypeCode() {
		return imageTypeCode;
	}

	public void setImageTypeCode(String imageTypeCode) {
		this.imageTypeCode = imageTypeCode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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


	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

}
