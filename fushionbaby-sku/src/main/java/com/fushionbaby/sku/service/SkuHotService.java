package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.sku.model.Sku;

/**
 * @author xupeijun
 * 
 */
public interface SkuHotService {


	/***
	 * 查询前几个商品热销
	 * 
	 * @param queryCondition 查询条件
	 * @return
	 */
	List<Sku> findHotSkuList(SkuEntryQueryCondition queryCondition);

}
