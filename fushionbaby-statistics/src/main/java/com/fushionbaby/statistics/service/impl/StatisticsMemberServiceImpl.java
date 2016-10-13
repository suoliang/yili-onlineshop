package com.fushionbaby.statistics.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.statistics.dao.StatisticsMemberDao;
import com.fushionbaby.statistics.model.StatisticsMember;
import com.fushionbaby.statistics.service.StatisticsMemberService;
/**
 * @description 统计会员
 * @author 索亮
 * @date 2015年7月30日下午2:53:12
 */
@Service
public class StatisticsMemberServiceImpl implements StatisticsMemberService<StatisticsMember> {
		
	@Autowired
	private StatisticsMemberDao statisticsMemberDao;

	public void add(StatisticsMember statisticsMember) throws DataAccessException {
		statisticsMemberDao.add(statisticsMember);
	}

	public BasePagination getPageList(BasePagination page) {
		Integer total = statisticsMemberDao.getTotal(page.getSearchParamsMap());
		List<StatisticsMember> result = new ArrayList<StatisticsMember>();
		if(total != 0) {
			result = statisticsMemberDao.getPageList(page.getSearchParamsMap());
		}
		page.setTotal(total);
		page.setResult(result);
		return page;
	}

	public List<StatisticsMember> getListStatisticsUse(
			HashMap<String, String> map) {
		return this.statisticsMemberDao.getListStatisticsUse(map);
	}


}
