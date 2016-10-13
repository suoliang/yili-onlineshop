/**
 * 
 */
package com.fushionbaby.common.dto.sku;

/**
 * @author mengshaobo 热销商品
 */
public class SkuHotDto {
	/** 商品名称 */
	private String name;
	/** 商品图片 */
	private String imgUrl;
	/** 商品编号*/
	private String skuCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	

}
