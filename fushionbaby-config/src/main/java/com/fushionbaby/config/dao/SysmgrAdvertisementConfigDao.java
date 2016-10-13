package com.fushionbaby.config.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.config.model.SysmgrAdvertisementConfig;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SysmgrAdvertisementConfigDao {

	public void add(SysmgrAdvertisementConfig sysAdvertisementConfig);

	public void deleteById(Long id);

	public void update(SysmgrAdvertisementConfig sysAdvertisementConfig);

	public SysmgrAdvertisementConfig findById(Long id);

	public SysmgrAdvertisementConfig findByAdCode(String adCode);

	public List<SysmgrAdvertisementConfig> findAll();

	public Integer getTotal(Map<String, Object> map);

	public List<SysmgrAdvertisementConfig> getListPage(Map<String, Object> map);

	/***
	 * 设置配置广告是否禁用
	 * 
	 * @param map
	 */
	public void updateIsUse(Map<String, Object> map);
}
