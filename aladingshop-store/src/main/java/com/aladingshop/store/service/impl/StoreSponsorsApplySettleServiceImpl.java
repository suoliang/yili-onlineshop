package com.aladingshop.store.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.store.dao.StoreSponsorsApplySettleDao;
import com.aladingshop.store.model.StoreSponsorsApplySettle;
import com.aladingshop.store.service.StoreSponsorsApplySettleService;
import com.fushionbaby.common.util.BasePagination;

@Service
public class StoreSponsorsApplySettleServiceImpl implements
		StoreSponsorsApplySettleService<StoreSponsorsApplySettle> {
	
	@Autowired
	private StoreSponsorsApplySettleDao storeSponsorsApplySettleDao;

	public void add(StoreSponsorsApplySettle entry) {
		this.storeSponsorsApplySettleDao.add(entry);
	}

	public void update(StoreSponsorsApplySettle entry) {
		this.storeSponsorsApplySettleDao.update(entry);
	}

	public List<StoreSponsorsApplySettle> findByStoreCode(String storeCode) {
		return this.storeSponsorsApplySettleDao.findByStoreCode(storeCode);
	}

	public BasePagination getListPage(BasePagination page) {
		
		Map<String,Object> param = page.getSearchParamsMap();
		List<StoreSponsorsApplySettle> storeSponsorsApplySettleList = new ArrayList<StoreSponsorsApplySettle>();
		Integer total = storeSponsorsApplySettleDao.getTotal(param);
		page.setCurrentTotal(total);
		if (total > 0) {
			storeSponsorsApplySettleList = storeSponsorsApplySettleDao.getListPage(param);
		}
		page.setResult(storeSponsorsApplySettleList);
		return page;
	}

	public StoreSponsorsApplySettle findByApplyNumber(String applyNumber) {
		return storeSponsorsApplySettleDao.findByApplyNumber(applyNumber);
	}

	public StoreSponsorsApplySettle findById(String id) {
		return storeSponsorsApplySettleDao.findById(id);
	}

}
