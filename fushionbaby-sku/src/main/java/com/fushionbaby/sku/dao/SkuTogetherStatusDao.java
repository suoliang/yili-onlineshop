/**
 * 
 */
package com.fushionbaby.sku.dao;


import org.apache.ibatis.annotations.Param;

import com.fushionbaby.sku.model.SkuTogetherStatus;

/**
 * @author mengshaobo
 * 
 */
public interface SkuTogetherStatusDao {
	
	void add(SkuTogetherStatus skuTogetherStatus);

	void updateBySkuTogetherId(SkuTogetherStatus skuTogetherStatus);

	SkuTogetherStatus findBySkuTogetherId(@Param(value = "skuTogetherId") Long skuTogetherId);
	
	void deleteBySkuTogetherId(@Param(value = "skuTogetherId") Long skuTogetherId);
	
}
