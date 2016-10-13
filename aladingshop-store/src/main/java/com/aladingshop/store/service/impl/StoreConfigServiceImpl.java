package com.aladingshop.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.store.dao.StoreConfigDao;
import com.aladingshop.store.model.StoreConfig;
import com.aladingshop.store.service.StoreConfigService;

@Service
public class StoreConfigServiceImpl implements StoreConfigService<StoreConfig> {
	
	@Autowired
	private StoreConfigDao storeConfigDao ;
	
	public void add(StoreConfig entry) {
		this.storeConfigDao.add(entry);
	}

	public void update(StoreConfig entry) {
		this.storeConfigDao.update(entry);
	}

	public StoreConfig findByStoreCode(String storeCode) {
		return storeConfigDao.findByStoreCode(storeCode);
	}

}
