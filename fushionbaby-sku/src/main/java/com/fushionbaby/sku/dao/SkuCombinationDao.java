package com.fushionbaby.sku.dao;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuCombinationQueryCondition;
import com.fushionbaby.sku.model.SkuCombination;

/**
 * 
 * @author mengshaobo
 *
 */
public interface SkuCombinationDao {
	
	void add(SkuCombination skuCombination);
	
	void update(SkuCombination skuCombination);
	
	void deleteById(Long id);
	
	SkuCombination queryById(Long id);
	
	List<SkuCombination> queryByConditon(SkuCombinationQueryCondition queryCondition);
	
	int getTotalByCondition(SkuCombinationQueryCondition queryCondition);		
}
