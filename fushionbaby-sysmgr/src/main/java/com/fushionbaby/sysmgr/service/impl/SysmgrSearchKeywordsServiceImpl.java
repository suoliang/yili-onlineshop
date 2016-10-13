package com.fushionbaby.sysmgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fushionbaby.sysmgr.dao.SysmgrSearchKeywordsDao;
import com.fushionbaby.sysmgr.model.SysmgrSearchKeywords;
import com.fushionbaby.sysmgr.service.SysmgrSearchKeywordsService;

/**
 * 
 * @author King
 * 
 */
@Service
@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class})
public class SysmgrSearchKeywordsServiceImpl   implements
		SysmgrSearchKeywordsService<SysmgrSearchKeywords> {

	/**
	 * @description 类描述...
	 * @author 孟少博
	 * @date 2015年12月2日下午4:20:56
	 */
	@Autowired
	private SysmgrSearchKeywordsDao sysSearchKeywordsDao;

	public void add(SysmgrSearchKeywords sysSearchKeywords)
			throws DataAccessException {
		sysSearchKeywordsDao.add(sysSearchKeywords);
	}

	public void update(SysmgrSearchKeywords entity) throws DataAccessException {
		sysSearchKeywordsDao.update(entity);
	}

	public void deleteById(Long id) throws DataAccessException {
		sysSearchKeywordsDao.deleteById(id);
	}

	public SysmgrSearchKeywords findById(Long id) throws DataAccessException {

		return sysSearchKeywordsDao.findById(id);
	}

	public List<SysmgrSearchKeywords> findAll() throws DataAccessException {
		return sysSearchKeywordsDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sysmgr.service.SysSearchKeywordsService#queryBySearchKey
	 * (java.lang.String)
	 */
	public SysmgrSearchKeywords queryBySearchKey(String searchKey,
			String sourceCode) {
		return sysSearchKeywordsDao.queryBySearchKey(searchKey, sourceCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sysmgr.service.SysmgrSearchKeywordsService#insertSearchKey
	 * (java.lang.String, java.lang.Integer)
	 */
	public void insertSearchKey(String searchKey, String source, String ip) {
		SysmgrSearchKeywords searchKeywords = null;
		searchKeywords = this.queryBySearchKey(searchKey, source);
		if (searchKeywords != null) {
			Integer count = searchKeywords.getCount() + 1;
			searchKeywords.setCount(count);
			searchKeywords.setKeyword(searchKey);
			searchKeywords.setIp(ip);
			this.update(searchKeywords);
		} else {
			searchKeywords = new SysmgrSearchKeywords();
			searchKeywords.setCount(1);
			searchKeywords.setSourceCode(source);
			searchKeywords.setKeyword(searchKey);
			searchKeywords.setIp(ip);
			this.add(searchKeywords);
		}

	}

	public void addBatch(List<SysmgrSearchKeywords> list) throws Exception {
		for (SysmgrSearchKeywords sysmgrSearchKeywords : list) {
			sysSearchKeywordsDao.add(sysmgrSearchKeywords);
		}
		
	}
}
