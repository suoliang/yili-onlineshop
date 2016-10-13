/**
 * 
 */
package com.fushionbaby.log.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.log.model.LogWxCardVoucher;

/**
 * @author mengshaobo
 *
 */
public interface LogWxCardVoucherDao {
	 void add(LogWxCardVoucher cardVoucher);
	 
	 
	 List<LogWxCardVoucher> getPageList(Map<String, Object> params);
	 
	 Integer getTotal(Map<String, Object> params);
}
