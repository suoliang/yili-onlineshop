package com.aladingshop.store.sku.service;

import com.aladingshop.store.sku.model.StoreSkuProductAttr;

public interface StoreSkuProductAttrService <T extends StoreSkuProductAttr>{

	public void add(StoreSkuProductAttr storeSkuProductAttr);
	
	public void updateById(StoreSkuProductAttr storeSkuProductAttr);
	
	public void deleteById(Long id);
	
	public void deleteByStoreCodeAndProductCode(String storeCode,String productCode);
	
	public StoreSkuProductAttr findById(Long id);
	
	public StoreSkuProductAttr findByStoreCodeAndProductCode(String storeCode,String productCode);
}
