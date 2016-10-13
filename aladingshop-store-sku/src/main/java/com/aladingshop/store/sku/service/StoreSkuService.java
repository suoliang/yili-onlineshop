package com.aladingshop.store.sku.service;



import java.util.List;

import com.aladingshop.store.sku.model.StoreSku;
import com.fushionbaby.common.util.BasePagination;

public interface StoreSkuService <T extends StoreSku>{

	 void add(StoreSku storeSku);
	
	 void updateByStoreCodeAndSkuCode(StoreSku storeSku);
	
	 void updateById(StoreSku storeSku);
	
	 void deleteById(Long id);
	
	
	 List<StoreSku> findByStoreCode(String storeCode);
	
	 void deleteByStoreCodeAndSkuCode(String storeCode,String skuCode);
	
	 StoreSku findById(Long id);
	
	 StoreSku findByStoreCodeAndSkuCode(String storeCode,String skuCode);
	
	 BasePagination getListPage(BasePagination page);
	
	
	
}
