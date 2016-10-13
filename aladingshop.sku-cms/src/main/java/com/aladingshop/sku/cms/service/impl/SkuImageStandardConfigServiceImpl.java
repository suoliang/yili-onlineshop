package com.aladingshop.sku.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuImageStandardConfigDao;
import com.aladingshop.sku.cms.model.SkuImageStandardConfig;
import com.aladingshop.sku.cms.service.SkuImageStandardConfigService;

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
