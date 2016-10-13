package com.aladingshop.store.auth.service;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.aladingshop.store.auth.model.StoreAuthPrivilege;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
/**
 * 
 * @author xupeijun
 *
 */
public interface StoreAuthPrivilegeService<T extends StoreAuthPrivilege> extends BaseService<T>{

	List<StoreAuthPrivilege> findAll();
	/**
	 * 通过父权限id得到 子权限集合
	 * @param parentId
	 * @return
	 */
    List<StoreAuthPrivilege >findByParentId(Long parentId);
    /**
     * 通过等级level查询权限集合
     * @param level
     * @return
     */
    List<StoreAuthPrivilege> findByLevel(Integer level);
    /**
     * 得到所有的第一级权限集合
     * @return
     */
	List<T> findAllFirst();

	/**
	 * 分页
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;

	/**
	 * 通过一级的id数组，得到所有（一级，二级）的权限id集合
	 * @param first
	 * @return
	 */
	List<Long> getallidsByFirsts(String[] first);
	
  /**
  * 通过用户的id得到用户的所有第一级的权限
  * @param userId
  * @return
  */
  // List<StoreAuthPrivilege> listParentPrivilegeByUserId(Long userId);
   
   List<StoreAuthPrivilege> findFirstByStoreCode(String storeCode);
	
}