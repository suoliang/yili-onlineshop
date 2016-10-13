package com.fushionbaby.statistics.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.statistics.dao.StatisticsSkuSellDao;
import com.fushionbaby.statistics.model.StatisticsSkuSell;
import com.fushionbaby.statistics.service.StatisticsSkuSellService;

/**
 * @description 统计订单
 * @author 索亮
 * @date 2015年7月30日下午6:13:18
 */
@Service
public class StatisticsSkuSellServiceImpl implements StatisticsSkuSellService<StatisticsSkuSell>{

	@Autowired
	private StatisticsSkuSellDao statisticsSkuSellDao;

	public BasePagination getListPage(BasePagination page) {
		int total = statisticsSkuSellDao.getTotal(page.getSearchParamsMap());
		List<StatisticsSkuSell> result = new ArrayList<StatisticsSkuSell>();
		if(total != 0) {
			result = statisticsSkuSellDao.getListPage(page.getSearchParamsMap());
		}
		page.setTotal(total);
		page.setResult(result);
		return page;
	}

}
