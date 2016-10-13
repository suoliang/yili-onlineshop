/**
 * 
 */
package com.fushionbaby.common.condition.sku;

import com.fushionbaby.common.condition.QueryCondition;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年8月7日上午10:31:40
 */
public class SkuSearchQueryCondition extends QueryCondition{

	/** 是否是app*/
	private String isApp;
	
	/** 品牌编号*/
	private String brandCode;
	
	/** 分类编号*/
	private String categoryCode;
	
	/** 标签编号*/
	private String labelCode;
	
	/** 商品名称*/
	private String skuName;
	
	/** 商品价格*/
	private String price;

	/** 关键字*/
	private String searchKey;
	
	/** 排序类型*/
	private String sortType;
	
	/** 排序字段*/
	private String sortParam;
	
	/** 图片尺寸编号*/
	private String imageStandardCode;
	
	

	public String getImageStandardCode() {
		return imageStandardCode;
	}

	public void setImageStandardCode(String imageStandardCode) {
		this.imageStandardCode = imageStandardCode;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	
	

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getSortParam() {
		return sortParam;
	}

	public void setSortParam(String sortParam) {
		this.sortParam = sortParam;
	}
	
	
	
}
