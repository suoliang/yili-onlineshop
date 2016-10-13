package com.aladingshop.store.sku.service;



import com.aladingshop.store.sku.model.StoreSkuProduct;
import com.fushionbaby.common.util.BasePagination;

public interface StoreSkuProductService <T extends StoreSkuProduct>{

	public void add(StoreSkuProduct storeSkuProduct);
	
	public void updateByStoreCodeAndProductCode(StoreSkuProduct storeSkuProduct);
	
	public void updateById(StoreSkuProduct storeSkuProduct);
	
	public void deleteById(Long id);
	
	public void deleteByStoreCodeAndProductCode(String storeCode,String productCode);
	
	public StoreSkuProduct findById(Long id);
	
	public StoreSkuProduct findByStoreCodeAndProductCode(String storeCode,String productCode);
	
	public BasePagination getListPage(BasePagination page);
	
	
	
}
