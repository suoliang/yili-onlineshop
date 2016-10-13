package com.fushionbaby.config.service;

import com.fushionbaby.config.model.SysmgrAlabaoConfig;

/***
 * @description 如意消费卡余额和折扣的关联Service
 * @author 索亮
 * @date 2016年2月26日下午3:52:38
 */
public interface SysmgrAlabaoConfigService<T extends SysmgrAlabaoConfig> {
	/** 查询出余额区间对象 */
	SysmgrAlabaoConfig findByMinMaxValue(String value);
	
}
