package com.aladingshop.alabao.service; 

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.aladingshop.alabao.model.AlabaoRollOffRecord;
import com.fushionbaby.common.util.BasePagination;

public interface AlabaoRollOffRecordService<T extends AlabaoRollOffRecord> { 
	
	
	void deleteById(Long id);

	void add(AlabaoRollOffRecord object);

	AlabaoRollOffRecord findById(Long id);

	List<AlabaoRollOffRecord> findAll();

	void update(AlabaoRollOffRecord object);
	
	BasePagination getListPage(BasePagination page) throws DataAccessException;

	List<AlabaoRollOffRecord> findByMemberIdTime(Long memberId ,Date createTime);
	
	List<AlabaoRollOffRecord> findAllByMemberId(Long memberId);
	
	List<AlabaoRollOffRecord> findAllByAccount(String account);
	
	List<AlabaoRollOffRecord>  findByBatchNum(String batchNum);

	/**
	 * 分页查询
	 * @param account
	 * @param start
	 * @param limit
	 * @return
	 */
	List<AlabaoRollOffRecord> getListPageParam(String account, Integer start,Integer limit);
	/**查询4个月内的转出交易记录*/
	List<AlabaoRollOffRecord> findBillRecordByTime(String account);
	
	/**
	 * 通过交易的序列号查询
	 * @param serialNum
	 * @return
	 */
	AlabaoRollOffRecord findBySerialNum(String serialNum);
}
