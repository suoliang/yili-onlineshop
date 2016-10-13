package com.fushionbaby.act.activity.service; 

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.act.activity.model.ActEntityCardUseRecord;

public interface ActEntityCardUseRecordService<T extends ActEntityCardUseRecord> { 
	
	
	void deleteById(Long id);

	void add(ActEntityCardUseRecord object);

	ActEntityCardUseRecord findById(Long id);

	List<ActEntityCardUseRecord> findAll();

	void update(ActEntityCardUseRecord object);
	
	BasePagination getListPage(BasePagination page) throws DataAccessException;

	/***
	 * 通过code查询消费记录
	 * @param entityCode
	 * @return
	 */
	List<ActEntityCardUseRecord> findByCode(String entityCode);

}
