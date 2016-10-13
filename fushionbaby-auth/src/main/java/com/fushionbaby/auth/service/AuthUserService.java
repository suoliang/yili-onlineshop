package com.fushionbaby.auth.service;

import java.util.List;
import java.util.Map;

import com.fushionbaby.auth.model.AuthPrivilege;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
/**
 * 
 * @author xupeijun
 *
 */
public interface AuthUserService <T extends AuthUser> extends BaseService<T> {
	
	 List<AuthUser> findAll();

    /**
     * 修改用户状态
     * 索亮
     */
	void updateStatus(Long id,Integer status);
	
	/**
	 * 通过用户名得到用户（做登陆判断）
	 * @param userName
	 * @return
	 */
    public AuthUser findAuthUserByUserName(String userName);


	/**
	 * 判断用户是不是系统用户
	 * @param id
	 * @return
	 */
	AuthUser isSystemUser(Long id);
	/**
	 * 分页
	 * @param page
	 * @return
	 */
	BasePagination getListPage(BasePagination page);
		/**
		 * 系统用户登录的时候，罗列所有的权限（一级，二级）
		 * 
		 * @return
		 */
	List<Map<AuthPrivilege, List<AuthPrivilege>>> loginAsSystem();
	/**
	 * 普通用户登录的时候，列出所有的关于该用户的权限
	 * @param userId
	 * @return
	 */
	List<Map<AuthPrivilege, List<AuthPrivilege>>> loginAsNormalUser(Long userId);

	/** 判断用户是否唯一 */
	boolean isUniqueByUserName(String name,long id);

	/***
	 * 查询可可分单的后台登陆人员列表
	 * @param levelTwo
	 * @return
	 */
	List<AuthUser> findDistributionUser(Integer levelTwo);

    
}