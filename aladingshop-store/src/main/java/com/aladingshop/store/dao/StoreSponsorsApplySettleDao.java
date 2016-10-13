package com.aladingshop.store.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.store.model.StoreSponsorsApplySettle;

public interface StoreSponsorsApplySettleDao {

	void add(StoreSponsorsApplySettle entry);

	void update(StoreSponsorsApplySettle entry);

	List<StoreSponsorsApplySettle> findByStoreCode(String storeCode);

	Integer getTotal(Map<String, Object> param);

	List<StoreSponsorsApplySettle> getListPage(Map<String, Object> param);
	
	StoreSponsorsApplySettle findByApplyNumber(String applyNumber);

	StoreSponsorsApplySettle findById(String id);

}
