package com.fushionbaby.config.service;

import java.util.List;
import java.util.Map;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrGlobalConfig;

/**
 *
 * @author cyla
 * 
 */
public interface SysmgrGlobalConfigService {
    /**
     * 添加操作
     */
	public void add(SysmgrGlobalConfig sysmgrGlobalConfig);
	
	/**
     * 更新操作
     */
	public void update(SysmgrGlobalConfig sysmgrGlobalConfig);
	
	/**
     * 删除操作
     */
	public void deleteSysmgrGlobalConfigById(long id);
	/**
	 * 通过code拿到全局对象
	 * */
	SysmgrGlobalConfig findByCode(String code);
	
	/**
	  *通过code拿到全局对象的值
	  * @param code 
	  * @return
	  */
	 Integer getConfigValueByCode(final String code);
	 
	
	/**
     * 根据主键字段查询
     */
	public SysmgrGlobalConfig getSysmgrGlobalConfigById(long id);
	
	/**
     * 分页查询
     */
	public List<SysmgrGlobalConfig> getSysmgrGlobalConfigByPage(SysmgrGlobalConfig sysmgrGlobalConfig, int offset, int limit);
	
	/**
     * 获取总记录数
     */
	public int getCount(SysmgrGlobalConfig sysmgrGlobalConfig);
	
	public Integer getTotal(Map<String, Object> map);

	public BasePagination getListPage(BasePagination page);

	 
}
