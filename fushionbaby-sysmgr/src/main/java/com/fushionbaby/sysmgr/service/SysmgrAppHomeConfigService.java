package com.fushionbaby.sysmgr.service;

import com.fushionbaby.sysmgr.model.SysmgrAppHomeConfig;

/**
 * 
 * @description 类描述...
 * @author 孟少博
 * @date 2016年1月11日下午6:01:24
 */
public interface SysmgrAppHomeConfigService<T extends SysmgrAppHomeConfig> {
	SysmgrAppHomeConfig findByPlatfrom(Integer platform);
}
