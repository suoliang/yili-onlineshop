package com.fushionbaby.statistics.service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.statistics.model.StatisticsOrder;
/**
 * @description 统计订单
 * @author 索亮
 * @date 2015年7月30日下午6:13:34
 */
public interface StatisticsOrderService<T extends StatisticsOrder> {

	void add(StatisticsOrder statisticsOrder);
	
	BasePagination getListPage(BasePagination page);
}
