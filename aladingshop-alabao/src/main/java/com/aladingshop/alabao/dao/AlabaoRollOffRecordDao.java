package com.aladingshop.alabao.dao; 

import java.util.List;
import java.util.Map;

import com.aladingshop.alabao.model.AlabaoRollOffRecord;

public interface AlabaoRollOffRecordDao { 


	void deleteById(Long id);

	void add(AlabaoRollOffRecord object);

	AlabaoRollOffRecord findById(Long id);

	List<AlabaoRollOffRecord> findAll();

	void update(AlabaoRollOffRecord object);
	
	List<AlabaoRollOffRecord> getListPage(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);

	List<AlabaoRollOffRecord> findByMemberIdTime(Map<String, Object> map);

	List<AlabaoRollOffRecord> findAllByMemberId(Long memberId);

	List<AlabaoRollOffRecord> findAllByAccount(String account);
	
	List<AlabaoRollOffRecord>  findByBatchNum(String batchNum);
	/**查询4个月内的转出交易记录*/
	List<AlabaoRollOffRecord> findBillRecordByTime(String account);
	
	/**
	 * 通过交易的序列号查询
	 * @param serialNum
	 * @return
	 */
	AlabaoRollOffRecord findBySerialNum(String serialNum);
	
	
}
