package com.aladingshop.sku.cms.service;

import java.util.List;

import com.aladingshop.sku.cms.model.SkuCategoryBrandRelation;

public interface SkuCategoryBrandRelationService<T extends SkuCategoryBrandRelation> {
	
	
	

	void add(SkuCategoryBrandRelation skuCategoryBrandRelation);
	
	void deleteById(Long id);
	
	void deleteByCategoryAndBrandCode(String categoryCode,String brandCode);
	
	void update(SkuCategoryBrandRelation skuCategoryBrandRelation);
	
	List<SkuCategoryBrandRelation> findById(Long id);
	
	SkuCategoryBrandRelation findByCategoryCodeAndBrandCode(String categoryCode,
			 String brandCode);
	
	List<SkuCategoryBrandRelation> findByCategoryCode(String categoryCode);
	
	List<SkuCategoryBrandRelation> findByBrandCode(String brandCode);
}
