package com.aladingshop.store.sku.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.store.sku.model.StoreSkuPrice;


public interface StoreSkuPriceDao {
	
	public void add(StoreSkuPrice storeSkuPrice);
	
	public void updateByStoreCodeAndSkuCode(StoreSkuPrice storeSkuPrice);
	
	public void updateById(StoreSkuPrice storeSkuPrice);
	
	public void deleteById(Long id);
	
	public void deleteByStoreCodeAndSkuCode(@Param(value = "storeCode") String storeCode,@Param(value = "skuCode") String skuCode);
	
	public StoreSkuPrice findById(Long id);
	
	public StoreSkuPrice findByStoreCodeAndSkuCode(@Param(value = "storeCode") String storeCode,@Param(value = "skuCode") String skuCode);
	
	List<StoreSkuPrice> getList(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
	
}
