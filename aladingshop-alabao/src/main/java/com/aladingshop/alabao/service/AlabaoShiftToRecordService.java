package com.aladingshop.alabao.service;

import java.util.List;

import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.fushionbaby.common.util.BasePagination;

/**
 * @description 阿拉宝转入金额记录表
 * @author 索亮
 * @date 2015年9月8日下午3:01:52
 */
public interface AlabaoShiftToRecordService<T extends AlabaoShiftToRecord> {
	/**添加*/
	void add(AlabaoShiftToRecord alabaoShiftToRecord);

	/*根据memberId查询*/
	List<AlabaoShiftToRecord> findAllByMemberId(Long memberId);
	/** 根据account查询 **/
	List<AlabaoShiftToRecord> findAllByAccount(String account);
	
	List<AlabaoShiftToRecord> findAllByBatchNum(String batchNum);
	
	
	BasePagination getListPage(BasePagination params);
	
	
	void deleteByAccountAndBatchNum(String accountCode,String batchNum);
	
	/**
	 * 分页查询
	 * @param account
	 * @param start
	 * @param limit
	 * @return
	 */
	List<AlabaoShiftToRecord> getListPageParam(String account,Integer start,Integer limit);
	
	/**查询4个月内的转出交易记录*/
	List<AlabaoShiftToRecord> findBillRecordByTime(String account);
	
	void insertBatch(List<AlabaoShiftToRecord> list);
	
}
