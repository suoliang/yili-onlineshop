package com.fushionbaby.push.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.push.dao.SysmgrJpushDao;
import com.fushionbaby.push.model.SysmgrJpush;
import com.fushionbaby.push.service.SysmgrJpushService;
/***
 * 
 * @author xupeijun
 *
 */
 @Service
public class SysmgrJpushServiceImpl  implements SysmgrJpushService<SysmgrJpush>{

	@Autowired 
	private SysmgrJpushDao jpushDao;
	
	public void add(SysmgrJpush entity) throws DataAccessException {
		jpushDao.add(entity);
	}
	public void deleteById(Long id) throws DataAccessException {
		jpushDao.deleteById(id);
	}
	public void update(SysmgrJpush entity) throws DataAccessException {
		jpushDao.update(entity);
	}
	public SysmgrJpush findById(Long id) throws DataAccessException {
		return jpushDao.findById(id);
	}
	public List<SysmgrJpush> findAll() {
		return jpushDao.findAll();
	}
	public BasePagination getListPage(BasePagination page) {
		Integer total = this.jpushDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SysmgrJpush> list = this.jpushDao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SysmgrJpush>());
		}
		return page;
	}

}
