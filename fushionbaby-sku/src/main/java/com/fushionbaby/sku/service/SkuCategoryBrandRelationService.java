package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.sku.model.SkuCategoryBrandRelation;

public interface SkuCategoryBrandRelationService<T extends SkuCategoryBrandRelation> {
	
	
	

	void add(SkuCategoryBrandRelation skuCategoryBrandRelation);
	
	void deleteById(Long id);
	
	void deleteByCategoryAndBrandCode(String categoryCode,String brandCode);
	
	void update(SkuCategoryBrandRelation skuCategoryBrandRelation);
	
	List<SkuCategoryBrandRelation> findById(Long id);
	
	SkuCategoryBrandRelation findByCategoryCodeAndBrandCode(String categoryCode,
			 String brandCode);
	
	List<SkuCategoryBrandRelation> findByCategoryCode(String categoryCode);
}
