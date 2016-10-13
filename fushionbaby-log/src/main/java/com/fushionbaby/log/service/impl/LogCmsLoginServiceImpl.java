package com.fushionbaby.log.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.log.dao.LogCmsLoginDao;
import com.fushionbaby.log.model.LogCmsLogin;
import com.fushionbaby.log.service.LogCmsLoginService;

/**
 * 
 * @author King suoliang
 * 
 */
@Service
public class LogCmsLoginServiceImpl implements LogCmsLoginService<LogCmsLogin> {

	@Autowired
	private LogCmsLoginDao logCmsLoginDao;

	public void add(LogCmsLogin logCmsLogin) throws DataAccessException {


		logCmsLoginDao.add(logCmsLogin);
	}

	public void deleteById(Long id) throws DataAccessException {
		logCmsLoginDao.deleteById(id);
	}

	public void update(LogCmsLogin entity) throws DataAccessException {

	}

	public LogCmsLogin findById(Long id) throws DataAccessException {
		return null;
	}

	public List<LogCmsLogin> findAll() throws DataAccessException {
		return null;
	}


	public void addLoginMessage(LogCmsLogin cms) {
	
		this.logCmsLoginDao.add(cms);

	}

	public BasePagination getListPage(BasePagination page) {
		Integer total = this.logCmsLoginDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<LogCmsLogin> list = this.logCmsLoginDao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<LogCmsLogin>());
		}
		return page;
	}

}
