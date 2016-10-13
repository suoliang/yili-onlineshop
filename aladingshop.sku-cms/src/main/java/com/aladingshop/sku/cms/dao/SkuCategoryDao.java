package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.sku.cms.model.SkuCategory;
import com.fushionbaby.common.condition.sku.SkuCategoryQueryCondition;

/**
 * 
 * @author King
 * 
 */
public interface SkuCategoryDao {
	
	
	String findMaxCategoryCode(SkuCategoryQueryCondition queryCondition);
	
	
	List<SkuCategory> queryByCondition(SkuCategoryQueryCondition queryCondition);

	void add(SkuCategory category);

	void deleteById(Long id);

	void update(SkuCategory category);

	SkuCategory findById(Long id);

	/**
	 * 分页查询
	 * @param map
	 * @return List<MaCategory>
	 */
	List<SkuCategory> getListPage(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);


	
	
	

	
	
}
