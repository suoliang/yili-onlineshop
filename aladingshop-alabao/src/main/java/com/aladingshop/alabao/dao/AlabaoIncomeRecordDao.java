package com.aladingshop.alabao.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.alabao.model.AlabaoIncomeRecord;

/**
 * @description 阿拉宝收益记录表DAO
 * @author 索亮
 * @date 2015年9月8日下午2:52:31
 */
public interface AlabaoIncomeRecordDao {
	/**添加*/
	void add(AlabaoIncomeRecord alabaoIncomeRecord);
	/*根据memberId查询*/
	List<AlabaoIncomeRecord> findAllByMemberId(Long memberId);
	/** 根据account查询 **/
	List<AlabaoIncomeRecord> findAllByAccount(String account);
	
	List<AlabaoIncomeRecord> getListPage(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
	/**查询4个月内的转出交易记录*/
	List<AlabaoIncomeRecord> findBillRecordByTime(String account);
	
	Map<String, Object> getListPageMoney(Map<String, Object> map);
	
}
