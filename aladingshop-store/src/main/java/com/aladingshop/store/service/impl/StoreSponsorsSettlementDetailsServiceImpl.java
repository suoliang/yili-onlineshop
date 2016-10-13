package com.aladingshop.store.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.store.dao.StoreSponsorsSettlementDetailsDao;
import com.aladingshop.store.model.StoreSponsorsSettlementDetails;
import com.aladingshop.store.service.StoreSponsorsSettlementDetailsService;
import com.fushionbaby.common.util.BasePagination;


@Service
public class StoreSponsorsSettlementDetailsServiceImpl implements
		StoreSponsorsSettlementDetailsService<StoreSponsorsSettlementDetails> {
	
	@Autowired
	private StoreSponsorsSettlementDetailsDao storeSponsorsSettlementDetailsDao;
	
	public void add(StoreSponsorsSettlementDetails entry) {
		this.storeSponsorsSettlementDetailsDao.add(entry);
	}

	public void update(StoreSponsorsSettlementDetails entry) {
		this.storeSponsorsSettlementDetailsDao.update(entry);
	}

	public List<StoreSponsorsSettlementDetails> findByStoreCode(String storeCode) {
		return this.storeSponsorsSettlementDetailsDao.findByStoreCode(storeCode);
	}

	public BasePagination getListPage(BasePagination page) {
		Map<String,Object> param = page.getSearchParamsMap();
		List<StoreSponsorsSettlementDetails> storeSponsorsSettlementDetailsList = new ArrayList<StoreSponsorsSettlementDetails>();
		Integer total = storeSponsorsSettlementDetailsDao.getTotal(param);
		page.setCurrentTotal(total);
		if (total > 0) {
			storeSponsorsSettlementDetailsList = storeSponsorsSettlementDetailsDao.getListPage(param);
		}
		page.setResult(storeSponsorsSettlementDetailsList);
		return page;
	}

}
