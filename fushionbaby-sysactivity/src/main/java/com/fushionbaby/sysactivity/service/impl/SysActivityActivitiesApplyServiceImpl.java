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
import com.fushionbaby.sysactivity.dao.SysActivityActivitiesApplyDao;
import com.fushionbaby.sysactivity.model.SysActivityActivitiesApply;
import com.fushionbaby.sysactivity.service.SysActivityActivitiesApplyService;

/**
 * 
 * @author xupeijun
 * 
 */
@Service
public class SysActivityActivitiesApplyServiceImpl implements
		SysActivityActivitiesApplyService<SysActivityActivitiesApply> {

	@Autowired
	private SysActivityActivitiesApplyDao sysActivitiesApplyDao;

	public void add(SysActivityActivitiesApply sysActivitiesApply)
			throws DataAccessException {
		sysActivitiesApplyDao.add(sysActivitiesApply);
	}

	public void deleteById(Long id) throws DataAccessException {
		sysActivitiesApplyDao.deleteById(id);
	}

	public void update(SysActivityActivitiesApply sysActivitiesApply)
			throws DataAccessException {
		sysActivitiesApplyDao.update(sysActivitiesApply);
	}

	public SysActivityActivitiesApply findById(Long id)
			throws DataAccessException {
		return sysActivitiesApplyDao.findById(id);
	}

	public List<SysActivityActivitiesApply> findAll()
			throws DataAccessException {
		return sysActivitiesApplyDao.findAll();
	}

	public BasePagination getListPage(BasePagination page) {
		Integer total = this.sysActivitiesApplyDao.getTotal(page
				.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SysActivityActivitiesApply> list = this.sysActivitiesApplyDao
					.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SysActivityActivitiesApply>());
		}
		return page;
	}

	public void changeIsTouch(Long id) {
		Map<String, String> map = new HashMap<String, String>();
		SysActivityActivitiesApply sysActivityActivitiesApply = this
				.findById(id);
		String isTouch = sysActivityActivitiesApply.getIsTouch();
		isTouch = CommonConstant.NO.equals(isTouch) ? CommonConstant.YES
				: CommonConstant.NO;
		map.put("id", String.valueOf(id));
		map.put("isTouch", isTouch);
		this.sysActivitiesApplyDao.changeIsTouch(map);
	}

	public void changeIsOk(Long id) {
		Map<String, String> map = new HashMap<String, String>();
		SysActivityActivitiesApply sysActivityActivitiesApply = this
				.findById(id);
		String isOk = sysActivityActivitiesApply.getIsOk();
		isOk = CommonConstant.NO.equals(isOk) ? CommonConstant.YES
				: CommonConstant.NO;
		map.put("id", String.valueOf(id));
		map.put("isOk", isOk);
		this.sysActivitiesApplyDao.changeIsOk(map);

	}

	public SysActivityActivitiesApply query(Long memberId,Long activitiesId) throws DataAccessException {
		return sysActivitiesApplyDao.query(memberId,activitiesId);
	}

}
