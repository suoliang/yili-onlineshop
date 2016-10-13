/**
 * 
 */
package com.fushionbaby.sku.dao;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuGiftQueryCondition;
import com.fushionbaby.sku.model.SkuGiftRelation;

/**
 * @author mengshaobo
 * 
 */
public interface SkuGiftRelationDao {

	void add(SkuGiftRelation skuGift);

	void deleteById(Long id);

	SkuGiftRelation findById(Long id);

	List<SkuGiftRelation> findAll();

	/**
	 * 根据条件查询赠品信息
	 * 
	 * @param queryCondition
	 * @return
	 */
	List<SkuGiftRelation> queryByCondition(SkuGiftQueryCondition queryCondition);

}
