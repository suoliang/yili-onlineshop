package com.fushionbaby.log.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.log.model.LogCmsLogin;

/**
 * 
 * @author King suoliang
 * 
 */
public interface LogCmsLoginDao {

	void add(LogCmsLogin logCmsLogin);

	void deleteById(Long id);

	List<LogCmsLogin> getListPage(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
}