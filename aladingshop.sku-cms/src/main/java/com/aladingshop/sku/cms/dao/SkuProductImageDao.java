package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.sku.cms.model.SkuProductImage;

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
	
	List<SkuProductImage> getListPageByProductCodeAndProductName(Map<String, Object> searchParamsMap);
	
	int getTotalByProductCodeAndProductName(Map<String, Object> searchParamsMap);
}
