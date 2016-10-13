/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月28日下午2:02:59
 */
package com.fushionbaby.app.dto;

import java.util.List;

import com.fushionbaby.common.dto.FocusPicDto;
import com.fushionbaby.common.dto.sku.SkuDto;

/**
 * @description vipDto
 * @author 孟少博
 * @date 2015年10月28日下午2:02:59
 */
public class VipSkuDto {
	
	/** 分类编号*/
	private String categoryCode;
	
	/** 分类名称*/
	private String categoryName;
	
	/** 商品列表*/
	private List<SkuDto> skuList;
	
	/** 分类广告*/
	private FocusPicDto categoryBanner;

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<SkuDto> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<SkuDto> skuList) {
		this.skuList = skuList;
	}

	public FocusPicDto getCategoryBanner() {
		return categoryBanner;
	}

	public void setCategoryBanner(FocusPicDto categoryBanner) {
		this.categoryBanner = categoryBanner;
	}


	
	
	

}
