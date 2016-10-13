package com.aladingshop.store.sku.service;



import com.aladingshop.store.sku.model.StoreSkuPrice;
import com.fushionbaby.common.util.BasePagination;

public interface StoreSkuPriceService <T extends StoreSkuPrice>{

	public void add(StoreSkuPrice storeSkuPrice);
	
	public void updateByStoreCodeAndSkuCode(StoreSkuPrice storeSkuPrice);
	
	public void updateById(StoreSkuPrice storeSkuPrice);
	
	public void deleteById(Long id);
	
	public void deleteByStoreCodeAndSkuCode(String storeCode,String skuCode);
	
	public StoreSkuPrice findById(Long id);
	
	public StoreSkuPrice findByStoreCodeAndSkuCode(String storeCode,String skuCode);
	
	public BasePagination getListPage(BasePagination page);
	
	
	
}
