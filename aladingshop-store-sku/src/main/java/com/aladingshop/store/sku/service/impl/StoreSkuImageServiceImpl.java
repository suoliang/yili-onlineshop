package com.aladingshop.store.sku.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.store.sku.dao.StoreSkuImageDao;
import com.aladingshop.store.sku.model.StoreSkuImage;
import com.aladingshop.store.sku.service.StoreSkuImageService;

@Service
public class StoreSkuImageServiceImpl implements StoreSkuImageService<StoreSkuImage> {

	@Autowired
	private StoreSkuImageDao storeSkuImageDao;
	
	public void add(StoreSkuImage storeSkuImage) {
		storeSkuImageDao.add(storeSkuImage);
		
	}

	
	public void updateById(StoreSkuImage storeSkuImage) {
		storeSkuImageDao.updateById(storeSkuImage);
		
	}

	public void deleteById(Long id) {
		storeSkuImageDao.deleteById(id);
		
	}


	public StoreSkuImage findById(Long id) {
		return storeSkuImageDao.findById(id);
	}


	public void deleteByStoreCodeAndSkuCode(String storeCode,String skuCode) {
		storeSkuImageDao.deleteByStoreCodeAndSkuCode(storeCode,skuCode);
		
	}


	public StoreSkuImage findByStoreCodeAndSkuCode(String storeCode, String skuCode) {


		return 		storeSkuImageDao.findByStoreCodeAndSkuCode(storeCode, skuCode);
		
	}



}
