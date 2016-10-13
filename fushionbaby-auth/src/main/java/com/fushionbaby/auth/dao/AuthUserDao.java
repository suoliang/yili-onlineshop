package com.fushionbaby.auth.dao;
import java.util.List;
import java.util.Map;

import com.fushionbaby.auth.model.AuthRole;
import com.fushionbaby.auth.model.AuthUser;



public interface AuthUserDao {
    void deleteById(Long id);
    void add(AuthUser auUser);
    AuthUser findById(Long id);
    void update(AuthUser auUser);
 	void updateStatus(AuthUser auUser);
    List<AuthUser> findAll();
    /***
     * 判断用户是不是系统用户
     * @param id
     * @return
     */
    AuthUser isSystemUser(Long  id);
    /***
     * 得到用户的角色,通过用户id
     * @param userId
     * @return
     */
    List<AuthRole> listAuRoleByUserId(Long userId);
    /***
     * 通过用户名查找用户
     * @param userName
     * @return
     */
    AuthUser findAuthUserByUserName(String userName);
    /**
	 * 分页查询
	 */
	List<AuthUser> getListPage(Map<String, Object> map);
	/**
	 * 分页查询的总记录数
	 * 
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);
	/***
	 * 查询可分单的后台人员的列表
	 * @param level
	 * @return
	 */
	List<AuthUser> findDistributionUser(Integer level);
    
}