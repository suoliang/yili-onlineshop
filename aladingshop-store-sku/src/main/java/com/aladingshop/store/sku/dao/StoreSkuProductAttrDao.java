package com.aladingshop.store.sku.dao;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.store.sku.model.StoreSkuProductAttr;

public interface StoreSkuProductAttrDao {
	
	public void add(StoreSkuProductAttr storeSkuProductAttr);
	
	public void updateById(StoreSkuProductAttr storeSkuProductAttr);
	
	public void deleteById(Long id);
	
	public void deleteByStoreCodeAndProductCode(@Param(value = "storeCode") String storeCode,@Param(value = "productCode") String productCode);
	
	public StoreSkuProductAttr findById(Long id);
	
	public StoreSkuProductAttr findByStoreCodeAndProductCode(@Param(value = "storeCode") String storeCode,@Param(value = "productCode") String productCode);
	
}
