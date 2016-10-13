package com.aladingshop.card.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.aladingshop.card.model.MemberCardIncomeRecord;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

public interface MemberCardIncomeRecordService<T extends MemberCardIncomeRecord> extends
BaseService<T> {
	List<MemberCardIncomeRecord> findAll();
	/**
	 *            分页
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;
}
