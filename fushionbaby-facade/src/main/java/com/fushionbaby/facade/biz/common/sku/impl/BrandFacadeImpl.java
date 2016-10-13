/**
 * 
 */
package com.fushionbaby.facade.biz.common.sku.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.dto.BrandDto;
import com.fushionbaby.facade.biz.AbstactSkuFacade;
import com.fushionbaby.facade.biz.common.sku.BrandFacade;
import com.fushionbaby.sku.model.SkuBrand;
import com.fushionbaby.sku.model.SkuCategoryBrandRelation;
import com.fushionbaby.sku.service.SkuBrandService;
import com.fushionbaby.sku.service.SkuCategoryBrandRelationService;
import com.fushionbaby.sku.service.SkuService;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年7月29日上午10:34:04
 */
 @Service
public class BrandFacadeImpl extends AbstactSkuFacade implements BrandFacade  {
	 
	 @Autowired
	 private SkuCategoryBrandRelationService<SkuCategoryBrandRelation> skuCategoryBrandRelationService;
	 
	 @Autowired
	 private SkuBrandService<SkuBrand> skuBrandService;
	 @Autowired
	 private SkuService skuService;
	 
	/* (non-Javadoc)
	 * @see com.fushionbaby.facade.biz.common.sku.BrandFacade#getBrandDtoByCategoryCode(java.lang.String, java.lang.Integer)
	 */
	public List<BrandDto> getBrandDtoByCategoryCode(String categoryCode,
			Integer count) {
		List<BrandDto> brandDtos = new ArrayList<BrandDto>();
		List<SkuCategoryBrandRelation> brandList = skuCategoryBrandRelationService.findByCategoryCode(categoryCode);
		if (count != null && count != 0 && brandList.size() > count) {
			brandList = brandList.subList(0, count);
		}

		for (SkuCategoryBrandRelation relation : brandList) {

			SkuBrand brand = skuBrandService.findByBrandCode(relation.getBrandCode());
			if (brand == null) {
				continue;
			}
			brandDtos.add(this.assBrandDto(brand));
		}

		return brandDtos;
	}

	/* (non-Javadoc)
	 * @see com.fushionbaby.facade.biz.common.sku.BrandFacade#getBrandDto(java.lang.String)
	 */
	public BrandDto getBrandDto(String brandCode) {
		SkuBrand brand = skuBrandService.findByBrandCode(brandCode);
		if(brand == null){
			return new BrandDto();
		}
		
		return this.assBrandDto(brand);
	}

}
