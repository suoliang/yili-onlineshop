/**
 * 
 */
package com.fushionbaby.facade.biz.common.order.dto;

import java.util.List;

import com.fushionbaby.common.dto.BrandDto;
import com.fushionbaby.common.dto.sku.CategoryDto;
import com.fushionbaby.common.dto.sku.web.PageSetDto;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年8月6日下午5:43:26
 */
public class SkuSearchResultDto {
	
	private PageSetDto pageSet;
	
	private List<CategoryDto> categoryList;
	
	private List<BrandDto> brandList;
	

	public PageSetDto getPageSet() {
		return pageSet;
	}

	public void setPageSet(PageSetDto pageSet) {
		this.pageSet = pageSet;
	}

	public List<CategoryDto> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryDto> categoryList) {
		this.categoryList = categoryList;
	}

	public List<BrandDto> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<BrandDto> brandList) {
		this.brandList = brandList;
	}

	 
}
