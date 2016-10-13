/**
 * 
 */
package com.fushionbaby.sku.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.common.condition.sku.SkuCategoryRelationQueryCondition;
import com.fushionbaby.sku.model.SkuCategoryRelation;

/**
 * @author mengshaobo
 * 
 */
public interface SkuCategoryRelationDao {
	void add(SkuCategoryRelation maSkuCategoryRelation);

	void update(SkuCategoryRelation skuCategoryRelation);

	void deleteById(Long id);

	SkuCategoryRelation findById(Long id);

	List<SkuCategoryRelation> findAll();

	SkuCategoryRelation findBySkuCode(String skuCode);

	/**
	 * 通过分类编号查询分类关联列表
	 * 
	 * @param categoryId
	 *            分类编号
	 * @param 开始索引
	 * @param 显示数量
	 * @return
	 */
	List<SkuCategoryRelation> findByCategoryCode(Map<String, Object> mapParams);

	/**
	 * 通过条件查询分类关联列表
	 * 
	 * @param queryCondition
	 * @return
	 */
	List<SkuCategoryRelation> findByCondition(SkuCategoryRelationQueryCondition queryCondition);

	/**
	 * 通过分类编号查询分类关联数量
	 * 
	 * @param categoryCode
	 *            分类编号
	 * @return
	 */
	Integer getTotalByCategoryCode(String categoryCode);
	
	/**
	 * 条件查询,根据多个sku_Code,in查询、得到所有sku_Code对应的,分类id
	 * @param skuIds
	 * @return
	 */
	List<String> findCategoryCodesBySkuCodes(Map<String,Object> map);
}
