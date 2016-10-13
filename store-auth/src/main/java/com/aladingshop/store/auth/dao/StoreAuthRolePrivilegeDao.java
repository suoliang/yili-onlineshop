package com.aladingshop.store.auth.dao;

import java.util.List;

import com.aladingshop.store.auth.model.StoreAuthRolePrivilege;

/***
 * 门店系统  角色权限关联
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月26日下午3:03:21
 */
public interface StoreAuthRolePrivilegeDao {
        void add(StoreAuthRolePrivilege StoreAuthRolePrivilege);
        void deleteById(Long id);
    	void update(StoreAuthRolePrivilege entity);
     	List<StoreAuthRolePrivilege> findAll();
		StoreAuthRolePrivilege findById(Long id);
         /***
          * 通过角色id得到该  角色权限对象
          * @param roleId
          */
		List<StoreAuthRolePrivilege> findByRoleId(Long roleId);
		/**
		 * 通过角色id删除，该关联表中的数据
		 * @param id
		 */
		void deleteByRoleId(Long id);
	
         
}
