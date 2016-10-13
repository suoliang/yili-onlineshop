package com.fushionbaby.statistics.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.statistics.model.StatisticsSkuSell;

public interface StatisticsSkuSellDao {
	
	Integer getTotal(Map<String, Object> searchParamsMap);

	List<StatisticsSkuSell> getListPage(Map<String, Object> searchParamsMap);
}
