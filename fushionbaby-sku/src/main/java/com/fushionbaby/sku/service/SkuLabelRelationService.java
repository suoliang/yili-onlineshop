package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;

/**
 * 
 * @author 孟少博
 *
 */
public interface SkuLabelRelationService {
	/**
	 * 查询商品编号集合
	 * @param queryCondition
	 * @return
	 */
	List<String> querySkuUnCodeListByLabel(SkuLabelRelationQueryCondition queryCondition);
	
	/**
	 * 条件查询,根据多个商品编号,in查询、得到所有商品编号对应的,标签编号并去除重复
	 * 
	 * @param skuCodes
	 * @return
	 */
	List<String> findLabelCodesBySkuCodes(List<String> skuCodes);
	
	

	/**
	 * 通过标签编号查询商品编号集合(,号分割)
	 * 
	 * @param labelId
	 * @return
	 */
	String getSkuCodeAryByLabelCode(String labelCode);
	
	/**
	 * 查询当天操作过的商品编号
	 * @return
	 */
	List<String> queryBySameDayOperateing();
	
}
