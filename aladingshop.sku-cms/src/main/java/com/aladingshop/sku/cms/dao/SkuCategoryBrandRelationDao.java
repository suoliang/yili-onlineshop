package com.aladingshop.sku.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.sku.cms.model.SkuCategoryBrandRelation;

public interface SkuCategoryBrandRelationDao {

	void add(SkuCategoryBrandRelation skuCategoryBrandRelation);
	
	void deleteById(Long id);
	
	void update(SkuCategoryBrandRelation skuCategoryBrandRelation);
	
	List<SkuCategoryBrandRelation> findById(Long id);
	
	SkuCategoryBrandRelation findByCategoryCodeAndBrandCode(@Param("categoryCode") String categoryCode,
			@Param("brandCode") String brandCode);
	
	List<SkuCategoryBrandRelation> findByCategoryCode(@Param("categoryCode")String categoryCode);

	List<SkuCategoryBrandRelation> findByBrandCode(String brandCode);
	
}
