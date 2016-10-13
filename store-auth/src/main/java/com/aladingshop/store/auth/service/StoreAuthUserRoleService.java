package com.aladingshop.store.auth.service;

import java.util.List;

import com.aladingshop.store.auth.model.StoreAuthUserRole;
import com.fushionbaby.common.service.BaseService;

/**
 * 
 * @author xupeijun
 * 
 * @param <T>
 */
public interface StoreAuthUserRoleService<T extends StoreAuthUserRole> extends BaseService<T> {
	/***
	 * 通过角色id得到
	 * 
	 * @param id
	 * @return
	 */
	List<StoreAuthUserRole> findByRoleId(Long id);
	
	List<StoreAuthUserRole> findAll();
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
	List<StoreAuthUserRole> findByUserId(Long id);

}
