package com.fushionbaby.sms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sms.dao.SmsTypeConfigDao;
import com.fushionbaby.sms.model.SmsTypeConfig;
import com.fushionbaby.sms.service.SmsTypeConfigService;
/**
 * 
 * @author King
 *
 */
@Service
public class SmsTypeConfigServiceImpl implements SmsTypeConfigService<SmsTypeConfig> {

	@Autowired
	private SmsTypeConfigDao smsTypeDao;
	
	public void add(SmsTypeConfig smsType) throws DataAccessException {
		smsTypeDao.add(smsType);
	}

	public void deleteById(Long id) throws DataAccessException {
		smsTypeDao.deleteById(id);
	}

	public void update(SmsTypeConfig smsType) throws DataAccessException {
		smsTypeDao.update(smsType);
	}

	public SmsTypeConfig findById(Long id) throws DataAccessException {
		return smsTypeDao.findById(id);
	}

	public List<SmsTypeConfig> findAll() throws DataAccessException {
		return smsTypeDao.findAll();
	}

	public BasePagination findBySmsName(BasePagination page) {
		int total = smsTypeDao.getTotal(page.getSearchParamsMap());
		List<SmsTypeConfig> result = new ArrayList<SmsTypeConfig>();
		if(total != 0) {
			result = smsTypeDao.getPageList(page.getSearchParamsMap());
		}
		page.setTotal(total);
		page.setResult(result);
		return page;
	}

}
