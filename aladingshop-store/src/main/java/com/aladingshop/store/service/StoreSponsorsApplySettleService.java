package com.aladingshop.store.service;

import java.util.List;

import com.aladingshop.store.model.StoreSponsorsApplySettle;
import com.fushionbaby.common.util.BasePagination;

public interface StoreSponsorsApplySettleService<T extends StoreSponsorsApplySettle> {

	void add(StoreSponsorsApplySettle entry);
	void update(StoreSponsorsApplySettle entry);
	List<StoreSponsorsApplySettle> findByStoreCode(String storeCode);
	BasePagination getListPage(BasePagination page);
	StoreSponsorsApplySettle findByApplyNumber(String applyNumber);
	StoreSponsorsApplySettle findById(String id);
}
