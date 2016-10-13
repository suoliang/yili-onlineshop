package com.aladingshop.store.auth.service;

import java.util.List;
import java.util.Map;

import com.aladingshop.store.auth.model.StoreAuthPrivilege;
import com.aladingshop.store.auth.model.StoreAuthUser;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
/**
 * 
 * @author xupeijun
 *
 */
public interface StoreAuthUserService <T extends StoreAuthUser> extends BaseService<T> {
	
	 List<StoreAuthUser> findAll();

    /**
     * 修改用户状态
     * 索亮
     */
	void updateIsDisabled(Long id,String isDisabled);
	
	/**
	 * 通过用户名得到用户（做登陆判断）
	 * @param userName
	 * @return
	 */
    public StoreAuthUser findStoreAuthUserByUserNameAndStoreNumber(String userName,String storeNumber);

    /**
	 * 通过用户名得到用户
	 * @param userName
	 * @return
	 */
    public StoreAuthUser findStoreAuthUserByUserNameAndStoreCode(String userName,String storeCode);

	/**
	 * 判断用户是不是系统用户
	 * @param id
	 * @return
	 */
	StoreAuthUser isSystemUser(Long id);
	/**
	 * 分页
	 * @param page
	 * @return
	 */
	BasePagination getListPage(BasePagination page);
		/**
		 * 系统用户登录的时候，罗列所有的权限（一级，二级）
		 * 每一个第一级的权限名 和其下的第二级的权限列表放到map集合中
		 * 该方法得到的是所有的map集合
		 * 
		 * @return
		 */
	List<Map<StoreAuthPrivilege, List<StoreAuthPrivilege>>> loginAsSystem();
/**
 * 普通用户登录的时候，列出所有的关于该用户的权限
 * 
 * 如果该用户拥有某个第一级权限，那他拥有该第一级权限下的所有二级权限
 * @param userId
 * @return
 */
	List<Map<StoreAuthPrivilege, List<StoreAuthPrivilege>>> loginAsNormalUser(Long userId);

	/** 判断用户是否唯一 */
	boolean isUniqueByUserNameAndStoreCode(String name,String storeCode,long id);

	StoreAuthUser findSysUserByStoreCode(String storeCode);
}