package com.fushionbaby.config.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.dao.SysmgrVersionConfigDao;
import com.fushionbaby.config.model.SysmgrVersionConfig;
import com.fushionbaby.config.service.SysmgrVersionConfigService;

/**
 * 
 * @author xupeijun
 * 
 */
@Service
public class SysmgrVersionConfigServiceImpl implements
		SysmgrVersionConfigService<SysmgrVersionConfig> {

	@Autowired
	private SysmgrVersionConfigDao sysVersionDao;

	public void add(SysmgrVersionConfig sysVersion) throws DataAccessException {
		sysVersionDao.add(sysVersion);
	}

	public void deleteById(Long id) throws DataAccessException {
		sysVersionDao.deleteById(id);
	}

	public void update(SysmgrVersionConfig sysVersion) throws DataAccessException {
		sysVersionDao.update(sysVersion);
	}

	public SysmgrVersionConfig findById(Long id) throws DataAccessException {
		return sysVersionDao.findById(id);
	}

	public List<SysmgrVersionConfig> findAll() throws DataAccessException {
		return sysVersionDao.findAll();
	}
	
	public int getLatestVersionNum(String sourceCode) {
		return sysVersionDao.getLatestVersionNum(sourceCode);
	}
	
	public SysmgrVersionConfig getLatestVersionByLatestVersionNumAndSourceCode(SysmgrVersionConfig sysmgrVersion) {
		return sysVersionDao.getLatestVersionByLatestVersionNumAndSourceCode(sysmgrVersion);
	}
	
	public List<SysmgrVersionConfig> checkIsNeedUpdateOrNot(SysmgrVersionConfig sysVersion) {
		return sysVersionDao.checkIsNeedUpdateOrNot(sysVersion);
	}

	public BasePagination getListPage(BasePagination page) {

		Integer total = sysVersionDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SysmgrVersionConfig> list = sysVersionDao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SysmgrVersionConfig>());
		}

		return page;
	}

}
