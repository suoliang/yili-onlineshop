/**
 * 
 */
package com.fushionbaby.sku.service;

import com.fushionbaby.sku.model.SkuTogetherStatus;

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
