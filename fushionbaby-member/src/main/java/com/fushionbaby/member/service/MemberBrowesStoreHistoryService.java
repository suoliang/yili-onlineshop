package com.fushionbaby.member.service;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.MemberBrowesStoreHistory;

public interface MemberBrowesStoreHistoryService<T extends MemberBrowesStoreHistory> {
	
	void add(MemberBrowesStoreHistory memberBrowesStoreHistory);
	
	void update(MemberBrowesStoreHistory memberBrowesStoreHistory);
	
	BasePagination getListPage(BasePagination page)throws DataAccessException;
	
	MemberBrowesStoreHistory findByAccountAndStoreCode(String account,String storeCode);
}
