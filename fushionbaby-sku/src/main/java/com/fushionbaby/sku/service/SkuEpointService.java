package com.fushionbaby.sku.service;

import java.util.List;
import com.fushionbaby.sku.model.SkuEpoint;

public interface SkuEpointService {
	
	SkuEpoint findBySkuCode(String skuCode);
	
	/**
	 * 根据标签查询多商品集合
	 * @param labelCode
	 * @return
	 */
	List<SkuEpoint> findByLabelCode(String labelCode);
	
}
