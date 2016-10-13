package com.fushionbaby.config.service;

import java.util.List;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.config.model.SysmgrSourceConfig;

/**
 * 
 * @author King suoliang
 * 
 * @param <T>
 */
public interface SysmgrSourceConfigService<T extends SysmgrSourceConfig> extends
		BaseService<T> {
	/** 通过code得到name */
	String findByCode(String sourceCode);
	List<SysmgrSourceConfig> findAll();
}
