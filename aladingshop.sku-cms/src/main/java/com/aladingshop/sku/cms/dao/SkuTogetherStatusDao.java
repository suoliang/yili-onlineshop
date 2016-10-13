/**
 * 
 */
package com.aladingshop.sku.cms.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.sku.cms.model.SkuTogetherStatus;

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
