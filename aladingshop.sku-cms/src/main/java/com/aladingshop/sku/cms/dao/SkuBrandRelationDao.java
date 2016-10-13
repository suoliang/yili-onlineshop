/**
 * 
 */
package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.sku.cms.model.SkuBrandRelation;
import com.fushionbaby.common.condition.sku.SkuBrandRelationQueryCondition;

/**
 * @author mengshaobo
 * 
 */
public interface SkuBrandRelationDao {

	void add(SkuBrandRelation maSkuBrandRelation);

	void deleteById(Long id);

	void update(SkuBrandRelation maSkuBrandRelation);

	SkuBrandRelation findById(Long id);

	List<SkuBrandRelation> findAll();

	/***
	 * 通过商品号查询与品牌关联表
	 * 
	 * @param skuId
	 *            商品编号
	 * @return
	 */
	SkuBrandRelation findBySkuCode(String skuCode);

	/**
	 * 通过品牌号查询关联集合
	 * 
	 * @param brandId
	 *            品牌号
	 * @return
	 * */
	List<SkuBrandRelation> findByBrandCode(String brandCode);

	/**
	 * 获得总数
	 * 
	 * @param map
	 * @return
	 */
	Integer getTotal(Map<String, Object> map);

	/**
	 * 分页查询
	 * 
	 * @param map
	 * @return
	 */
	List<SkuBrandRelation> getPageList(Map<String, Object> map);

	/**
	 * 条件查询品牌关联商品编号集合
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return
	 */
	List<SkuBrandRelation> queryByCondition(SkuBrandRelationQueryCondition queryCondition);

	/**
	 * 条件查询品牌关联数量
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return
	 */
	Integer queryTotalByCondition(SkuBrandRelationQueryCondition queryCondition);

	/**
	 * 条件查询,根据多个sku_id,in查询、得到所有sku_id对应的,品牌id
	 * @param skuIds
	 * @return
	 */
	List<String> findBrandCodesBySkuCodes(Map<String,Object> map);
}
