package com.fushionbaby.sku.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.common.condition.sku.SkuLinkSkusQueryCondition;
import com.fushionbaby.sku.model.SkuLinkSkusRelation;

/**
 * 
 * @author King suoliang
 * 
 */
public interface SkuLinkSkusRelationDao {

	void add(SkuLinkSkusRelation skuLinkSkus);

	void deleteById(Long id);

	void update(SkuLinkSkusRelation skuLinkSkus);

	SkuLinkSkusRelation findById(Long id);

	List<SkuLinkSkusRelation> findAll();

	/**
	 * 通过条件查询商品搭配
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return
	 */
	List<SkuLinkSkusRelation> queryByCondition(SkuLinkSkusQueryCondition queryCondition);

	/**
	 * 分页查询数量
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
	List<SkuLinkSkusRelation> getListPage(Map<String, Object> map);

}
