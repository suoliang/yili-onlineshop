/**
 * 
 */
package com.fushionbaby.common.dto.sku;

import java.io.Serializable;

/**
 * @author mengshaobo
 *分类 和商品关联信息
 */
public class CategoryBrandRelationDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 品牌编号*/
	private String brandCode;
	/**品牌名称*/
	private String brandName;
	/**图片地址*/
	private String linkUrl;

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}


	
	
	
}
