package com.aladingshop.sku.cms.dao;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;

public interface SkuLabelRelationWebDao {
	/**
	 * 通过标签 获取商品编号集合
	 * @param queryCondition
	 * @return
	 */
	List<String> queryUnCodeListByLabel(SkuLabelRelationQueryCondition queryCondition);
	
}
