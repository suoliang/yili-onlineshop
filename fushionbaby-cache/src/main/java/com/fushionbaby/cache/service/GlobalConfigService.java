package com.fushionbaby.cache.service;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.config.model.SysmgrGlobalConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
/**
 * 
 * @author mengshaobo
 *@version 2015-7-2
 */
@Service
public class GlobalConfigService implements GlobalConfig {

	@Autowired
	private SysmgrGlobalConfigService globalConfigService;
	public String  findByCode(String code) {
		
		SysmgrGlobalConfig config = globalConfigService.findByCode(code);
		if(ObjectUtils.notEqual(null, config)){
			return config.getValue();
		}
		return StringUtils.EMPTY;
	}

}
