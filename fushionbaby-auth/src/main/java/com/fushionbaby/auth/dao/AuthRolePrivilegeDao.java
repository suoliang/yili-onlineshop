package com.fushionbaby.auth.dao;

import java.util.List;

import com.fushionbaby.auth.model.AuthRolePrivilege;

public interface AuthRolePrivilegeDao {
        void add(AuthRolePrivilege authRolePrivilege);
        void deleteById(Long id);
     	List<AuthRolePrivilege> findAll();
		void update(AuthRolePrivilege entity);
		AuthRolePrivilege findById(Long id);
         /***
          * 通过角色id得到该  角色权限对象
          * @param roleId
          */
		List<AuthRolePrivilege> findByRoleId(Long roleId);
		/**
		 * 通过角色id删除，该关联表中的数据
		 * @param id
		 */
		void deleteByRoleId(Long id);
		
		void deleteByPrivilegeId(Long id);
	
         
}
