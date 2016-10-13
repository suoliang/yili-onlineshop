package com.aladingshop.store.dao;

import com.aladingshop.store.model.StoreSponsorsBank;

public interface StoreSponsorsBankDao {

	void add(StoreSponsorsBank entry);

	void update(StoreSponsorsBank entry);

	StoreSponsorsBank findByStoreCode(String storeCode);

	StoreSponsorsBank findById(String id);

}
