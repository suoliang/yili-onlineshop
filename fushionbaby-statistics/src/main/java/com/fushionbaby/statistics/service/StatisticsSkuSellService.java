package com.fushionbaby.statistics.service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.statistics.model.StatisticsSkuSell;
/**
 * @description 统计订单
 * @author 索亮
 * @date 2015年7月30日下午6:13:34
 */
public interface StatisticsSkuSellService<T extends StatisticsSkuSell> {

	BasePagination getListPage(BasePagination page);
}
