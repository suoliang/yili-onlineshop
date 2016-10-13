package com.aladingshop.alabao.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.alabao.dao.AlabaoIncomeRecordDao;
import com.aladingshop.alabao.model.AlabaoIncomeRecord;
import com.aladingshop.alabao.service.AlabaoIncomeRecordService;
import com.fushionbaby.common.util.BasePagination;
/**
 * @description 阿拉宝收益记录表
 * @author 索亮
 * @date 2015年9月8日下午2:55:19
 */
@Service
public class AlabaoIncomeRecordServiceImpl implements AlabaoIncomeRecordService<AlabaoIncomeRecord> {
	@Autowired
	private AlabaoIncomeRecordDao alabaoIncomeRecordDao;
	
	public void add(AlabaoIncomeRecord alabaoIncomeRecord) {
		alabaoIncomeRecordDao.add(alabaoIncomeRecord);
	}

	public List<AlabaoIncomeRecord> findAllByMemberId(Long memberId) {
		return alabaoIncomeRecordDao.findAllByMemberId(memberId);
	}

	public List<AlabaoIncomeRecord> findAllByAccount(String account) {
		return alabaoIncomeRecordDao.findAllByAccount(account);
	}

	public BasePagination getListPage(BasePagination page) {
		Integer total = alabaoIncomeRecordDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<AlabaoIncomeRecord> list = alabaoIncomeRecordDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<AlabaoIncomeRecord>());
		}
		return page;
	}
	
	public List<AlabaoIncomeRecord> getListPageParam(String account,Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("start", (start-1)*limit);
		map.put("limit", limit);
		return alabaoIncomeRecordDao.getListPage(map);
	}

	public List<AlabaoIncomeRecord> findBillRecordByTime(String account) {
		return alabaoIncomeRecordDao.findBillRecordByTime(account);
	}
	
	
	public String getListPageMoney(BasePagination page) {
		Map<String,Object> map= this.alabaoIncomeRecordDao.getListPageMoney(page.getSearchParamsMap());
		BigDecimal totalMoney = new BigDecimal("0");
		if(null != map){
		 totalMoney= (BigDecimal) map.get("total_income_money");
		}
		return String.valueOf(totalMoney);
	}
	
}
