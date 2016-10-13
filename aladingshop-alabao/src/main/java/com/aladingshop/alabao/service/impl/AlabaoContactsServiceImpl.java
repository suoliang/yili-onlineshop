package com.aladingshop.alabao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.alabao.dao.AlabaoContactsDao;
import com.aladingshop.alabao.model.AlabaoContacts;
import com.aladingshop.alabao.service.AlabaoContactsService;
import com.fushionbaby.common.util.BasePagination;

@Service
public class AlabaoContactsServiceImpl implements AlabaoContactsService<AlabaoContacts> {

	@Autowired
	private AlabaoContactsDao alabaoContactsDao;
	
	public void add(AlabaoContacts alabaoContacts) {
		alabaoContactsDao.add(alabaoContacts);
	}

	public AlabaoContacts findById(Long id) {
		
		return alabaoContactsDao.findById(id);
	}

	public void update(AlabaoContacts alabaoContacts) {
		alabaoContactsDao.update(alabaoContacts);
	}

	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {
		Integer total = alabaoContactsDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if(total > 0){
			List<AlabaoContacts> result = alabaoContactsDao.getListPage(page.getSearchParamsMap());
			page.setResult(result);
		}else{
			page.setResult(new ArrayList<AlabaoContacts>());
		}
		return page;
	}


	public AlabaoContacts findByAccountAndLinkAccount(String account,
			String otherAccount) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("linkAccount", otherAccount);
		return alabaoContactsDao.findByAccountAndLinkAccount(map);
	}

}
