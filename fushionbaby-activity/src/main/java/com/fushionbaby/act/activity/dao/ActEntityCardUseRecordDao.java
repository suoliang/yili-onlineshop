package com.fushionbaby.act.activity.dao; 

import java.util.List;
import java.util.Map;

import com.fushionbaby.act.activity.model.ActEntityCardUseRecord;

public interface ActEntityCardUseRecordDao { 


	void deleteById(Long id);

	void add(ActEntityCardUseRecord object);

	ActEntityCardUseRecord findById(Long id);

	List<ActEntityCardUseRecord> findAll();

	void update(ActEntityCardUseRecord object);
	
	List<ActEntityCardUseRecord> getListPage(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);

	/***
	 * 通过code查询使用记录
	 * @param entityCode
	 * @return
	 */
	List<ActEntityCardUseRecord> findByCode(String entityCode);

}
