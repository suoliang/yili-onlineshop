package com.fushionbaby.statistics.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.statistics.model.StatisticsOrder;
/**
 * @description 统计订单
 * @author 索亮
 * @date 2015年8月3日下午2:43:59
 */
public interface StatisticsOrderDao {

	void add(StatisticsOrder statisticsOrder);
	
	Integer getTotal(Map<String, Object> searchParamsMap);

	List<StatisticsOrder> getPageList(Map<String, Object> searchParamsMap);
}
