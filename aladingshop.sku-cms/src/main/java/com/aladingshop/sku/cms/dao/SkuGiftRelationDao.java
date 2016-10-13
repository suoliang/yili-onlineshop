/**
 * 
 */
package com.aladingshop.sku.cms.dao;

import java.util.List;

import com.aladingshop.sku.cms.model.SkuGiftRelation;
import com.fushionbaby.common.condition.sku.SkuGiftQueryCondition;

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
