package com.aladingshop.store.sku.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.store.sku.model.StoreSku;


public interface StoreSkuDao {
	
	public void add(StoreSku storeSku);
	
	public void updateByStoreCodeAndSkuCode(StoreSku storeSku);
	
	public void updateById(StoreSku storeSku);
	
	public void deleteById(Long id);
	
	public void deleteByStoreCodeAndSkuCode(@Param(value = "storeCode") String storeCode,@Param(value = "skuCode") String skuCode);
	
	public StoreSku findById(Long id);
	
	public StoreSku findByStoreCodeAndSkuCode(@Param(value = "storeCode") String storeCode,@Param(value = "skuCode") String skuCode);
	
	List<StoreSku> getList(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
	
	List<StoreSku> findByStoreCode(String storeCode);
	
}
