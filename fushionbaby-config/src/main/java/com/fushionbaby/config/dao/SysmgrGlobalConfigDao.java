package com.fushionbaby.config.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.config.model.SysmgrGlobalConfig;

/**
 * 
 * @author cyla
 * 
 */
public interface SysmgrGlobalConfigDao {

	public void add(SysmgrGlobalConfig sysmgrGlobalConfig);

	public void update(SysmgrGlobalConfig sysmgrGlobalConfig);

	public void deleteSysmgrGlobalConfigById(long id);

	public SysmgrGlobalConfig getSysmgrGlobalConfigById(long id);

	public int getCount(
			@Param(value = "sysmgrGlobalConfig") SysmgrGlobalConfig sysmgrGlobalConfig);

	public List<SysmgrGlobalConfig> getSysmgrGlobalConfigByPage(
			@Param(value = "sysmgrGlobalConfig") SysmgrGlobalConfig sysmgrGlobalConfig,
			@Param(value = "offset") int offset,
			@Param(value = "limit") int limit);
	/**通过code拿到全局对象*/
	public SysmgrGlobalConfig findByCode(String code);

	public Integer getTotal(Map<String, Object> map);

	public List<SysmgrGlobalConfig> getListPage(Map<String, Object> map);
}
