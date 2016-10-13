package com.fushionbaby.statistics.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.statistics.dao.StatisticsSmsDao;
import com.fushionbaby.statistics.model.StatisticsSms;
import com.fushionbaby.statistics.service.StatisticsSmsService;
/**
 * @description 统计短信
 * @author 索亮
 * @date 2015年8月3日下午2:46:24
 */
@Service
public class StatisticsSmsServiceImpl implements StatisticsSmsService<StatisticsSms> {

	@Autowired
	private StatisticsSmsDao statisticsSmsDao;
	
	public void add(StatisticsSms statisticsSms) throws DataAccessException {
		statisticsSmsDao.add(statisticsSms);
	}

	public BasePagination getListPage(BasePagination page) {
		int total = statisticsSmsDao.getTotal(page.getSearchParamsMap());
		List<StatisticsSms> result = new ArrayList<StatisticsSms>();
		if(total != 0) {
			result = statisticsSmsDao.getPageList(page.getSearchParamsMap());
		}
		page.setTotal(total);
		page.setResult(result);
		return page;
	}

}
