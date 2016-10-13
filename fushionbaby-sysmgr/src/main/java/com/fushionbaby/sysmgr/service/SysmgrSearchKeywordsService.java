package com.fushionbaby.sysmgr.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.sysmgr.model.SysmgrSearchKeywords;

/**
 * 
 * @author King
 * 
 */
public interface SysmgrSearchKeywordsService<T extends SysmgrSearchKeywords>
		extends BaseService<T> {
	/**
	 * 通过关键字查询搜索关键字
	 * 
	 * @param searchKey
	 * @return
	 */
	SysmgrSearchKeywords queryBySearchKey(String searchKey, String source);

	/**
	 * 添加关键字
	 * 
	 * @param searchKey
	 *            关键字
	 * @param source
	 *            来源
	 * @param ip
	 *            IP地址
	 */
	void insertSearchKey(String searchKey, String source, String ip);
	
	void addBatch(List<SysmgrSearchKeywords>  list)  throws Exception ;

}
