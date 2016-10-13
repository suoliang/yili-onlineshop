/**
 * 
 */
package com.fushionbaby.common.dto.sku;

import java.util.List;

/**
 * @author mengshaobo
 *
 */
public class SkuBrandGraphemeDto {
	/** 字母*/
	private String grapheme;
	/** 品牌集合*/
	private List<SkuBrandDto> skuBrandList;

	public String getGrapheme() {
		return grapheme;
	}

	public void setGrapheme(String grapheme) {
		this.grapheme = grapheme;
	}

	public List<SkuBrandDto> getSkuBrandList() {
		return skuBrandList;
	}

	public void setSkuBrandList(List<SkuBrandDto> skuBrandList) {
		this.skuBrandList = skuBrandList;
	}
	
	
	
}
