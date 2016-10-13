package com.fushionbaby.sysactivity.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysactivity.model.SysActivityWill;

/**
 * 
 * @author cyla
 * 
 */
public interface SysActivityWillService<T extends SysActivityWill> extends
		BaseService<T> {

	public BasePagination getListPageOne(BasePagination page);
	List<SysActivityWill> findAll();
	/***
	 * 索亮 APP 风尚宝贝
	 * 
	 * @param map
	 * @return
	 */
	List<SysActivityWill> getListPage(int page_index, int page_size);


	public List<SysActivityWill> findWillShowList() throws DataAccessException;
}
