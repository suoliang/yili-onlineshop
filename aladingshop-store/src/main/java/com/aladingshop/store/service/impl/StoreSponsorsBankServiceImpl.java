package com.aladingshop.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.store.dao.StoreSponsorsBankDao;
import com.aladingshop.store.model.StoreSponsorsBank;
import com.aladingshop.store.service.StoreSponsorsBankService;

@Service
public class StoreSponsorsBankServiceImpl implements
		StoreSponsorsBankService<StoreSponsorsBank> {
	
	@Autowired
	private StoreSponsorsBankDao storeSponsorsBankDao;
	
	public void add(StoreSponsorsBank entry) {
		this.storeSponsorsBankDao.add(entry);
		
	}

	public void update(StoreSponsorsBank entry) {
		this.storeSponsorsBankDao.update(entry);
		
	}

	public StoreSponsorsBank findByStoreCode(String storeCode) {
		return this.storeSponsorsBankDao.findByStoreCode(storeCode);
	}

	public StoreSponsorsBank findById(String id) {
		
		return this.storeSponsorsBankDao.findById(id);
	}

}
