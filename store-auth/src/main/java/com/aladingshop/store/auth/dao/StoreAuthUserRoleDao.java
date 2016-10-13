package com.aladingshop.store.auth.dao;

import java.util.List;

import com.aladingshop.store.auth.model.StoreAuthUserRole;

/**
 * 
 * @author xupeijun
 * 
 */
public interface StoreAuthUserRoleDao {

	void add(StoreAuthUserRole StoreAuthUserRole);

	void deleteById(Long id);

	void update(StoreAuthUserRole entity);

	StoreAuthUserRole findById(Long id);

	List<StoreAuthUserRole> findAll();

	List<StoreAuthUserRole> findByRoleId(Long roleId);

	/**
	 * 根据用户的id来删除 该用户的角色
	 * 
	 * @param id
	 */
	void deleteByUserId(Long id);

	/***
	 * 用户id获得
	 * 
	 * @param id
	 * @return
	 */
	List<StoreAuthUserRole> findByUserId(Long id);


}
