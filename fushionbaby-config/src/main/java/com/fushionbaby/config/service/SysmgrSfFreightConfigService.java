package com.fushionbaby.config.service;

import java.util.Map;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrSfFreightConfig;

/**
 * 
 * @author cyla
 * 
 */
public interface SysmgrSfFreightConfigService<T extends SysmgrSfFreightConfig> {
	void add(T t);

	T findByAreaCode(String areaCode);
	
	void updateByAreaCode(SysmgrSfFreightConfig sysmgrSfFreightConfig);
	
	public Integer getTotal(Map<String, Object> map);

	public BasePagination getListPage(BasePagination page);
}
