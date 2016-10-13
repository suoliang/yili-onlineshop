package com.fushionbaby.cms.dto;


/**
 * 
 * @author mengshaobo
 *
 */
public class SkuImageDto {
	/** id*/
	private Long id;
	
	/** 商品唯一标示编号*/
	private String skuCode;
	
	/** 图片类型*/
	private String imageTypeCode;
	
	/** 商品 编号*/
	private String skuNo;
	
	/** 商品 条形码*/
	private String barCode;

	/** 图片地址*/
	private String imgUrl;
	
	/** 显示顺序*/
	private Integer sortOrder;

	/** 只供显示数据库没有 */
	private String imageName;
	
	/** 创建者，更新者 ,时间*/
	private String createName;
	
	private String updateName;
	
	private String createTime;
	
	private String updateTime;

	
	
	
	public String getImageTypeCode() {
		return imageTypeCode;
	}

	public void setImageTypeCode(String imageTypeCode) {
		this.imageTypeCode = imageTypeCode;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	
	


}
