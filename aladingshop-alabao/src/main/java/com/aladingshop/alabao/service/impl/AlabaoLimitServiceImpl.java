package com.aladingshop.alabao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.alabao.dao.AlabaoLimitDao;
import com.aladingshop.alabao.model.AlabaoLimit;
import com.aladingshop.alabao.service.AlabaoLimitService;
import com.fushionbaby.common.util.BasePagination;

@Service
public class AlabaoLimitServiceImpl implements AlabaoLimitService<AlabaoLimit> {
	
	@Autowired
	private AlabaoLimitDao alabaoLimitDao;
	
	public void add(AlabaoLimit alabaoLimit) {
		alabaoLimitDao.add(alabaoLimit);
	}

	public AlabaoLimit findByAccount(String account) {
		return alabaoLimitDao.findByAccount(account);
	}

	public void update(AlabaoLimit alabaoLimit) {
		alabaoLimitDao.update(alabaoLimit);		
	}

	public BasePagination getListPage(BasePagination page) {
		Integer total = alabaoLimitDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<AlabaoLimit> list = alabaoLimitDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<AlabaoLimit>());
		}
		return page;
	}

	public void deleteById(Long id) {
		alabaoLimitDao.deleteById(id);		
	}


}
