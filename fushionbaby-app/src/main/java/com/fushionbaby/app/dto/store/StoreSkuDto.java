package com.fushionbaby.app.dto.store;

import java.util.List;


/**
 * 
 * @description 门店商品
 * @author 孟少博
 * @date 2015年12月12日下午1:18:16
 */
public class StoreSkuDto {

	/***
	 * 商品编号（逻辑传递使用）
	 */
	private String skuCode;
	
	/** 商品编码（只做页面展示）*/
	private String skuNo;
	
	/** 商品名称*/
	private String name;
	
	/** 当前价格*/
	private String currentPrice;
	
	/** 商品图片*/
	private List<String> imgUrls;
	
	/** 库存数量*/
	private Integer inventory;
	
	
	

	public List<String> getImgUrls() {
		return imgUrls;
	}

	public void setImgUrls(List<String> imgUrls) {
		this.imgUrls = imgUrls;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}


	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	
	
	
	
}
