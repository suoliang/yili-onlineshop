package com.fushionbaby.cache.service;


/**
 * 
 * @author mengshaobo
 * @version 2015-7-2
 */
public interface GlobalConfig {
	/**
	 * 通过编号查询全局配置
	 * @param code 编号
	 * @return
	 */
	String findByCode(String code);
	
}
