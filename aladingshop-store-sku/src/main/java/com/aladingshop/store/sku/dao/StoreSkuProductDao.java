package com.aladingshop.store.sku.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.store.sku.model.StoreSkuProduct;


public interface StoreSkuProductDao {
	
	public void add(StoreSkuProduct storeSkuProduct);
	
	public void updateByStoreCodeAndProductCode(StoreSkuProduct storeSkuProduct);
	
	public void updateById(StoreSkuProduct storeSkuProduct);
	
	public void deleteById(Long id);
	
	public void deleteByStoreCodeAndProductCode(@Param(value = "storeCode") String storeCode,@Param(value = "productCode") String productCode);
	
	public StoreSkuProduct findById(Long id);
	
	public StoreSkuProduct findByStoreCodeAndProductCode(@Param(value = "storeCode") String storeCode,@Param(value = "productCode") String productCode);
	
	List<StoreSkuProduct> getList(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
	
}
