package com.fushionbaby.config.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.config.dao.SysmgrImportanceConfigDao;
import com.fushionbaby.config.model.SysmgrImportanceConfig;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;

/**
 * 
 * @author cyla
 * 
 */

@Service
public class SysmgrImportanceConfigServiceImpl implements SysmgrImportanceConfigService{

	@Autowired
	private SysmgrImportanceConfigDao sysmgrImportanceConfigDao;

	public SysmgrImportanceConfig findByCode(String code) {
		return sysmgrImportanceConfigDao.findByCode(code);
	}
	
}
