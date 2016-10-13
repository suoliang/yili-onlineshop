package com.fushionbaby.sku.dao;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuCategoryQueryCondition;
import com.fushionbaby.sku.model.SkuCategory;

/**
 * 
 * @author King
 * 
 */
public interface SkuCategoryDao {
	/***
	 * 查询所有的分类
	 * @return
	 */
	List<SkuCategory> findAllCategory(String storeCode);

	void add(SkuCategory category);

	void deleteById(Long id);

	void update(SkuCategory category);

	SkuCategory findById(Long id);

	/**
	 * 
	 * @return List<MaCategory>
	 */
	List<SkuCategory> findByCondition(SkuCategoryQueryCondition queryCondition);

	
}
