package com.fushionbaby.sku.service;

import com.fushionbaby.sku.model.SkuImageStandardConfig;

/**
 * 
 * @author cyla
 * 
 */

public interface SkuImageStandardConfigService<T extends SkuImageStandardConfig> {
	/**
	 * 通过code查询图片尺寸
	 * 
	 * @param code
	 * @return
	 */
	SkuImageStandardConfig findByCode(String code);
}