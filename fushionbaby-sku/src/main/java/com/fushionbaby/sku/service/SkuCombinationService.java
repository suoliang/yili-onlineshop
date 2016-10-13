package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuCombinationQueryCondition;
import com.fushionbaby.sku.model.SkuCombination;

public interface SkuCombinationService {
	
	
	void add(SkuCombination skuCombination);
	
	void update(SkuCombination skuCombination);
	
	void deleteById(Long id);
	
	SkuCombination queryById(Long id);
	
	SkuCombination queryBySkuCode(String skuCode);
	
	List<SkuCombination> queryByConditon(SkuCombinationQueryCondition queryCondition);
	
	int getTotalByCondition(SkuCombinationQueryCondition queryCondition);
}
