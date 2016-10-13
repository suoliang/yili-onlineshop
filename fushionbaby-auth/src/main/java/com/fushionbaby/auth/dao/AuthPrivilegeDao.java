package com.fushionbaby.auth.dao;
import java.util.List;
import java.util.Map;

import com.fushionbaby.auth.model.AuthPrivilege;
/***
 * 
 * @author xupeijun
 *
 */

public interface AuthPrivilegeDao {

    void deleteById(Long  id);

    void add(AuthPrivilege auPrivilege);
   
    AuthPrivilege findById(Long id);
   
    List<AuthPrivilege> findAll();
  
     void update(AuthPrivilege auPrivilege);
 /**
  * 通过父权限id得到子权限集合
  * @param parentId
  * @return
  */
     List<AuthPrivilege> findByParentId(Long parentId);
  /**
   * 通过权限等级查找
   * @param level
   * @return
   */
     List<AuthPrivilege> findByLevel(Integer level);
    /**
     * 得到所有的第一级的权限
     * @return
     */
     List<AuthPrivilege> findAllFirst();
  

     /**
      * 分页
      * @param map
      * @return
      */
 	List<AuthPrivilege> getListPage(Map<String, Object> map);

 	/**
 	 * 分页
 	 * @param map
 	 * @return
 	 */
 	Integer getTotal(Map<String, Object> map);
     
 	  //得到用户的第一级权限
     List<AuthPrivilege> listParentPrivilegeByUserId(Long userId);
     
}