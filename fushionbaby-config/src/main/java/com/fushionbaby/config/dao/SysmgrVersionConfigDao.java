package com.fushionbaby.config.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.config.model.SysmgrVersionConfig;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SysmgrVersionConfigDao {

	void add(SysmgrVersionConfig sysVersion);

	void deleteById(Long id);

	void update(SysmgrVersionConfig sysVersion);

	SysmgrVersionConfig findById(Long id);

	List<SysmgrVersionConfig> findAll();

	/**
	 * 根据来源得到最新的版本号(IOS,Android)
	 */
	int getLatestVersionNum(String sourceCode);

	/**
	 * 得到最新的版本信息
	 */
	SysmgrVersionConfig getLatestVersionByLatestVersionNumAndSourceCode(SysmgrVersionConfig sysmgrVersion);
	
	/***
	 * 检查是否需要更新到最新版(IOS,Android)
	 */
	List<SysmgrVersionConfig> checkIsNeedUpdateOrNot(SysmgrVersionConfig sysmgrVersion);
	
	/***
	 * 分页获得总数量
	 * 
	 * @param searchParamsMap
	 * @return
	 */
	Integer getTotal(Map<String, Object> searchParamsMap);

	/***
	 * 分页获得总页数
	 * 
	 * @param searchParamsMap
	 * @return
	 */
	List<SysmgrVersionConfig> getListPage(Map<String, Object> searchParamsMap);

}