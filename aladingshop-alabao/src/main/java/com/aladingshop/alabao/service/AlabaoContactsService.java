package com.aladingshop.alabao.service;

import org.springframework.dao.DataAccessException;

import com.aladingshop.alabao.model.AlabaoContacts;
import com.fushionbaby.common.util.BasePagination;

public interface AlabaoContactsService<T extends AlabaoContacts> {

	void add(AlabaoContacts alabaoContacts);
	
	AlabaoContacts findById(Long id);
	
	void update(AlabaoContacts alabaoContacts);
	
	BasePagination getListPage(BasePagination page) throws DataAccessException;
	

	AlabaoContacts findByAccountAndLinkAccount(String account, String otherAccount);
}
