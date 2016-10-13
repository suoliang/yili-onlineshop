package com.fushionbaby.sysactivity.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysactivity.condition.SysActivityFamilyQueryCondition;
import com.fushionbaby.sysactivity.dao.SysActivityFamilyDao;
import com.fushionbaby.sysactivity.model.SysActivityFamily;
import com.fushionbaby.sysactivity.service.SysActivityFamilyService;

/**
 * 
 * @author xupeijun
 * 
 */
@Service
public class SysActivityFamilyServiceImpl implements
		SysActivityFamilyService<SysActivityFamily> {

	@Autowired
	private SysActivityFamilyDao SysActivityFamilyDao;

	public void add(SysActivityFamily sysActivityFamily)
			throws DataAccessException {
		SysActivityFamilyDao.add(sysActivityFamily);
	}

	public void deleteById(Long id) throws DataAccessException {
		SysActivityFamilyDao.deleteById(id);
	}

	public void update(SysActivityFamily sysActivityFamily)
			throws DataAccessException {
		SysActivityFamilyDao.update(sysActivityFamily);
	}

	public SysActivityFamily findById(Long id) throws DataAccessException {
		return SysActivityFamilyDao.findById(id);
	}

	public List<SysActivityFamily> findAll() throws DataAccessException {
		return SysActivityFamilyDao.findAll();
	}

	public BasePagination getListPage(BasePagination page) {
		Integer total = this.SysActivityFamilyDao.getTotal(page
				.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SysActivityFamily> list = this.SysActivityFamilyDao
					.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SysActivityFamily>());
		}
		return page;
	}

	/***
	 * 索亮 APP 亲子课程
	 */
	public List<SysActivityFamily> getListPageTypeTwo(int page_index,
			int page_size) {
		List<SysActivityFamily> list = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", (page_index - 1) * page_size);
			map.put("limit", page_size);
			list = SysActivityFamilyDao.getListPageTypeTwo(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sysactivity.service.SysActivityFamilyService#queryByCondition
	 * (com.fushionbaby.sysactivity.condition.SysActivityFamilyQueryCondition)
	 */
	public List<SysActivityFamily> queryByCondition(
			SysActivityFamilyQueryCondition queryCondition) {
		return SysActivityFamilyDao.queryByCondition(queryCondition);
	}

}
