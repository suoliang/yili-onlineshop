package com.fushionbaby.auth.dao;

import java.util.List;

import com.fushionbaby.auth.model.AuthUserRole;

/**
 * 
 * @author xupeijun
 * 
 */
public interface AuthUserRoleDao {

	void add(AuthUserRole authUserRole);

	void deleteById(Long id);

	void update(AuthUserRole entity);

	AuthUserRole findById(Long id);

	List<AuthUserRole> findAll();

	List<AuthUserRole> findByRoleId(Long roleId);

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
	List<AuthUserRole> findByUserId(Long id);


}
