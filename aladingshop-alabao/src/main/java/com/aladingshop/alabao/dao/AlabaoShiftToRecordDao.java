package com.aladingshop.alabao.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.alabao.model.AlabaoShiftToRecord;

/**
 * @description 阿拉宝转入金额记录表
 * @author 索亮
 * @date 2015年9月8日下午3:01:05
 */
public interface AlabaoShiftToRecordDao {
	/**添加*/
	void add(AlabaoShiftToRecord alabaoShiftToRecord);

	/*根据memberId查询*/
	List<AlabaoShiftToRecord> findAllByMemberId(Long memberId);
	/** 根据account查询 **/
	List<AlabaoShiftToRecord> findAllByAccount(String account);
	
	List<AlabaoShiftToRecord> findAllByBatchNum(String batchNum);
	
	
	List<AlabaoShiftToRecord> getListPage(Map<String, Object> map);
	
	
	Integer getTotal(Map<String, Object> map);
	
	
	void deleteByAccountAndBatchNum(@Param("accountCode")String accountCode,@Param("batchNum") String batchNum) ;
	/**查询4个月内的转出交易记录*/
	List<AlabaoShiftToRecord> findBillRecordByTime(String account);
	
	
}
