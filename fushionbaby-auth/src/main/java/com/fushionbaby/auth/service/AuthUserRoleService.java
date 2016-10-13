package com.fushionbaby.auth.service;

import java.util.List;

import com.fushionbaby.auth.model.AuthUserRole;
import com.fushionbaby.common.service.BaseService;

/**
 * 
 * @author xupeijun
 * 
 * @param <T>
 */
public interface AuthUserRoleService<T extends AuthUserRole> extends
		BaseService<T> {
	/***
	 * 通过角色id得到
	 * 
	 * @param id
	 * @return
	 */
	List<AuthUserRole> findByRoleId(Long id);
	
	List<AuthUserRole> findAll();
	/**
	 * 根据userId删除角色
	 * 
	 * @param id
	 */
	void deleteByUserId(Long id);

	/***
	 * 通过用户id得到
	 * 
	 * @param id
	 * @return
	 */
	List<AuthUserRole> findByUserId(Long id);

}
