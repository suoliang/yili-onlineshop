package com.fushionbaby.config.service;

import java.util.List;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrExpressConfig;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月3日上午9:55:50
 */
public interface SysmgrExpressConfigService {

	public SysmgrExpressConfig findByExpressCompanyName(String name);
	
	public void add(SysmgrExpressConfig sysAdvertisementConfig);

	/***
	 * 分页查询
	 * @param page
	 * @return
	 */
	public BasePagination getListPage(BasePagination page);

	public List<SysmgrExpressConfig> findAll();

}
