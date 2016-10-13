package com.fushionbaby.sku.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.sku.dao.SkuImageStandardConfigDao;
import com.fushionbaby.sku.model.SkuImageStandardConfig;
import com.fushionbaby.sku.service.SkuImageStandardConfigService;

/**
 * 
 * @author cyla
 * 
 */
@Service
public class SkuImageStandardConfigServiceImpl implements
		SkuImageStandardConfigService<SkuImageStandardConfig> {

	@Autowired
	private SkuImageStandardConfigDao skuImageStandardDao;

	public SkuImageStandardConfig findByCode(String code) {
		return skuImageStandardDao.findByCode(code);
	}

}
