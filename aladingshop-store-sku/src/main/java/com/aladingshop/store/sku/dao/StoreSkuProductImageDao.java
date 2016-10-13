package com.aladingshop.store.sku.dao;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.store.sku.model.StoreSkuProductImage;

public interface StoreSkuProductImageDao {
	
	public void add(StoreSkuProductImage storeSkuProductImage);
	
	public void updateById(StoreSkuProductImage storeSkuProductImage);
	
	public void deleteById(Long id);
	
	public void deleteByStoreCodeAndProductCode(@Param(value = "storeCode") String storeCode,@Param(value = "productCode") String productCode);
	
	public StoreSkuProductImage findById(Long id);
	
	
}
