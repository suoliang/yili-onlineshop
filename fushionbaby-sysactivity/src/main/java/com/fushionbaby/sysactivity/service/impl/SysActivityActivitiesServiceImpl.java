package com.fushionbaby.sysactivity.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysactivity.dao.SysActivityActivitiesDao;
import com.fushionbaby.sysactivity.model.SysActivityActivities;
import com.fushionbaby.sysactivity.service.SysActivityActivitiesService;

/**
 * 
 * @author xupeijun
 * 
 */
@Service
public class SysActivityActivitiesServiceImpl implements
		SysActivityActivitiesService<SysActivityActivities> {

	@Autowired
	private SysActivityActivitiesDao sysActivityActivitiesDao;

	public void add(SysActivityActivities sysActivities)
			throws DataAccessException {
		sysActivityActivitiesDao.add(sysActivities);
	}

	public void deleteById(Long id) throws DataAccessException {
		sysActivityActivitiesDao.deleteById(id);
	}

	public void update(SysActivityActivities sysActivities)
			throws DataAccessException {
		sysActivityActivitiesDao.update(sysActivities);
	}

	public SysActivityActivities findById(Long id) throws DataAccessException {
		return sysActivityActivitiesDao.findById(id);
	}

	public List<SysActivityActivities> findAll() throws DataAccessException {
		return sysActivityActivitiesDao.findAll();
	}

	public BasePagination getListPage(BasePagination page) {

		Integer total = this.sysActivityActivitiesDao.getTotal(page
				.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SysActivityActivities> list = this.sysActivityActivitiesDao
					.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SysActivityActivities>());
		}
		return page;
	}

	/**
	 * APP 户外活动列表 索亮
	 */
	public List<SysActivityActivities> getListPageType(int page_index,
			int page_size) throws DataAccessException {
		List<SysActivityActivities> list = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", (page_index - 1) * page_size);
			map.put("limit", page_size);
			list = sysActivityActivitiesDao.getListPageType(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void changeIsOpen(Long id) {
		Map<String, String> map = new HashMap<String, String>();
		SysActivityActivities sysActivityActivities = this.findById(id);
		String isShow = sysActivityActivities.getIsShow();
		isShow = CommonConstant.NO.equals(isShow) ? CommonConstant.YES
				: CommonConstant.NO;
		map.put("id", String.valueOf(id));
		map.put("isShow", isShow);
		this.sysActivityActivitiesDao.changeIsOpen(map);

	}

	public void add(SysActivityActivities sysActivityActivities,
			String myEditorContent) {
		  sysActivityActivities.setIntroduce(myEditorContent);
		this.sysActivityActivitiesDao.add(sysActivityActivities);
	}

	public void updateActivity(String myEditorContent,
			SysActivityActivities sysActivityActivities) {
		sysActivityActivities.setIntroduce(myEditorContent);
		this.sysActivityActivitiesDao.update(sysActivityActivities);
	}

}
