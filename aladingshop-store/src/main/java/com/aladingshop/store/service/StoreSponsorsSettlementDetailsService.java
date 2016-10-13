package com.aladingshop.store.service;

import java.util.List;

import com.aladingshop.store.model.StoreSponsorsSettlementDetails;
import com.fushionbaby.common.util.BasePagination;
/***
 * 
 * @author chenyingtao
 *
 *
 */
public interface StoreSponsorsSettlementDetailsService<T extends StoreSponsorsSettlementDetails> {
	
	void add(StoreSponsorsSettlementDetails entry);
	void update(StoreSponsorsSettlementDetails entry);
	List<StoreSponsorsSettlementDetails> findByStoreCode(String storeCode);
	BasePagination getListPage(BasePagination page);
}
