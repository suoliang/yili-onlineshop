package com.aladingshop.store.auth.service;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.aladingshop.store.auth.model.StoreAuthRole;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author xupeijun
 *
 */
public interface StoreAuthRoleService <T extends StoreAuthRole> extends BaseService<T>{

 /***
  * 给角色添加权限
  * @param roleId
  * @param privilegeId
  */
	List<StoreAuthRole> findAll(String storeCode);
	/**
	 * @param page
	 *            分页
	 * @return 结果
	 * @throws DataAccessException
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;

}