package com.aladingshop.store.dto;

import java.util.Date;

/**
 * 
 * @description 门店商品dto
 * @author 孟少博
 * @date 2015年9月16日上午10:07:50
 */
public class StoreSkuDto {
	
	/** 门店编号*/
	private String storeCode;
	
	/** 商品唯一标示编号*/
	private String uniqueCode;
	
	/** 商品编号*/
	private String skuNo;
	
	/** 商品条形码*/
	private String barCode;
	
	/** 分类编号 */
	private String categoryCode;
	
	/** 分类名称*/
	private String categoryName;
	
	/** 品牌编号*/
	private String brandCode;
	
	/** 品牌名称*/
	private String brandName;
	
	/** 商品名称*/
	private String skuName;
	
	/** 商品颜色*/
	private String color;
	
	/** 商品 尺寸*/
	private String size;
	
	/** 产品编号*/
	private String productCode;
	
	/** 是否门店自营的商品*/
	private String isSupport;
	
	/** 商品状态*/
	private String status;
	
	
	/** 创建开始时间*/
	private String createTimeFrom;
	
	/** 创建结束时间*/
	private String createTimeTo;
	
	/** 修改开始时间*/
	private String updateTimeFrom;

	/** 修改结束时间*/
	private String updateTimeTo;
	
	/** 修改时间*/
	private Date updateTime;
	
	/** 商品描述信息*/
	private String skuDescription; 
	
	/** 关键字*/
	private String keyWords;
	
	/** 显示顺序*/
	private Integer showOrder;
	
	
	
	
	

	public String getCreateTimeFrom() {
		return createTimeFrom;
	}

	public void setCreateTimeFrom(String createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}

	public String getCreateTimeTo() {
		return createTimeTo;
	}

	public void setCreateTimeTo(String createTimeTo) {
		this.createTimeTo = createTimeTo;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public String getSkuDescription() {
		return skuDescription;
	}

	public void setSkuDescription(String skuDescription) {
		this.skuDescription = skuDescription;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getUpdateTimeFrom() {
		return updateTimeFrom;
	}

	public void setUpdateTimeFrom(String updateTimeFrom) {
		this.updateTimeFrom = updateTimeFrom;
	}

	public String getUpdateTimeTo() {
		return updateTimeTo;
	}

	public void setUpdateTimeTo(String updateTimeTo) {
		this.updateTimeTo = updateTimeTo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}


	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
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

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getIsSupport() {
		return isSupport;
	}

	public void setIsSupport(String isSupport) {
		this.isSupport = isSupport;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}



}
