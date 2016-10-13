package com.aladingshop.store.service;

import java.util.List;

import com.aladingshop.store.model.StoreSponsorsDailyDetails;
import com.fushionbaby.common.util.BasePagination;

/***
 * 
 * @author chenyingtao
 *
 *
 */
public interface StoreSponsorsDailyDetailsService<T extends StoreSponsorsDailyDetails> {
	
	void add(StoreSponsorsDailyDetails entry);
	void update(StoreSponsorsDailyDetails entry);
	List<StoreSponsorsDailyDetails> findByStoreCode(String storeCode);
	BasePagination getListPage(BasePagination page);
	StoreSponsorsDailyDetails findById(Long id);
	Integer getApplyDayNumber(String applyNumber);
	StoreSponsorsDailyDetails findByDailyNumber(String dailyNumber);
	List<StoreSponsorsDailyDetails> findByApplyNumber(String applyNumber);
}
