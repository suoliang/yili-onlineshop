package com.aladingshop.sku.cms.model;

import java.util.Date;

/**
 * @description 分类图片
 * @author 索亮
 * @date 2015年8月6日上午10:55:18
 */
public class SkuCategoryImage {
	/** 分类图片id */
	private Long id;
	/** 图片类型的名称 */
	private String imageTypeName;
	/** 图片类型的code */
	private String imageTypeCode;
	/** 分类的code */
	private String categoryCode;
	/** 图片地址路径 */
	private String imgUrl;
	/** 图片链接地址 */
	private String linkUrl;
	/** 显示顺序 */
	private Integer sortOrder;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date updateTime;
	/** 创建id */
	private Long createId;
	/** 修改id */
	private Long updateId;
	
	
	/** 页面显示 创建者姓名*/
   private String createUser;
   /** 更新者姓名*/
   private String updateUser;
   
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
	public String getImageTypeCode() {
		return imageTypeCode;
	}

	public void setImageTypeCode(String imageTypeCode) {
		this.imageTypeCode = imageTypeCode;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
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

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getImageTypeName() {
		return imageTypeName;
	}

	public void setImageTypeName(String imageTypeName) {
		this.imageTypeName = imageTypeName;
	}



}
