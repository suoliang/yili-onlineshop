/**
 * 
 */
package com.fushionbaby.common.dto.order;

import java.io.Serializable;

/**
 * @author mengshaobo
 *封装了 (为下订单gotoOrder页面)的商品列表信息
 */
public class GotoOrderLineDto  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 商品唯一标示编号*/
	private String skuCode;
	
	/** 商品名称*/
	private String skuName;
	
	private String imgPath;
	/** 数量,订购数量*/
	private Integer requestedQty;
	
	/** 单价,销售单价*/
	private String currentPrice;
	
	/** 原价*/
	private String origPrice;
	
	/** 零售价,调牌价 */
	private String retailPrice;
	
	/** 购买行小计*/
	private String currentPriceTotal;
	
	/** 购买行折扣后小计*/
	private String dicountPriceTotal;
	
	/** 商品颜色*/
	private String color;
	
	/** 尺寸*/
	private String size;
	
	
	
	public String getOrigPrice() {
		return origPrice;
	}
	public void setOrigPrice(String origPrice) {
		this.origPrice = origPrice;
	}
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
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public Integer getRequestedQty() {
		return requestedQty;
	}
	public void setRequestedQty(Integer requestedQty) {
		this.requestedQty = requestedQty;
	}
	public String getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}
	public String getCurrentPriceTotal() {
		return currentPriceTotal;
	}
	public void setCurrentPriceTotal(String currentPriceTotal) {
		this.currentPriceTotal = currentPriceTotal;
	}
	public String getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
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
	public String getDicountPriceTotal() {
		return dicountPriceTotal;
	}
	public void setDicountPriceTotal(String dicountPriceTotal) {
		this.dicountPriceTotal = dicountPriceTotal;
	}

}
