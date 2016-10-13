package com.fushionbaby.sysactivity.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.sysactivity.model.SysActivityActivitiesApply;

/**
 * 
 * @author King
 * 
 */
public interface SysActivityActivitiesApplyDao {

	void add(SysActivityActivitiesApply sysActivitiesApply);

	void deleteById(Long id);

	void update(SysActivityActivitiesApply sysActivitiesApply);

	/**
	 * 改变状态isOk
	 * 
	 * @param id
	 * @return
	 */

	void changeIsOk(Map<String, String> map);

	/**
	 * 改变是否联系
	 * 
	 * @param id
	 * @return
	 */
	void changeIsTouch(Map<String, String> map);

	SysActivityActivitiesApply findById(Long id);
	
	SysActivityActivitiesApply query(@Param(value = "memberId") long memberId,@Param(value = "activitiesId") long activitiesId);

	List<SysActivityActivitiesApply> findAll();

	/**
	 * 分页查询
	 * 
	 * @author
	 */
	List<SysActivityActivitiesApply> getListPage(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * 
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);

}