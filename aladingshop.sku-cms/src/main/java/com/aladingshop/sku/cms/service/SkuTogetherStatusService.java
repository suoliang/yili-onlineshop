/**
 * 
 */
package com.aladingshop.sku.cms.service;

import com.aladingshop.sku.cms.model.SkuTogetherStatus;

/**
 * @author mengshaobo
 * 
 */
public interface SkuTogetherStatusService<T extends SkuTogetherStatus>{

	void add(SkuTogetherStatus skuTogetherStatus);

	void updateBySkuTogetherId(SkuTogetherStatus skuTogetherStatus);

	SkuTogetherStatus findBySkuTogetherId(Long skuTogetherId);
	
	void deleteBySkuTogetherId(Long skuTogetherId);
	
}
