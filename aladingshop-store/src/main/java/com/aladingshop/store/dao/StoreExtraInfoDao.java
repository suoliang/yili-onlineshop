package com.aladingshop.store.dao;


import java.util.List;
import java.util.Map;

import com.aladingshop.store.model.StoreExtraInfo;


public interface StoreExtraInfoDao {
	
	public void add(StoreExtraInfo storeExtraInfo);
	
	public void updateByStoreCode(StoreExtraInfo storeExtraInfo);
	
	public void deleteById(Long id);
	
	public void deleteByStoreCode(String storeCode);
	
	public StoreExtraInfo findById(Long id);
	
	public StoreExtraInfo findByStoreCode(String StoreCode);

	public Integer getTotal(Map<String, Object> params);

	public List<StoreExtraInfo> getPageList(Map<String, Object> params);

	public void update(StoreExtraInfo storeExtraInfo);
	
}
