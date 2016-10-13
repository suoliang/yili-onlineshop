package com.fushionbaby.sysactivity.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysactivity.model.SysActivityActivities;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SysActivityActivitiesService<T extends SysActivityActivities>
		extends BaseService<T> {

	BasePagination getListPage(BasePagination page);
	List<SysActivityActivities> findAll();
	/**
	 * APP 户外活动列表 索亮
	 */
	List<SysActivityActivities> getListPageType(int page_index, int page_size)
			throws DataAccessException;

	/***
	 * 改变活动是否开启
	 * 
	 * @param id
	 */
	void changeIsOpen(Long id);

	/***
	 * 活动的添加,含有编辑器中的保存
	 * 
	 * @param sysActivityActivities
	 * @param myEditorContent
	 */
	void add(SysActivityActivities sysActivityActivities, String myEditorContent);

	/***
	 * 活动的修改
	 * 
	 * @param myEditorContent
	 * @param sysActivityActivities
	 */
	void updateActivity(String myEditorContent,
			SysActivityActivities sysActivityActivities);

}
