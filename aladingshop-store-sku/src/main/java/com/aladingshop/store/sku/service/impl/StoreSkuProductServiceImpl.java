package com.aladingshop.store.sku.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.store.sku.dao.StoreSkuProductDao;
import com.aladingshop.store.sku.model.StoreSkuProduct;
import com.aladingshop.store.sku.service.StoreSkuProductService;
import com.fushionbaby.common.util.BasePagination;

@Service
public class StoreSkuProductServiceImpl implements StoreSkuProductService<StoreSkuProduct> {

	@Autowired
	private StoreSkuProductDao storeSkuProductDao;
	
	public void add(StoreSkuProduct storeSkuProduct) {
		storeSkuProductDao.add(storeSkuProduct);
		
	}

	public void updateByStoreCodeAndProductCode(StoreSkuProduct storeSkuProduct) {
		storeSkuProductDao.updateByStoreCodeAndProductCode(storeSkuProduct);
		
	}
	
	public void updateById(StoreSkuProduct storeSkuProduct) {
		storeSkuProductDao.updateById(storeSkuProduct);
		
	}

	public void deleteById(Long id) {
		storeSkuProductDao.deleteById(id);
		
	}

	public void deleteByStoreCodeAndProductCode(String storeCode,String skuCode) {
		storeSkuProductDao.deleteByStoreCodeAndProductCode(storeCode, skuCode);
		
	}


	public StoreSkuProduct findById(Long id) {
		return storeSkuProductDao.findById(id);
	}

	public StoreSkuProduct findByStoreCodeAndProductCode(String storeCode,String skuCode){
		return storeSkuProductDao.findByStoreCodeAndProductCode(storeCode, skuCode);
	}

	
	public BasePagination getListPage(BasePagination pageParams) {
		Map<String, Object> map = pageParams.getSearchParamsMap();
		List<StoreSkuProduct> storeSkuList = new ArrayList<StoreSkuProduct>();
		Integer total = storeSkuProductDao.getTotal(map);
		pageParams.setCurrentTotal(total);
		if (total > 0) {
			storeSkuList = storeSkuProductDao.getList(map);
		}
		pageParams.setResult(storeSkuList);
		return pageParams;
	}


}
