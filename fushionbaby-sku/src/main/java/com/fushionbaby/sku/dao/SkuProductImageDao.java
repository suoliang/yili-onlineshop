package com.fushionbaby.sku.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.sku.model.SkuProductImage;

/**
 * 
 * @author cyla
 * 
 */
public interface SkuProductImageDao {
	List<SkuProductImage> findBySkuProductCode(String skuProductCode);

	List<SkuProductImage> findAllBySkuProductCode(String skuProductCode);

	void add(SkuProductImage skuProductImage);

	void update(SkuProductImage skuProductImage);

	void deleteByProductCode(@Param("productCode") String productCode);

	SkuProductImage findById(Long id);

	int getTotal(Map<String, Object> searchParamsMap);

	List<SkuProductImage> getListPage(Map<String, Object> searchParamsMap);

	void deleteById(Long id);
}
