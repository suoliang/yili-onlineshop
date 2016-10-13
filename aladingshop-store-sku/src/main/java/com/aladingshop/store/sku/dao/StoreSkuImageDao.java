package com.aladingshop.store.sku.dao;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.store.sku.model.StoreSkuImage;

public interface StoreSkuImageDao {
	
	public void add(StoreSkuImage storeSkuImage);
	
	public void updateById(StoreSkuImage storeSkuImage);
	
	public void deleteById(Long id);
	
	public void deleteByStoreCodeAndSkuCode(@Param(value = "storeCode") String storeCode,@Param(value = "skuCode") String skuCode);
	
	public StoreSkuImage findByStoreCodeAndSkuCode(@Param(value = "storeCode") String storeCode,@Param(value = "skuCode") String skuCode);
	
	public StoreSkuImage findById(Long id);
	
	
}
