package com.fushionbaby.sysactivity.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysactivity.dao.SysActivityWillDao;
import com.fushionbaby.sysactivity.model.SysActivityWill;
import com.fushionbaby.sysactivity.service.SysActivityWillService;

/**
 * 
 * @author cyla
 * 
 */


@Service
public class SysActivityWillServiceImpl implements
		SysActivityWillService<SysActivityWill> {
	@Autowired
	private SysActivityWillDao sysactivityWillDao;

	public void add(SysActivityWill sysactivityWill) {
		sysactivityWillDao.add(sysactivityWill);
	}

	public void deleteById(Long id) {
		sysactivityWillDao.deleteById(id);
	}

	public void update(SysActivityWill sysactivityWill) {
		sysactivityWillDao.update(sysactivityWill);
	}

	public SysActivityWill findById(Long id) throws DataAccessException {
		return this.sysactivityWillDao.findById(id);
	}

	public List<SysActivityWill> findAll() throws DataAccessException {
		return this.sysactivityWillDao.findAll();
	}

	public BasePagination getListPageOne(BasePagination page) {
		Integer total = this.sysactivityWillDao.getTotal(page
				.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SysActivityWill> list = this.sysactivityWillDao
					.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SysActivityWill>());
		}
		return page;
	}
	
	public List<SysActivityWill> findWillShowList() throws DataAccessException {
		return this.sysactivityWillDao.findWillShowList();
	}
	/****
	 * app 端风尚宝贝
	 */
	public List<SysActivityWill> getListPage(int page_index, int page_size) {
		List<SysActivityWill> list = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", (page_index - 1) * page_size);
			map.put("limit", page_size);
			list = sysactivityWillDao.getListPage(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
