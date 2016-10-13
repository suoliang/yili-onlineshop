package com.aladingshop.store.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.store.model.StoreSponsorsDailyDetails;

/***
 * 
 * @author chenyingtao
 *
 *
 */
public interface StoreSponsorsDailyDetailsDao {

	void add(StoreSponsorsDailyDetails entry);

	void update(StoreSponsorsDailyDetails entry);

	List<StoreSponsorsDailyDetails> findByStoreCode(String storeCode);
	
	List<StoreSponsorsDailyDetails> findByApplyNumber(String applyNumber);

	Integer getTotal(Map<String, Object> param);

	List<StoreSponsorsDailyDetails> getListPage(Map<String, Object> param);

	StoreSponsorsDailyDetails findById(Long id);

	StoreSponsorsDailyDetails findByDailyNumber(String dailyNumber);

}
