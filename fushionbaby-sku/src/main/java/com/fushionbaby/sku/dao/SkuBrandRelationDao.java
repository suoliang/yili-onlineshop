/**
 * 
 */
package com.fushionbaby.sku.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.common.condition.sku.SkuBrandRelationQueryCondition;
import com.fushionbaby.sku.model.SkuBrandRelation;

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
	 * 条件查询,根据多个skuCode,in查询、得到所有skuCode对应的,品牌code
	 * @param skuCodes
	 * @return
	 */
	List<String> findBrandCodesBySkuCodes(@Param("skuCodes") String skuCodes);
}
