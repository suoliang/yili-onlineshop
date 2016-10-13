package com.aladingshop.card.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.card.dao.MemberCardIncomeRecordDao;
import com.aladingshop.card.model.MemberCardIncomeRecord;
import com.aladingshop.card.service.MemberCardIncomeRecordService;
import com.fushionbaby.common.util.BasePagination;

@Service
public class MemberCardIncomeRecordServiceImpl implements MemberCardIncomeRecordService<MemberCardIncomeRecord> {

	@Autowired
	private MemberCardIncomeRecordDao membercardrateincomeDao;

	public void deleteById(Long id) {
		membercardrateincomeDao.deleteById(id);
	}

	public void add(MemberCardIncomeRecord record) {
		membercardrateincomeDao.add(record);
	}

	public MemberCardIncomeRecord findById(Long id) {
		return membercardrateincomeDao.findById(id);
	}

	public List<MemberCardIncomeRecord> findAll() {
		return membercardrateincomeDao.findAll();
	}

	public BasePagination getListPage(BasePagination page) throws DataAccessException {
		Integer total = membercardrateincomeDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<MemberCardIncomeRecord> list = membercardrateincomeDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<MemberCardIncomeRecord>());
		}
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fushionbaby.common.service.BaseService#update(java.lang.Object)
	 */
	public void update(MemberCardIncomeRecord entity) throws DataAccessException {
	}

}
