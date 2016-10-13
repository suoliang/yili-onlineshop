package com.fushionbaby.sysactivity.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.sysactivity.condition.SysActivityFamilyQueryCondition;
import com.fushionbaby.sysactivity.model.SysActivityFamily;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SysActivityFamilyDao {

	void add(SysActivityFamily sysActivityFamily);

	void deleteById(Long id);

	void update(SysActivityFamily sysActivityFamily);

	SysActivityFamily findById(Long id);

	List<SysActivityFamily> findAll();

	List<SysActivityFamily> getListPage(Map<String, Object> map);

	Integer getTotal(Map<String, Object> map);

	/***
	 * 索亮 APP 亲子课程
	 */
	List<SysActivityFamily> getListPageTypeTwo(Map<String, Object> map);

	/**
	 * 通过条件查询亲子课程列表
	 * 
	 * @param queryCondition
	 * @return
	 */
	List<SysActivityFamily> queryByCondition(
			SysActivityFamilyQueryCondition queryCondition);
}