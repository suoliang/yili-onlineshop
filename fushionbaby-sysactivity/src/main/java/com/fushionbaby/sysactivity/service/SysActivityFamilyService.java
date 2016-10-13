package com.fushionbaby.sysactivity.service;

import java.util.List;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysactivity.condition.SysActivityFamilyQueryCondition;
import com.fushionbaby.sysactivity.model.SysActivityFamily;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SysActivityFamilyService<T extends SysActivityFamily> extends
		BaseService<T> {
	List<SysActivityFamily> findAll();

	BasePagination getListPage(BasePagination page);
	List<SysActivityFamily> getListPageTypeTwo(int page_index, int page_size);

	/**
	 * 条件查询
	 * 
	 * @param queryCondition
	 * @return
	 */
	List<SysActivityFamily> queryByCondition(
			SysActivityFamilyQueryCondition queryCondition);

}
