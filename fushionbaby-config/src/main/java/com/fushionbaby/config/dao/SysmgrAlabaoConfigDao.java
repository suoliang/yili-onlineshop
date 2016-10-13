package com.fushionbaby.config.dao;

import java.util.Map;
import com.fushionbaby.config.model.SysmgrAlabaoConfig;

/***
 * @description 如意消费卡余额和折扣的关联DAO
 * @author 索亮
 * @date 2016年2月26日下午3:45:53
 */
public interface SysmgrAlabaoConfigDao {
	/** 查询出余额区间对象 */
	SysmgrAlabaoConfig findByMinMaxValue(Map<String, Object> map);
	
}
