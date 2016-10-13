package com.fushionbaby.member.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.dao.MemberBrowesStoreHistoryDao;
import com.fushionbaby.member.model.MemberBrowesStoreHistory;
import com.fushionbaby.member.service.MemberBrowesStoreHistoryService;

@Service
public class MemberBrowesStoreHistoryServiceImpl implements
		MemberBrowesStoreHistoryService<MemberBrowesStoreHistory> {
	
	@Autowired
	private MemberBrowesStoreHistoryDao memberBrowesStoreHistoryDao;

	public void add(MemberBrowesStoreHistory memberBrowesStoreHistory) {
		this.memberBrowesStoreHistoryDao.add(memberBrowesStoreHistory);
	}

	public void update(MemberBrowesStoreHistory memberBrowesStoreHistory) {
		this.memberBrowesStoreHistoryDao.update(memberBrowesStoreHistory);
		
	}

	public BasePagination getListPage(BasePagination page) throws DataAccessException {
		Map<String,Object> params = page.getSearchParamsMap();
		Integer total = memberBrowesStoreHistoryDao.getTotal(params);
		page.setTotal(total);
		if(total > 0){
			page.setResult(memberBrowesStoreHistoryDao.getListPage(params));
		}else{
			page.setResult(new ArrayList<MemberBrowesStoreHistory>());
		}
		return page;
	}

	public MemberBrowesStoreHistory findByAccountAndStoreCode(String account,
			String storeCode) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("memberAccount", account);
		map.put("storeCode", storeCode);
		return memberBrowesStoreHistoryDao.findByAccountAndStoreCode(map);
	}



}
