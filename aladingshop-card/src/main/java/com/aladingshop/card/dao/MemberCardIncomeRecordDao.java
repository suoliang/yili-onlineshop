package com.aladingshop.card.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.card.model.MemberCardIncomeRecord;

public interface MemberCardIncomeRecordDao {

	void deleteById(Long id);

	void add(MemberCardIncomeRecord record);

	MemberCardIncomeRecord findById(Long id);

	List<MemberCardIncomeRecord> findAll();

	/**
	 * 分页查询
	 * 
	 */
	List<MemberCardIncomeRecord> getListPage(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * 
	 */
	Integer getTotal(Map<String, Object> map);
}