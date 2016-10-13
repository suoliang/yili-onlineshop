package com.fushionbaby.auth.service;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.auth.model.AuthRole;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author xu
 *
 */
public interface AuthRoleService <T extends AuthRole> extends BaseService<T>{

 /***
  * 给角色添加权限
  * @param roleId
  * @param privilegeId
  */
	/* void addPrivilegeToRole(Long roleId,Long privilegeId); */
	List<AuthRole> findAll();
    
    //通过名称获得角色
    AuthRole findByName(String name);
	/**
	 * @author suoliang
	 * @param page
	 *            分页
	 * @return 结果
	 * @throws DataAccessException
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;

}