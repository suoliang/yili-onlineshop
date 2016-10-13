package com.fushionbaby.config.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.config.model.SysmgrExpressConfig;
/***
 * 快递物流信息
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月3日上午9:52:51
 */
public interface SysmgrExpressConfigDao {

	
	public void add(SysmgrExpressConfig sysmgrExpressConfig);

	public SysmgrExpressConfig findByExpressCompanyName(String name);

	public Integer getTotal(Map<String, Object> searchParamsMap);
	
	public List<SysmgrExpressConfig> getListPage(Map<String, Object> searchParamsMap);

	public List<SysmgrExpressConfig> findAll();

}
