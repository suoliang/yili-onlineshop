package com.fushionbaby.sku.model;


/**
 * @author mengshaobo 热销商品
 */
public class SkuHot {

	/** 热销商品的编码 */
	private String skuCode;
	
	/** 热销商品的名称 */
	private String skuName;
	
	/** 热销商品的销售数量 */
	private Integer salesVolume;
	
	/** 热销商品实际销售数量*/
	private Integer actualSalesVolume;

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
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



}
