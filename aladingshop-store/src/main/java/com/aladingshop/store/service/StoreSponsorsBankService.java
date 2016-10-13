package com.aladingshop.store.service;

import com.aladingshop.store.model.StoreSponsorsBank;

public interface StoreSponsorsBankService<T extends StoreSponsorsBank>{
	
	void add(StoreSponsorsBank entry);
	void update(StoreSponsorsBank entry);
	StoreSponsorsBank findByStoreCode(String storeCode);
	StoreSponsorsBank findById(String id);
}
