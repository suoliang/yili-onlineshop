package com.aladingshop.store.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.store.dao.StoreDao;
import com.aladingshop.store.model.Store;
import com.aladingshop.store.querycondition.StoreQueryCondition;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.RandomNumUtil;

@Service
public class StoreServiceImpl implements StoreService<Store> {

	@Autowired
	private StoreDao storeDao;
	
	public void add(Store stroe) {
		stroe.setCode(RandomNumUtil.getRandom("3", 20));
		storeDao.add(stroe);
		
	}


	public void deleteById(Long id) {
		storeDao.deleteById(id);
		
	}


	public Store findById(Long id) {
		return storeDao.findById(id);
	}

	public Store findByNumber(String number) {
		return storeDao.findByNumber(number);
	}

	public Store findByCode(String code) {
		return storeDao.findByCode(code);
	}

	
	public BasePagination getListPage(BasePagination pageParams) {
		Map<String, Object> map = pageParams.getSearchParamsMap();
		List<Store> storeList = new ArrayList<Store>();
		Integer total = storeDao.getTotal(map);
		pageParams.setCurrentTotal(total);
		if (total > 0) {
			storeList = storeDao.getList(map);
		}
		pageParams.setResult(storeList);
		return pageParams;
	}


	public void update(Store stroe) {
		storeDao.update(stroe);
		
	}


	public List<Store> queryByCondition(StoreQueryCondition queryCondition) {
		
		
		return storeDao.queryByCondition(queryCondition);
	}


	public Store findLastOne() {
		return this.storeDao.findLastOne();
	}


	public List<Store> findAll() {
		
		return this.storeDao.findAll();
	}


}
