package com.fushionbaby.facade.biz.common.config.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.config.model.SysmgrImportanceConfig;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.facade.biz.common.config.ImportanceConfigServiceFacade;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月19日下午2:19:28
 */
@Service
public class ImportanceConfigServiceFacadeImpl implements ImportanceConfigServiceFacade{

	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	
	public SysmgrImportanceConfig findByCode(String code) {
		return this.sysmgrImportanceConfigService.findByCode(code);
	}

}
