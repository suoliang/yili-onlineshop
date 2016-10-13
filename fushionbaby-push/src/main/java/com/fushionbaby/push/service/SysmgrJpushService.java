package com.fushionbaby.push.service;

import java.util.List;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.push.model.SysmgrJpush;

/***
 *推送service
 * @author xupeijun
 *
 */
public interface SysmgrJpushService <T extends SysmgrJpush> extends BaseService<T> {

	List<SysmgrJpush> findAll();

	BasePagination getListPage(BasePagination page);
	
}
