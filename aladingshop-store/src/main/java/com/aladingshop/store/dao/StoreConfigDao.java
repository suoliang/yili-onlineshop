package com.aladingshop.store.dao;

import com.aladingshop.store.model.StoreConfig;

public interface StoreConfigDao {

	void add(StoreConfig entry);

	void update(StoreConfig entry);

	StoreConfig findByStoreCode(String storeCode);

}
