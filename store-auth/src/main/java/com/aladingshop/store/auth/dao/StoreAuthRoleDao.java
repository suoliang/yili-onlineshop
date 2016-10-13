package com.aladingshop.store.auth.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.store.auth.model.StoreAuthRole;
/***
 * 门店系统 角色
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月26日下午2:54:35
 */
public interface StoreAuthRoleDao{
	void add(StoreAuthRole auRole);
	void deleteById(long id);
	void update(StoreAuthRole auRole);
	List<StoreAuthRole> findAll(String storeCode);
	StoreAuthRole findById(long id);
	/**
	 * 分页查询
	 * 
	 * 
	 */
	List<StoreAuthRole> getListPage(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * 
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);
}