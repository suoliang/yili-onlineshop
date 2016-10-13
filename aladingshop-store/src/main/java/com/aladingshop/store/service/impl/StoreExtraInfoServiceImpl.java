package com.aladingshop.store.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.store.dao.StoreExtraInfoDao;
import com.aladingshop.store.model.StoreExtraInfo;
import com.aladingshop.store.service.StoreExtraInfoService;
import com.fushionbaby.common.util.BasePagination;

@Service
public class StoreExtraInfoServiceImpl implements StoreExtraInfoService<StoreExtraInfo> {

	@Autowired
	private StoreExtraInfoDao storeExtraInfoDao;
	
	public void add(StoreExtraInfo storeExtraInfo) {
		storeExtraInfoDao.add(storeExtraInfo);
		
	}

	public void updateByStoreCode(StoreExtraInfo storeExtraInfo) {
		storeExtraInfoDao.updateByStoreCode(storeExtraInfo);
		
	}

	public void deleteById(Long id) {
		storeExtraInfoDao.deleteById(id);
		
	}


	public void deleteByStoreCode(String storeCode) {
		storeExtraInfoDao.deleteByStoreCode(storeCode);
		
	}

	public StoreExtraInfo findById(Long id) {
		return storeExtraInfoDao.findById(id);
	}


	public StoreExtraInfo findByStoreCode(String storeCode) {
		return storeExtraInfoDao.findByStoreCode(storeCode);
	}

	public BasePagination getListPage(BasePagination page) {
		Map<String,Object> params = page.getSearchParamsMap();
		List<StoreExtraInfo> list = new ArrayList<StoreExtraInfo>();
		Integer total = storeExtraInfoDao.getTotal(params);
		page.setTotal(total);
		if(total > 0){
			list = storeExtraInfoDao.getPageList(params);
		}
		page.setResult(list);
		return page;
	}

	public void update(StoreExtraInfo storeExtraInfo) {
		storeExtraInfoDao.update(storeExtraInfo);
		
	}

	


}
