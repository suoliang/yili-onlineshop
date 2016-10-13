package com.fushionbaby.common.dto;

import java.io.Serializable;

/**
 * 
 * @author guolongchao
 * 商品品牌信息Dto
 */
public class BrandDto  implements Serializable {
	private static final long serialVersionUID = -2076589689542729827L;
	
	/** web Logo地址*/
	private String brandLogoWebUrl;
	
	/** app Logo地址 */
	private String brandLogoAppUrl;
	
	/** 品牌编号*/
	private String brandCode;
	
	/** 品牌名称*/
	private String brandName;
	
	/** 品牌对应的网址*/
	private String brandSiteUrl;
	
	/** 品牌描述*/
	private String brandDesc;
	
	
	public String getBrandDesc() {
		return brandDesc;
	}
	public void setBrandDesc(String brandDesc) {
		this.brandDesc = brandDesc;
	}
	public String getBrandSiteUrl() {
		return brandSiteUrl;
	}
	public void setBrandSiteUrl(String brandSiteUrl) {
		this.brandSiteUrl = brandSiteUrl;
	}
	public String getBrandLogoWebUrl() {
		return brandLogoWebUrl;
	}
	public void setBrandLogoWebUrl(String brandLogoWebUrl) {
		this.brandLogoWebUrl = brandLogoWebUrl;
	}
	public String getBrandLogoAppUrl() {
		return brandLogoAppUrl;
	}
	public void setBrandLogoAppUrl(String brandLogoAppUrl) {
		this.brandLogoAppUrl = brandLogoAppUrl;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	
	
}
