package com.fushionbaby.member.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.member.model.VerificationRecord;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年12月7日下午3:50:59
 */
public interface VerificationRecordDao{

	void add(VerificationRecord record);

	void deleteById(Long id);

	void update(VerificationRecord record);
	
	VerificationRecord findById(Long id);
	
	List<VerificationRecord> findAll();
	/***
	 * 通过卡号和身份证号 得到验证记录
	 * @param map
	 * @return
	 */
	List<VerificationRecord> findByBankCardNoAndID(Map<String, Object> map);

	Integer getTotal(Map<String, Object> searchParamsMap);

	List<VerificationRecord> getListPage(Map<String, Object> searchParamsMap);
	
}