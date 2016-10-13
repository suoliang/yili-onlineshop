package com.aladingshop.sku.cms.service;

import java.util.List;

import com.aladingshop.sku.cms.model.SkuCombination;
import com.fushionbaby.common.condition.sku.SkuCombinationQueryCondition;

public interface SkuCombinationService {
	
	
	void add(SkuCombination skuCombination);
	
	void update(SkuCombination skuCombination);
	
	void deleteById(Long id);
	
	SkuCombination queryById(Long id);
	
	SkuCombination queryBySkuCode(String skuCode);
	
	List<SkuCombination> queryByConditon(SkuCombinationQueryCondition queryCondition);
	
	int getTotalByCondition(SkuCombinationQueryCondition queryCondition);
}
