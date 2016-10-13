package com.aladingshop.store.sku.service;



import com.aladingshop.store.sku.model.StoreSkuInventory;
import com.fushionbaby.common.util.BasePagination;

public interface StoreSkuInventoryService <T extends StoreSkuInventory>{

	public void add(StoreSkuInventory storeSkuInventory);
	
	public void updateById(StoreSkuInventory storeSkuInventory);
	
	public void deleteById(Long id);
	
	public StoreSkuInventory findById(Long id);
	
	public BasePagination getListPage(BasePagination page);
	
	
	
}
