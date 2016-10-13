/**
 * 
 */
package com.aladingshop.wap.vo;

import java.util.List;
import com.fushionbaby.common.dto.BrandDto;
import com.fushionbaby.common.dto.sku.CategoryDto;

/**
 * @description 一级分类封装
 * @author 孙涛
 * @date 2015年8月5日上午11:25:10
 */
public class CategoryVo {
	public CategoryVo(CategoryDto dto, List<CategoryGoodVo> goodVos) {
		this.categoryDto = dto;
		this.categoryGoodVos = goodVos;
	}

	public CategoryVo() {
	}

	/** 一级分类 */
	private CategoryDto categoryDto;
	/** 一级分类下的所有热销品牌 */
	private List<BrandDto> brandDtos;
	/** 分类集合 */
	private List<CategoryGoodVo> categoryGoodVos;

	public CategoryDto getCategoryDto() {
		return categoryDto;
	}

	public void setCategoryDto(CategoryDto categoryDto) {
		this.categoryDto = categoryDto;
	}

	public List<BrandDto> getBrandDtos() {
		return brandDtos;
	}

	public void setBrandDtos(List<BrandDto> brandDtos) {
		this.brandDtos = brandDtos;
	}

	public List<CategoryGoodVo> getCategoryGoodVos() {
		return categoryGoodVos;
	}

	public void setCategoryGoodVos(List<CategoryGoodVo> categoryGoodVos) {
		this.categoryGoodVos = categoryGoodVos;
	}

}
