package com.fushionbaby.statistics.service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.statistics.model.StatisticsSms;
/**
 * 
 * @author King
 *
 */
public interface StatisticsSmsService<T extends StatisticsSms> {
	/** 添加 */
	void add(StatisticsSms statisticsSms);
	/** 分页 */
	BasePagination getListPage(BasePagination page);
}
