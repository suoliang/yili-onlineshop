package com.fushionbaby.sku.dao;

import com.fushionbaby.sku.model.SkuImageStandardConfig;

/**
 * 
 * @author cyla
 * 
 */
public interface SkuImageStandardConfigDao {
	
	SkuImageStandardConfig findByCode(String code);
}
