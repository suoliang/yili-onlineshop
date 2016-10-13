/**
 * 
 */
package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuBrandRelationQueryCondition;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sku.model.SkuBrandRelation;

/**
 * @author mengshaobo
 * 
 */
public interface SkuBrandRelationService<T extends SkuBrandRelation> extends BaseService<T> {
	/**
	 * 添加或修改品牌和商品关联表
	 * 
	 * @param m
	 */
	void addOrUpdate(SkuBrandRelation m);

	/**
	 * 通过品牌号查询商品编号集合
	 * 
	 * @param brandId
	 * @return
	 */
	List<String> findSkuCodesByBrandCode(String brandCode);

	/**
	 * 条件查询品牌关联商品编号集合
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return
	 */
	List<String> queryByCondition(SkuBrandRelationQueryCondition queryCondition);

	/**
	 * 条件查询品牌关联数量
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return
	 */
	Integer queryTotalByCondition(SkuBrandRelationQueryCondition queryCondition);

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @return
	 */
	BasePagination getPageList(BasePagination page);
	
	/**
	 * 条件查询,根据多个sku_id,in查询、得到所有sku_id对应的,品牌id
	 * @param skuIds
	 * @return
	 */
	List<String> findBrandCodesBySkuCodes(String skuCodes);
	/**
	 * 条件查询集合
	 * @param queryCondition
	 * @return
	 */
	List<SkuBrandRelation> queryListByCondtion(SkuBrandRelationQueryCondition queryCondition);
}
