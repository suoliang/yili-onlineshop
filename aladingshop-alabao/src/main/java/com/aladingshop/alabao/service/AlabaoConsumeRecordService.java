package com.aladingshop.alabao.service;


import java.util.List;

import com.aladingshop.alabao.model.AlabaoConsumeRecord;
import com.fushionbaby.common.util.BasePagination;

public interface AlabaoConsumeRecordService <T extends AlabaoConsumeRecord>{
	
	public void add(AlabaoConsumeRecord alabaoAccount);
	
	public void updateByAccount(AlabaoConsumeRecord alabaoAccount);
	
	public void deleteById(Long id);
	
	public void deleteByAccount(String account);
	
	public AlabaoConsumeRecord findById(Long id);
	
	public AlabaoConsumeRecord findByAccount(String account);
	
	BasePagination getListPage(BasePagination pageParams);

	/*根据memberId查询*/
	List<AlabaoConsumeRecord> findAllByMemberId(Long memberId);
	/** 根据account查询 **/
	List<AlabaoConsumeRecord> findAllByAccount(String account);
	
	public AlabaoConsumeRecord findByMemberIdAndOrderCode(Long memberId,String orderCode);
	
	public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);
	
	public AlabaoConsumeRecord findByOrderCode(String orderCode);

	/***
	 * 分页查询
	 * @param account
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<AlabaoConsumeRecord> getListPageParam(String account,Integer start, Integer limit);
	/**查询4个月内的转出交易记录*/
	public List<AlabaoConsumeRecord> findBillRecordByTime(String account);
	
	String getListPageMoney(BasePagination page);
}
