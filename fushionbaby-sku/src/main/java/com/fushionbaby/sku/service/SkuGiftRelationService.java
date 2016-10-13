/**
 * 
 */
package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuGiftQueryCondition;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.sku.model.SkuGiftRelation;

/**
 * @author mengshaobo
 * 
 */
public interface SkuGiftRelationService<T extends SkuGiftRelation> extends BaseService<T> {
	/**
	 * 添加赠品集合
	 * 
	 * @param list
	 *            赠品信息
	 */
	void insertList(List<SkuGiftRelation> list);

	/**
	 * 根据条件查询赠品
	 * 
	 * @param queryCondition
	 * @return
	 */
	List<SkuGiftRelation> queryByCondition(SkuGiftQueryCondition queryCondition);

	/**
	 * 根据条件删除赠品表数据
	 * 
	 * @param queryCondition
	 */
	void deleteSkuGiftByCondition(SkuGiftQueryCondition queryCondition);

	/**
	 * 通过商品
	 * 
	 * @param skuId
	 *            商品编号
	 * @return
	 */
	public String getGiftSkuCodeAryBySkuCode(String skuCode);
}
