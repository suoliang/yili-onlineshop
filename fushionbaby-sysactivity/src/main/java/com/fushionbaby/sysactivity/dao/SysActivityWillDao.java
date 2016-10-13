package com.fushionbaby.sysactivity.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.sysactivity.model.SysActivityWill;


/**
 * 
 * @author cyla
 * 
 */
public interface SysActivityWillDao {

	void add(SysActivityWill sysactivityWil);

	void deleteById(Long id);

	void update(SysActivityWill sysactivityWil);

	SysActivityWill findById(Long id);

	List<SysActivityWill> findAll();
	
	List<SysActivityWill> findWillShowList();

	Integer getTotal(Map<String, Object> searchParamsMap);

	List<SysActivityWill> getListPage(Map<String, Object> searchParamsMap);
}
