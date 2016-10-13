package com.aladingshop.store.sku.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.store.sku.dao.StoreSkuPriceDao;
import com.aladingshop.store.sku.model.StoreSkuPrice;
import com.aladingshop.store.sku.service.StoreSkuPriceService;
import com.fushionbaby.common.util.BasePagination;

@Service
public class StoreSkuPriceServiceImpl implements StoreSkuPriceService<StoreSkuPrice> {

	@Autowired
	private StoreSkuPriceDao storeSkuPriceDao;
	
	public void add(StoreSkuPrice storeSkuPrice) {
		storeSkuPriceDao.add(storeSkuPrice);
		
	}

	public void updateByStoreCodeAndSkuCode(StoreSkuPrice storeSkuPrice) {
		storeSkuPriceDao.updateByStoreCodeAndSkuCode(storeSkuPrice);
		
	}
	
	public void updateById(StoreSkuPrice storeSkuPrice) {
		storeSkuPriceDao.updateById(storeSkuPrice);
		
	}

	public void deleteById(Long id) {
		storeSkuPriceDao.deleteById(id);
		
	}

	public void deleteByStoreCodeAndSkuCode(String storeCode,String skuCode) {
		storeSkuPriceDao.deleteByStoreCodeAndSkuCode(storeCode, skuCode);
		
	}


	public StoreSkuPrice findById(Long id) {
		return storeSkuPriceDao.findById(id);
	}

	public StoreSkuPrice findByStoreCodeAndSkuCode(String storeCode,String skuCode){
		return storeSkuPriceDao.findByStoreCodeAndSkuCode(storeCode, skuCode);
	}

	
	public BasePagination getListPage(BasePagination pageParams) {
		Map<String, Object> map = pageParams.getSearchParamsMap();
		List<StoreSkuPrice> storeSkuPriceList = new ArrayList<StoreSkuPrice>();
		Integer total = storeSkuPriceDao.getTotal(map);
		pageParams.setCurrentTotal(total);
		if (total > 0) {
			storeSkuPriceList = storeSkuPriceDao.getList(map);
		}
		pageParams.setResult(storeSkuPriceList);
		return pageParams;
	}

	


}
