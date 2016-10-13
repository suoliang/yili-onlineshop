package com.aladingshop.store.sku.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.store.sku.dao.StoreSkuProductImageDao;
import com.aladingshop.store.sku.model.StoreSkuProductImage;
import com.aladingshop.store.sku.service.StoreSkuProductImageService;

@Service
public class StoreSkuProductImageServiceImpl implements StoreSkuProductImageService<StoreSkuProductImage> {

	@Autowired
	private StoreSkuProductImageDao storeSkuProductImageDao;
	
	public void add(StoreSkuProductImage storeSkuProductImage) {
		storeSkuProductImageDao.add(storeSkuProductImage);
		
	}

	
	public void updateById(StoreSkuProductImage storeSkuProductImage) {
		storeSkuProductImageDao.updateById(storeSkuProductImage);
		
	}

	public void deleteById(Long id) {
		storeSkuProductImageDao.deleteById(id);
		
	}


	public StoreSkuProductImage findById(Long id) {
		return storeSkuProductImageDao.findById(id);
	}


	public void deleteByStoreCodeAndProductCode(String storeCode,String productCode) {
		storeSkuProductImageDao.deleteByStoreCodeAndProductCode(storeCode,productCode);
		
	}



}
