package com.aladingshop.store.service;

import com.aladingshop.store.model.StoreConfig;

public interface StoreConfigService<T extends StoreConfig> {
	void add(StoreConfig entry);
	void update(StoreConfig entry);
	StoreConfig findByStoreCode(String storeCode);
}
