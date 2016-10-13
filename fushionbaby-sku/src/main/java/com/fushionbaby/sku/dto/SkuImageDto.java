package com.fushionbaby.sku.dto;

/***
 * 商品图片的查询条件
 * 
 * @author xupeijun
 * 
 */
public class SkuImageDto {
	/** 商品code */
	private String skuCode;
	/** 图片类型code */
	private String imageTypeCode;

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getImageTypeCode() {
		return imageTypeCode;
	}

	public void setImageTypeCode(String imageTypeCode) {
		this.imageTypeCode = imageTypeCode;
	}


}
