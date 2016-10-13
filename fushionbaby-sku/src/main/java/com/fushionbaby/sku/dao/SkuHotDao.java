package com.fushionbaby.sku.dao;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.sku.model.Sku;

/**
 * @author xupeijun
 * 
 */
public interface SkuHotDao {
	

	 List<Sku> findHotSkuList(SkuEntryQueryCondition queryCondition);

}
