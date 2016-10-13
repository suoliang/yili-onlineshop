package com.aladingshop.store.sku.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.store.sku.dao.StoreSkuDao;
import com.aladingshop.store.sku.model.StoreSku;
import com.aladingshop.store.sku.service.StoreSkuService;
import com.fushionbaby.common.util.BasePagination;

@Service
public class StoreSkuServiceImpl implements StoreSkuService<StoreSku> {

	@Autowired
	private StoreSkuDao storeSkuDao;
	
	public void add(StoreSku storeSku) {
		storeSkuDao.add(storeSku);
		
	}

	public void updateByStoreCodeAndSkuCode(StoreSku storeSku) {
		storeSkuDao.updateByStoreCodeAndSkuCode(storeSku);
		
	}
	
	public void updateById(StoreSku storeSku) {
		storeSkuDao.updateById(storeSku);
		
	}

	public void deleteById(Long id) {
		storeSkuDao.deleteById(id);
		
	}

	public void deleteByStoreCodeAndSkuCode(String storeCode,String skuCode) {
		storeSkuDao.deleteByStoreCodeAndSkuCode(storeCode, skuCode);
		
	}


	public StoreSku findById(Long id) {
		return storeSkuDao.findById(id);
	}

	public StoreSku findByStoreCodeAndSkuCode(String storeCode,String skuCode){
		return storeSkuDao.findByStoreCodeAndSkuCode(storeCode, skuCode);
	}

	
	public BasePagination getListPage(BasePagination pageParams) {
		Map<String, Object> map = pageParams.getSearchParamsMap();
		List<StoreSku> storeSkuList = new ArrayList<StoreSku>();
		Integer total = storeSkuDao.getTotal(map);
		pageParams.setCurrentTotal(total);
		if (total > 0) {
			storeSkuList = storeSkuDao.getList(map);
		}
		pageParams.setResult(storeSkuList);
		return pageParams;
	}

	public List<StoreSku> findByStoreCode(String storeCode) {
		// TODO Auto-generated method stub
		return storeSkuDao.findByStoreCode(storeCode);
	}

	


}
