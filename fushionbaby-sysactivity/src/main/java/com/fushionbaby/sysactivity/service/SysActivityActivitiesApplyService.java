package com.fushionbaby.sysactivity.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysactivity.model.SysActivityActivitiesApply;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SysActivityActivitiesApplyService<T extends SysActivityActivitiesApply>
		extends BaseService<T> {

	BasePagination getListPage(BasePagination page);
	
	List<SysActivityActivitiesApply> findAll();

	/**
	 * 修改是否联系状态
	 * 
	 * @param id
	 */
	void changeIsTouch(Long id);

	/**
	 * 修改是否确认状态
	 * 
	 * @param id
	 */
	void changeIsOk(Long id);
	
	public T query(Long memberId,Long activitiesId) throws DataAccessException;

}
