package com.aladingshop.store.sku.service;

import com.aladingshop.store.sku.model.StoreSkuImage;

public interface StoreSkuImageService <T extends StoreSkuImage>{

	public void add(StoreSkuImage storeSkuImage);
	
	public void updateById(StoreSkuImage storeSkuImage);
	
	public void deleteById(Long id);
	
	public void deleteByStoreCodeAndSkuCode(String storeCode,String skuCode);
	
	public StoreSkuImage findByStoreCodeAndSkuCode(String storeCode,String skuCode);
	
	public StoreSkuImage findById(Long id);
	
}
