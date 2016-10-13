/**
 * 
 */
package com.fushionbaby.common.condition.sku;

/**
 * 商品赠品查询条件
 * 
 * @author mengshaobo
 * 
 */
public class SkuGiftQueryCondition {
	/** 商品编号 */
	private String skuCode;
	/** 商品赠品编号 */
	private String giftSkuCode;
	
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getGiftSkuCode() {
		return giftSkuCode;
	}
	public void setGiftSkuCode(String giftSkuCode) {
		this.giftSkuCode = giftSkuCode;
	}
	

}
