package com.fushionbaby.member.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.member.model.MemberBrowesStoreHistory;

public interface MemberBrowesStoreHistoryDao {

	void add(MemberBrowesStoreHistory memberBrowesStoreHistory);
	
	void update(MemberBrowesStoreHistory memberBrowesStoreHistory);
	
	List<MemberBrowesStoreHistory> getListPage(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
	
	MemberBrowesStoreHistory findByAccountAndStoreCode(Map<String, Object> map);
}
