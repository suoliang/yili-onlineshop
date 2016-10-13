package com.fushionbaby.sku.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;

public interface SkuLabelRelationDao {
	/**
	 * 通过标签 获取商品编号集合
	 * @param queryCondition
	 * @return
	 */
	List<String> querySkuUnCodeListByLabel(SkuLabelRelationQueryCondition queryCondition);
	
	/**
	 * 通过商品编号集合获取标签编号集合
	 * @param skuCodes 商品编号集合
	 * @return
	 */
	List<String> findLabelCodesBySkuCodes(@Param("skuCodes") List<String> skuCodes);
	
	
	/**
	 * 查询当天更新新增的标签商品
	 * @return
	 */
	List<String> queryBySameDayOperateing();
}
