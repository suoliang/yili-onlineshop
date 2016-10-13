package com.aladingshop.store.auth.dao;
import java.util.List;
import java.util.Map;

import com.aladingshop.store.auth.model.StoreAuthPrivilege;
/***
 * 门店系统 后台权限
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月26日下午2:44:50
 */
public interface StoreAuthPrivilegeDao {
    void add(StoreAuthPrivilege auPrivilege);
    void deleteById(Long  id);
    void update(StoreAuthPrivilege auPrivilege);
    StoreAuthPrivilege findById(Long id);
    List<StoreAuthPrivilege> findAll();
    
    /**
     * 得到所有的第一级的权限
     * @return
     */
     List<StoreAuthPrivilege> findAllFirst();
	 /**
	  * 通过父权限id得到子权限集合
	  * @param parentId
	  * @return
	  */
     List<StoreAuthPrivilege> findByParentId(Long parentId);
	  /**
	   * 通过权限等级查找
	   * @param level
	   * @return
	   */
     List<StoreAuthPrivilege> findByLevel(Integer level);

     /**
      * 分页
      * @param map
      * @return
      */
 	List<StoreAuthPrivilege> getListPage(Map<String, Object> map);

 	/**
 	 * 分页
 	 * @param map
 	 * @return
 	 */
 	Integer getTotal(Map<String, Object> map);

 	 //得到用户的第一级权限
//    List<StoreAuthPrivilege> listParentPrivilegeByUserId(Long userId);
    
	List<StoreAuthPrivilege> findFirstByStoreCode(String storeCode);
     
}