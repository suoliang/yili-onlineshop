package com.fushionbaby.statistics.service;

import java.util.HashMap;
import java.util.List;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.statistics.model.StatisticsMember;
/**
 * @description 统计会员
 * @author 索亮
 * @date 2015年7月30日下午2:53:44
 */
public interface StatisticsMemberService <T extends StatisticsMember> {

	void add(StatisticsMember statisticsMember);
	
	BasePagination getPageList(BasePagination page);
	
	/** 只在统计时使用*/
	List<StatisticsMember> getListStatisticsUse(HashMap<String , String> map);
}
