package com.aladingshop.web.vo;

import java.util.List;
import com.fushionbaby.common.dto.BrandDto;
import com.fushionbaby.common.dto.sku.CategoryDto;
import com.fushionbaby.common.dto.sku.SkuDto;

/**
 * @description 二级分类所对应的商品列表
 * @author 孙涛
 * @date 2015年7月27日下午6:43:53
 */
public class CategoryGoodVo {
	/** 二级分类 */
	private CategoryDto childCategory;
	/** 三级分类 */
	private List<CategoryDto> thirdCategory;
	/** 二级分类商品列表 */
	private List<SkuDto> skuList;
	/** 二级分类商品TOP3 */
	private List<SkuDto> hotGoods;
	/** 二级分类品牌TOP6 */
	private List<BrandDto> hotBrands;

	public CategoryDto getChildCategory() {
		return childCategory;
	}

	public void setChildCategory(CategoryDto childCategory) {
		this.childCategory = childCategory;
	}

	public List<CategoryDto> getThirdCategory() {
		return thirdCategory;
	}

	public void setThirdCategory(List<CategoryDto> thirdCategory) {
		this.thirdCategory = thirdCategory;
	}

	public List<SkuDto> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<SkuDto> skuList) {
		this.skuList = skuList;
	}

	public List<SkuDto> getHotGoods() {
		return hotGoods;
	}

	public void setHotGoods(List<SkuDto> hotGoods) {
		this.hotGoods = hotGoods;
	}

	public List<BrandDto> getHotBrands() {
		return hotBrands;
	}

	public void setHotBrands(List<BrandDto> hotBrands) {
		this.hotBrands = hotBrands;
	}

}
