package com.aladingshop.store.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.store.model.Store;
import com.aladingshop.store.querycondition.StoreQueryCondition;


public interface StoreDao {
	
	 void add(Store stroe);
	
	void update(Store stroe) ;
	
	 void deleteById(Long id);
	
	
	 Store findById(Long id);
	
	 Store findByNumber(String number);
	
	 Store findByCode(String code);
	
	List<Store> getList(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
	
	
	List<Store> queryByCondition(StoreQueryCondition queryCondition);
	
	
	Store findLastOne();

	List<Store> findAll();
	
}
