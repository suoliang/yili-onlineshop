package com.fushionbaby.push.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.push.model.SysmgrJpush;

/***
 * 推送dao
 * @author xupeijun
 *
 */
public interface SysmgrJpushDao {
	void add(SysmgrJpush jpushModel);
	
	void deleteById(Long id);
	
	void update(SysmgrJpush jpushModel);
	
	SysmgrJpush findById(Long id);
	
	List<SysmgrJpush> findAll();

	Integer getTotal(Map<String, Object> searchParamsMap);

	List<SysmgrJpush> getListPage(Map<String, Object> searchParamsMap);
}
