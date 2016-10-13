package com.fushionbaby.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fushionbaby.statistics.model.StatisticsMember;
/**
 * @description 统计会员
 * @author 索亮
 * @date 2015年7月30日下午2:53:32
 */
public interface StatisticsMemberDao {
		
	void add(StatisticsMember statisticsMember);
	
	Integer getTotal(Map<String, Object> searchParamsMap);

	List<StatisticsMember> getPageList(Map<String, Object> searchParamsMap);	
	
	/** 只在统计时使用*/
	List<StatisticsMember> getListStatisticsUse(HashMap<String , String> map);
}
