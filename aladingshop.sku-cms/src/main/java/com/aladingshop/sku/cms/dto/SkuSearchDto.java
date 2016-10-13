/**
 * 
 */
package com.aladingshop.sku.cms.dto;

/**
 * @description  商品搜索引擎 创建索引参数。
 * @author 孟少博
 * @date 2015年7月23日下午4:57:10
 */
public class SkuSearchDto {
	
	/** 商品编号*/
	private String skuCode;
	
	/** 商品名称*/
	private String skuName;
	
	/** 商品关键字*/
	private String keyWords;
	
	/** 商品描述*/
	private String skuDescription;
	
	/** 商品标签(以逗号隔开存储)*/
	private String skuLabel;
	
	/** 商品品牌*/
	private String skuBrand;
	
	/** 商品品牌编号*/
	private String skuBrandCode;
	
	/** 商品分类*/
	private String skuCategory;
	
	/** 商品分类编号*/
	private String skuCategoryCode;
	
	/** 商品当前价格*/
	private String skuPrice;
	
	/** 商品销量*/
	private Integer salesVolume;
	
	/** 商品实际销量*/
	private Integer actualSalesVolume;
	
	/** 商品状态*/
	private String skuStatus;
	
	/** 上架时间*/
	private String onshelvestime;
	

	public String getSkuStatus() {
		return skuStatus;
	}

	public void setSkuStatus(String skuStatus) {
		this.skuStatus = skuStatus;
	}

	public String getOnshelvestime() {
		return onshelvestime;
	}

	public void setOnshelvestime(String onshelvestime) {
		this.onshelvestime = onshelvestime;
	}

	public Integer getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

	public Integer getActualSalesVolume() {
		return actualSalesVolume;
	}

	public void setActualSalesVolume(Integer actualSalesVolume) {
		this.actualSalesVolume = actualSalesVolume;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSkuDescription() {
		return skuDescription;
	}

	public void setSkuDescription(String skuDescription) {
		this.skuDescription = skuDescription;
	}

	public String getSkuBrand() {
		return skuBrand;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public void setSkuBrand(String skuBrand) {
		this.skuBrand = skuBrand;
	}

	public String getSkuCategory() {
		return skuCategory;
	}

	public void setSkuCategory(String skuCategory) {
		this.skuCategory = skuCategory;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getSkuPrice() {
		return skuPrice;
	}

	public void setSkuPrice(String skuPrice) {
		this.skuPrice = skuPrice;
	}

	public String getSkuBrandCode() {
		return skuBrandCode;
	}

	public void setSkuBrandCode(String skuBrandCode) {
		this.skuBrandCode = skuBrandCode;
	}

	public String getSkuCategoryCode() {
		return skuCategoryCode;
	}

	public void setSkuCategoryCode(String skuCategoryCode) {
		this.skuCategoryCode = skuCategoryCode;
	}

	public String getSkuLabel() {
		return skuLabel;
	}

	public void setSkuLabel(String skuLabel) {
		this.skuLabel = skuLabel;
	}
	
	
}
