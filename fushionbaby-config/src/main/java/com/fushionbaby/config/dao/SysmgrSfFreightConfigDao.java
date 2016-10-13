package com.fushionbaby.config.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.config.model.SysmgrSfFreightConfig;

/**
 * 
 * @author cyla
 * 
 */
public interface SysmgrSfFreightConfigDao {
	void add(SysmgrSfFreightConfig sysmgrSfFreight);

	SysmgrSfFreightConfig findByAreaCode(String areaCode);
	
	void updateByAreaCode(SysmgrSfFreightConfig sysmgrSfFreightConfig);
	
	public Integer getTotal(Map<String, Object> map);

	public List<SysmgrSfFreightConfig> getListPage(Map<String, Object> map);
}
