package com.aladingshop.store.sku.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.store.sku.dao.StoreSkuInventoryDao;
import com.aladingshop.store.sku.model.StoreSkuInventory;
import com.aladingshop.store.sku.service.StoreSkuInventoryService;
import com.fushionbaby.common.util.BasePagination;

@Service
public class StoreSkuInventoryServiceImpl implements StoreSkuInventoryService<StoreSkuInventory> {

	@Autowired
	private StoreSkuInventoryDao storeSkuInventoryDao;
	
	public void add(StoreSkuInventory storeSkuInventory) {
		storeSkuInventoryDao.add(storeSkuInventory);
		
	}
	
	public void updateById(StoreSkuInventory storeSkuInventory) {
		storeSkuInventoryDao.updateById(storeSkuInventory);
		
	}

	public void deleteById(Long id) {
		storeSkuInventoryDao.deleteById(id);
		
	}



	public StoreSkuInventory findById(Long id) {
		return storeSkuInventoryDao.findById(id);
	}

	
	public BasePagination getListPage(BasePagination pageParams) {
		Map<String, Object> map = pageParams.getSearchParamsMap();
		List<StoreSkuInventory> storeSkuInventoryList = new ArrayList<StoreSkuInventory>();
		Integer total = storeSkuInventoryDao.getTotal(map);
		pageParams.setCurrentTotal(total);
		if (total > 0) {
			storeSkuInventoryList = storeSkuInventoryDao.getList(map);
		}
		pageParams.setResult(storeSkuInventoryList);
		return pageParams;
	}

	


}
