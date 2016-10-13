package com.fushionbaby.sms.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.sms.model.SmsTypeConfig;
/**
 * 
 * @author King
 *
 */
public interface SmsTypeConfigDao {
		
		void add(SmsTypeConfig smsType);
		
		void deleteById(Long id);
		
		void update(SmsTypeConfig smsType);
		
		SmsTypeConfig findById(Long id);
		
		List<SmsTypeConfig> findAll();

		int getTotal(Map<String, Object> searchParamsMap);

		List<SmsTypeConfig> getPageList(Map<String, Object> searchParamsMap);
		
}
