package com.aladingshop.alabao.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.alabao.model.AlabaoContacts;



public interface AlabaoContactsDao {

	
	void add(AlabaoContacts alabaoContacts);
	
	void update(AlabaoContacts alabaoContacts);
	
	AlabaoContacts findById(Long id);
	
	Integer getTotal(Map<String, Object> map);
	
	List<AlabaoContacts> getListPage(Map<String, Object> map);
	
	AlabaoContacts findByAccountAndLinkAccount(Map<String, Object> map);
}
