package com.aladingshop.store.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.store.model.StoreSponsorsSettlementDetails;

public interface StoreSponsorsSettlementDetailsDao {

	void add(StoreSponsorsSettlementDetails entry);

	void update(StoreSponsorsSettlementDetails entry);

	List<StoreSponsorsSettlementDetails> findByStoreCode(String storeCode);

	Integer getTotal(Map<String, Object> param);

	List<StoreSponsorsSettlementDetails> getListPage(Map<String, Object> param);

	List<StoreSponsorsSettlementDetails> findByDailyNumber(String dailyNumber);

}
