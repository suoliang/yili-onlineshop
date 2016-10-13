package com.fushionbaby.config.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.config.dao.SysmgrSourceConfigDao;
import com.fushionbaby.config.model.SysmgrSourceConfig;
import com.fushionbaby.config.service.SysmgrSourceConfigService;

/**
 * 
 * @author King suoliang
 * 
 */
@Service
public class SysmgrSourceConfigServiceImpl implements
		SysmgrSourceConfigService<SysmgrSourceConfig> {

	@Autowired
	private SysmgrSourceConfigDao sysSourceDao;

	public void add(SysmgrSourceConfig sysSource) throws DataAccessException {
		sysSourceDao.add(sysSource);
	}

	public void deleteById(Long id) throws DataAccessException {
		sysSourceDao.deleteById(id);
	}

	public void update(SysmgrSourceConfig sysSource) throws DataAccessException {
		sysSourceDao.update(sysSource);
	}

	public SysmgrSourceConfig findById(Long id) throws DataAccessException {
		return sysSourceDao.findById(id);
	}

	public List<SysmgrSourceConfig> findAll() throws DataAccessException {
		return sysSourceDao.findAll();
	}

	public String findByCode(String sourceCode) {
		SysmgrSourceConfig ss = this.sysSourceDao.findByCode(sourceCode);

			return ss.getName();


	}

}
