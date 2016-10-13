package com.fushionbaby.sms.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.sms.model.Sms;
/**
 * 
 * @author King
 *
 */
public interface SmsDao {
		
		void add(Sms sms);
		
		void deleteById(Long id);
		
		void update(Sms sms);
		
		Sms findById(Long id);
		
		List<Sms> findAll();
		
		/**
		 * 分页查询
		 * @author suoliang
		 */
		List<Sms> getListPage(Map<String, Object> map);

		/**
		 * 分页查询的总记录数
		 * 
		 * @param map
		 * @return 总记录数
		 */
		Integer getTotal(Map<String, Object> map);

		long count(Map<String, Object> map);
		
		/***
		 * 查询用户最近10分钟短信发送量
		 * @param phone
		 * @return
		 */
		List<Sms> findNumByLastTime(String phone);
		
}
