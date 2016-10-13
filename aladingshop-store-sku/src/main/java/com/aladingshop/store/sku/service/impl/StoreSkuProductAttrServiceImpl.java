package com.aladingshop.store.sku.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.store.sku.dao.StoreSkuProductAttrDao;
import com.aladingshop.store.sku.model.StoreSkuProductAttr;
import com.aladingshop.store.sku.service.StoreSkuProductAttrService;

@Service
public class StoreSkuProductAttrServiceImpl implements StoreSkuProductAttrService<StoreSkuProductAttr> {

	@Autowired
	private StoreSkuProductAttrDao storeSkuProductAttrDao;
	
	public void add(StoreSkuProductAttr storeSkuProductAttr) {
		storeSkuProductAttrDao.add(storeSkuProductAttr);
		
	}

	
	public void updateById(StoreSkuProductAttr storeSkuProductAttr) {
		storeSkuProductAttrDao.updateById(storeSkuProductAttr);
		
	}

	public void deleteById(Long id) {
		storeSkuProductAttrDao.deleteById(id);
		
	}


	public StoreSkuProductAttr findById(Long id) {
		return storeSkuProductAttrDao.findById(id);
	}


	public void deleteByStoreCodeAndProductCode(String storeCode,String productCode) {
		storeSkuProductAttrDao.deleteByStoreCodeAndProductCode(storeCode,productCode);
		
	}


	public StoreSkuProductAttr findByStoreCodeAndProductCode(String storeCode,
			String productCode) {
		return storeSkuProductAttrDao.findByStoreCodeAndProductCode(storeCode, productCode);
	}



}
