package com.fushionbaby.sysmgr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.sysmgr.dao.SysmgrAppHomeConfigDao;
import com.fushionbaby.sysmgr.model.SysmgrAppHomeConfig;
import com.fushionbaby.sysmgr.service.SysmgrAppHomeConfigService;
/**
 * 
 * @description 类描述...
 * @author 孟少博
 * @date 2016年1月11日下午6:01:58
 */
@Service
public class SysmgrAppHomeConfigServiceImpl implements
		SysmgrAppHomeConfigService<SysmgrAppHomeConfig> {
	
	@Autowired
	private SysmgrAppHomeConfigDao dao;

	public SysmgrAppHomeConfig findByPlatfrom(Integer platform) {
		
		return dao.findByPlatfrom(platform);
	}

}
