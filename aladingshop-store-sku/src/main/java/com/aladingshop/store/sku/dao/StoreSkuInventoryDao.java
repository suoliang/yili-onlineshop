package com.aladingshop.store.sku.dao;

import java.util.List;
import java.util.Map;


import com.aladingshop.store.sku.model.StoreSkuInventory;


public interface StoreSkuInventoryDao {
	
	public void add(StoreSkuInventory storeSkuInventory);
	
	public void updateById(StoreSkuInventory storeSkuInventory);
	
	public void deleteById(Long id);
	
	public StoreSkuInventory findById(Long id);
	
	List<StoreSkuInventory> getList(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
	
}
