/**
 * 
 */
package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuCategoryRelationQueryCondition;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.sku.model.SkuCategoryRelation;

/**
 * @author mengshaobo
 * 
 */
public interface SkuCategoryRelationService<T extends SkuCategoryRelation> extends BaseService<T> {
	/**
	 * 添加或修改商品分类信息
	 * 
	 * @param m
	 */
	void addOrUpdate(SkuCategoryRelation m);

	/**
	 * 通过分类编号分页查询
	 * 
	 * @param categoryId
	 *            分类编号
	 * @param curPage
	 *            当前页数
	 * @param limit
	 *            每页显示
	 * @return
	 */

	List<String> findSkuCodesByCategoryCode(String categoryCode, Integer curPage, Integer limit);
	List<String> findSkuCodesByCategoryCode(String categoryCode);
	/**
	 * 条件查询获得商品编号集合
	 * 
	 * @param queryCondition
	 * @return
	 */
	List<String> findSkuCodesByQueryCondition(SkuCategoryRelationQueryCondition queryCondition);

	/**
	 * 通过分类编号查询分类关联数量
	 * 
	 * @param categoryId
	 *            分类编号
	 * @return
	 */
	Integer getTotalByCategoryCode(String categoryCode);
	
	/**
	 * 条件查询,根据多个sku_id,in查询、得到所有sku_id对应的,分类id
	 * @param skuIds
	 * @return
	 */
	List<String> findCategoryCodesBySkuCodes(String skuCodes);
	/**
	 * 条件查询
	 * @param queryCondition
	 * @return
	 */
	List<SkuCategoryRelation> queryByCondition(SkuCategoryRelationQueryCondition queryCondition);
	
	
}
