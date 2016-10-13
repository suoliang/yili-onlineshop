package com.fushionbaby.cms.dto;

/**
 * 商品品牌的查询条件
 * 
 * @author Leon
 *
 */
public class SkuBrandDto {
	/** 品牌名称 */
	private String brandName;
	/** 品牌编码 */
	private String brandCode;
	/** 是否显示 */
	private String isShow;
	

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

}
