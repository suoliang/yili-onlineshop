package com.fushionbaby.sms.service;

import java.util.List;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sms.model.SmsTypeConfig;

/**
 * 
 * @author King
 *
 */
public interface SmsTypeConfigService<T extends SmsTypeConfig> extends BaseService<T> {

	BasePagination findBySmsName(BasePagination page);
	List<SmsTypeConfig> findAll();
}
