package com.fushionbaby.auth.service;
import java.util.List;

import com.fushionbaby.auth.model.AuthRolePrivilege;
import com.fushionbaby.common.service.BaseService;
/**
 * 
 * @author xupeijun
 *
 * @param <T>
 */
public interface AuthRolePrivilegeService<T extends AuthRolePrivilege> extends BaseService<T>{
	
	List<AuthRolePrivilege> findAll();
	/***
	 * 通过角色id得到该角色的权限id集合
	 * @param roleId
	 * @return
	 */
	List<Long> findByRoleId(Long roleId);

		/**
		 * 通过roleid 删除角色权限表里面的相关数据
		 * @param id
		 */
		void deleteByRoleId(Long id);
	/**
	 * 通过传入的roleId，和所有权限的id集合，修改角色
	 * @param id
	 * @param allid
	 */
		void updateRole(Long id, List<Long> allid);
		
	void deleteByPrivilegeId(String id);
	
	
	

}
