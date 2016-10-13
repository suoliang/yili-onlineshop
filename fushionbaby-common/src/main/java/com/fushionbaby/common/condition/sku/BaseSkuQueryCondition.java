/**
 * 
 */
package com.fushionbaby.common.condition.sku;

import java.math.BigDecimal;

import com.fushionbaby.common.condition.QueryCondition;

/**
 * @author mengshaobo 查询条件基础类
 */
public class BaseSkuQueryCondition extends QueryCondition {
	
	/** 门店编号*/
	private String storeCode;
	
	/** 商品优惠率*/
	private BigDecimal discountRate;
	
	/** 图片类型*/
	private String imageType;
	
	/** 图片尺寸编号*/
	private String imageStandardCode;
	
	/** 商品状态 */
	private String status;
	
	/** 商品编号 */
	private String skuCode;
	
	/** 商品名称*/
	private String skuName;
	
	/** 排序参数 */
	private String sortParam;
	
	/** 排序类型 */
	private String sortType;
	
	
	
	
	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getImageStandardCode() {
		return imageStandardCode;
	}

	public void setImageStandardCode(String imageStandardCode) {
		this.imageStandardCode = imageStandardCode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}


	public String getSortParam() {
		return sortParam;
	}

	public void setSortParam(String sortParam) {
		this.sortParam = sortParam;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

}
