package com.aladingshop.store.service;


import com.aladingshop.store.model.StoreExtraInfo;
import com.fushionbaby.common.util.BasePagination;

public interface StoreExtraInfoService <T extends StoreExtraInfo>{
	
	public void add(StoreExtraInfo storeExtraInfo);
	
	public void updateByStoreCode(StoreExtraInfo storeExtraInfo);
	
	public void deleteById(Long id);
	
	public void deleteByStoreCode(String storeCode);
	
	public StoreExtraInfo findById(Long id);
	
	public StoreExtraInfo findByStoreCode(String StoreCode);

	public BasePagination getListPage(BasePagination page);

	public void update(StoreExtraInfo storeExtraInfo);
	
}
