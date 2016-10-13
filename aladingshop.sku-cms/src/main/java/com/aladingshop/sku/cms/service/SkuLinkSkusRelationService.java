package com.aladingshop.sku.cms.service;

import java.util.List;

import com.aladingshop.sku.cms.model.SkuLinkSkusRelation;
import com.fushionbaby.common.condition.sku.SkuLinkSkusQueryCondition;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

public interface SkuLinkSkusRelationService<T extends SkuLinkSkusRelation> extends
		BaseService<T> {

	/**
	 * 查询商品搭配表
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return
	 */
	List<T> queryByCondition(SkuLinkSkusQueryCondition queryCondition);

	/**
	 * 分页查询商品搭配表
	 * 
	 * @param page
	 * @return
	 */
	BasePagination getByListPage(BasePagination page);

	/**
	 * 批量添加商品搭配
	 * 
	 * @param list
	 */
	void insertList(List<SkuLinkSkusRelation> list, Long adminId);

	/**
	 * 删除搭配商品
	 * 
	 * @param queryCondition
	 *            删除条件
	 */
	void deleteByCondition(SkuLinkSkusQueryCondition queryCondition);

	/**
	 * 搭配商品编号集合（以，号分割）
	 * 
	 * @param skuId
	 *            商品编号
	 * @return
	 */
	String getLinkSkuCodeAry(String skuCode);
}
