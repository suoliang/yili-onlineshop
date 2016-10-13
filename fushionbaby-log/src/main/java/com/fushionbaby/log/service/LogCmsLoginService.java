package com.fushionbaby.log.service;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.log.model.LogCmsLogin;

/**
 * 
 * @author King suoliang
 * 
 * @param <T>
 */
public interface LogCmsLoginService<T extends LogCmsLogin> extends
		BaseService<T> {

	void add(LogCmsLogin logCmsLogin);

	void deleteById(Long id);

	/***
	 * 登记到日志里面
	 * 
	 * @param auUser
	 * @param status
	 */
	void addLoginMessage(LogCmsLogin cms);

	/**
	 * 分页
	 * @param page
	 * @return
	 */
	BasePagination getListPage(BasePagination page);
	
}
