package com.aladingshop.sku.cms.dao;

import java.util.List;

import com.aladingshop.sku.cms.model.SkuCombination;
import com.fushionbaby.common.condition.sku.SkuCombinationQueryCondition;

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
