/**
 * 
 */
package com.fushionbaby.common.dto.sku;

/**
 * @author mengshaobo 限时抢购
 */
public class SkuTimelimitDto {
	/** 商品编号 */
	private String skuCode;
	/** 商品名称 */
	private String skuName;
	/** 抢购价格 */
	private String limitPrice;
	/** 距离下架时间（时间戳格式） */
	private String offshelvestime;
	/** 限购数量 */
	private String placeNum;
	/** 图片地址 */
	private String imgPath;

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
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

	public String getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(String limitPrice) {
		this.limitPrice = limitPrice;
	}

	public String getPlaceNum() {
		return placeNum;
	}

	public void setPlaceNum(String placeNum) {
		this.placeNum = placeNum;
	}

	public String getOffshelvestime() {
		return offshelvestime;
	}

	public void setOffshelvestime(String offshelvestime) {
		this.offshelvestime = offshelvestime;
	}

}
