/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月10日下午2:02:52
 */
package com.fushionbaby.app.dto;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月10日下午2:02:52
 */
public class PromotionSkuDto {
	
	/** 活动类型编号*/
	private String pmCode;

	/** 商品唯一标示编号*/
	private String skuCode;
	
	/** 商品名称*/
	private String skuName;
	
	/** 剩余数量*/
	private Integer overNum;
	
	/** 商品图片*/
	private String imgPath;
	
	/** 促销价*/
	private String salesPrice;
	
	/** 原价*/
	private String origPrice;
	
	

	public String getPmCode() {
		return pmCode;
	}

	public void setPmCode(String pmCode) {
		this.pmCode = pmCode;
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


	
	public Integer getOverNum() {
		return overNum;
	}

	public void setOverNum(Integer overNum) {
		this.overNum = overNum;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(String salesPrice) {
		this.salesPrice = salesPrice;
	}

	public String getOrigPrice() {
		return origPrice;
	}

	public void setOrigPrice(String origPrice) {
		this.origPrice = origPrice;
	}
	
	
	
}
