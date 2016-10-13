/**
 * 
 */
package com.fushionbaby.common.dto.sku;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fushionbaby.common.dto.BrandDto;
import com.fushionbaby.common.dto.FocusPicDto;

/**
 * @author mengshaobo 首页展示的商品 & 品牌的列表Dto
 */
public class IndexDto implements Serializable{
	private static final long serialVersionUID = 2584276803636326347L;
	/**分类列表*/
	private List<CategoryDto> categoryList;
	
	/** 品牌列表(25个) */
	private List<BrandDto> brandList;
	
	/** 焦点图*/
	private List<FocusPicDto> focusPictureList;
	 

	
	
	public List<CategoryDto> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<CategoryDto> categoryList) {
		this.categoryList = categoryList;
	}
	public List<FocusPicDto> getFocusPictureList() {
		return focusPictureList;
	}
	public void setFocusPictureList(List<FocusPicDto> focusPictureList) {
		this.focusPictureList = focusPictureList;
	}
	
	public List<BrandDto> getBrandList() {
		return brandList;
	}
	public void setBrandList(List<BrandDto> brandList) {
		this.brandList = brandList;
	}
}
