package com.aladingshop.alabao.service;

import java.util.List;

import com.aladingshop.alabao.model.AlabaoIncomeRecord;
import com.fushionbaby.common.util.BasePagination;

/**
 * @description 阿拉宝收益记录表
 * @author 索亮
 * @date 2015年9月8日下午2:55:04
 */
public interface AlabaoIncomeRecordService<T extends AlabaoIncomeRecord> {
	/**添加*/
	void add(AlabaoIncomeRecord alabaoIncomeRecord);

	/*根据memberId查询*/
	List<AlabaoIncomeRecord> findAllByMemberId(Long memberId);
	/** 根据account查询 **/
	List<AlabaoIncomeRecord> findAllByAccount(String account);
	
	BasePagination  getListPage(BasePagination page);
	
	/**
	 * 分页查询
	 * @param account
	 * @param start
	 * @param limit
	 * @return
	 */
	List<AlabaoIncomeRecord> getListPageParam(String account, Integer start,Integer limit);
	/**查询4个月内的转出交易记录*/
	List<AlabaoIncomeRecord> findBillRecordByTime(String account);
	
	String getListPageMoney(BasePagination page);
}
