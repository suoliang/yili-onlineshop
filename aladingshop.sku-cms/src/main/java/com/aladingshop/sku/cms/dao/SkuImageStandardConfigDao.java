package com.aladingshop.sku.cms.dao;

import com.aladingshop.sku.cms.model.SkuImageStandardConfig;

/**
 * 
 * @author cyla
 * 
 */
public interface SkuImageStandardConfigDao {
	
	SkuImageStandardConfig findByCode(String code);
}
