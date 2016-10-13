package com.fushionbaby.config.dao;

import java.util.List;

import com.fushionbaby.config.model.SysmgrSourceConfig;

/**
 * 
 * @author King suoliang
 * 
 */
public interface SysmgrSourceConfigDao {

	void add(SysmgrSourceConfig sysSource);

	void deleteById(Long id);

	void update(SysmgrSourceConfig sysSource);

	SysmgrSourceConfig findById(Long id);

	List<SysmgrSourceConfig> findAll();

	/** 通过code */
	SysmgrSourceConfig findByCode(String sourceCode);
}
