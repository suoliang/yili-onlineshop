package com.aladingshop.store.service;


import java.util.List;

import com.aladingshop.store.model.Store;
import com.aladingshop.store.querycondition.StoreQueryCondition;
import com.fushionbaby.common.util.BasePagination;

public interface StoreService <T extends Store>{
	
	public void add(Store stroe);
	
	public void update(Store stroe);
	
	public void deleteById(Long id);
	
	public Store findById(Long id);
	
	public Store findByNumber(String number);
	
	public Store findByCode(String code);
	
	BasePagination getListPage(BasePagination pageParams);
	
	
	List<Store> queryByCondition(StoreQueryCondition queryCondition);
	
	
	Store findLastOne();

	public List<Store> findAll();
	
}
