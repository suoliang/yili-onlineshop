package com.fushionbaby.statistics.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.statistics.model.StatisticsSms;
/**
 * @description 统计短信
 * @author 索亮
 * @date 2015年8月3日下午2:44:16
 */
public interface StatisticsSmsDao {
		
	void add(StatisticsSms statisticsSms);

	Integer getTotal(Map<String, Object> searchParamsMap);

	List<StatisticsSms> getPageList(Map<String, Object> searchParamsMap);
}
