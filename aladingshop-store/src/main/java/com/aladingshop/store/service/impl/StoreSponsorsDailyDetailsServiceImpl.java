package com.aladingshop.store.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.store.dao.StoreSponsorsDailyDetailsDao;
import com.aladingshop.store.model.StoreSponsorsDailyDetails;
import com.aladingshop.store.service.StoreSponsorsDailyDetailsService;
import com.fushionbaby.common.util.BasePagination;
/***
 * 
 * @author chenyingtao
 *
 *
 */

@Service
public class StoreSponsorsDailyDetailsServiceImpl implements
		StoreSponsorsDailyDetailsService<StoreSponsorsDailyDetails> {
	
	@Autowired
	private StoreSponsorsDailyDetailsDao storeSponsorsDailyDetailsDao;
	
	public void add(StoreSponsorsDailyDetails entry) {
		this.storeSponsorsDailyDetailsDao.add(entry);
	}

	public void update(StoreSponsorsDailyDetails entry) {
		this.storeSponsorsDailyDetailsDao.update(entry);
	}

	public List<StoreSponsorsDailyDetails> findByStoreCode(String storeCode) {
		return this.storeSponsorsDailyDetailsDao.findByStoreCode(storeCode);
	}
	
	public List<StoreSponsorsDailyDetails> findByApplyNumber(String applyNumber) {
		return this.storeSponsorsDailyDetailsDao.findByApplyNumber(applyNumber);
	}

	public BasePagination getListPage(BasePagination page) {
		Map<String,Object> param = page.getSearchParamsMap();
		List<StoreSponsorsDailyDetails> StoreSponsorsDailyDetailsList = new ArrayList<StoreSponsorsDailyDetails>();
		Integer total = storeSponsorsDailyDetailsDao.getTotal(param);
		page.setCurrentTotal(total);
		if (total > 0) {
			StoreSponsorsDailyDetailsList = storeSponsorsDailyDetailsDao.getListPage(param);
		}
		page.setResult(StoreSponsorsDailyDetailsList);
		return page;
	}

	public StoreSponsorsDailyDetails findById(Long id) {
		
		return storeSponsorsDailyDetailsDao.findById(id);
	}

	public Integer getApplyDayNumber(String applyNumber) {
		
		return storeSponsorsDailyDetailsDao.findByApplyNumber(applyNumber).size();
	}

	public StoreSponsorsDailyDetails findByDailyNumber(String dailyNumber) {
		
		return storeSponsorsDailyDetailsDao.findByDailyNumber(dailyNumber);
	}
}
