package com.aladingshop.store.sku.service;

import com.aladingshop.store.sku.model.StoreSkuProductImage;

public interface StoreSkuProductImageService <T extends StoreSkuProductImage>{

	public void add(StoreSkuProductImage storeSkuProductImage);
	
	public void updateById(StoreSkuProductImage storeSkuProductImage);
	
	public void deleteById(Long id);
	
	public void deleteByStoreCodeAndProductCode(String storeCode,String productCode);
	
	public StoreSkuProductImage findById(Long id);
	
}
