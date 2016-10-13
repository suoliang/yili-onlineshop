package com.fushionbaby.statistics.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.statistics.dao.StatisticsOrderDao;
import com.fushionbaby.statistics.model.StatisticsOrder;
import com.fushionbaby.statistics.service.StatisticsOrderService;

/**
 * @description 统计订单
 * @author 索亮
 * @date 2015年7月30日下午6:13:18
 */
@Service
public class StatisticsOrderServiceImpl implements StatisticsOrderService<StatisticsOrder>{

	@Autowired
	private StatisticsOrderDao statisticsOrderDao;

	public void add(StatisticsOrder statisticsOrder) throws DataAccessException {
		statisticsOrderDao.add(statisticsOrder);
	}

	public BasePagination getListPage(BasePagination page) {
		int total = statisticsOrderDao.getTotal(page.getSearchParamsMap());
		List<StatisticsOrder> result = new ArrayList<StatisticsOrder>();
		if(total != 0) {
			result = statisticsOrderDao.getPageList(page.getSearchParamsMap());
		}
		page.setTotal(total);
		page.setResult(result);
		return page;
	}

}
