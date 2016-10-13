package com.aladingshop.alabao.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.alabao.model.AlabaoConsumeRecord;

public interface AlabaoConsumeRecordDao {
	
	public void add(AlabaoConsumeRecord alabaoConsumeRecord);
	
	public void updateByAccount(AlabaoConsumeRecord alabaoConsumeRecord);
	
	public void deleteById(Long id);
	
	public void deleteByAccount(String account);
	
	public AlabaoConsumeRecord findById(Long id);
	
	public AlabaoConsumeRecord findByAccount(String account);
	
	List<AlabaoConsumeRecord> getList(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
	

	/*根据memberId查询*/
	List<AlabaoConsumeRecord> findAllByMemberId(Long memberId);
	/** 根据account查询 **/
	List<AlabaoConsumeRecord> findAllByAccount(String account);
	
	public AlabaoConsumeRecord findByMemberIdAndOrderCode(@Param("memberId")Long memberId,@Param("orderCode")String orderCode);
	
	public void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);
	
	public AlabaoConsumeRecord findByOrderCode(@Param("orderCode")String orderCode);
	/**查询4个月内的转出交易记录*/
	public List<AlabaoConsumeRecord> findBillRecordByTime(String account);
	
	Map<String, Object> getListPageMoney(Map<String, Object> map);
	
}