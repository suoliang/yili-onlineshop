package com.fushionbaby.sysactivity.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.sysactivity.model.SysActivityActivities;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SysActivityActivitiesDao {

	void add(SysActivityActivities sysActivityActivities);

	void deleteById(Long id);

	void update(SysActivityActivities sysActivityActivities);

	SysActivityActivities findById(Long id);

	List<SysActivityActivities> findAll();

	/**
	 * 分页查询
	 * 
	 * @author suoliang
	 */
	List<SysActivityActivities> getListPage(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * 
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);

	/**
	 * 手机APP 户外活动列表
	 * 
	 * @author 索亮
	 */
	List<SysActivityActivities> getListPageType(Map<String, Object> map);

	/***
	 * 改变活动是否开启的状态
	 * 
	 * @param map
	 */
	void changeIsOpen(Map<String, String> map);

}