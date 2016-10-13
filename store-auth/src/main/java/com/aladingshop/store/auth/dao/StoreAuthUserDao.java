package com.aladingshop.store.auth.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.store.auth.model.StoreAuthRole;
import com.aladingshop.store.auth.model.StoreAuthUser;


/***
 * 门店系统  后台用户
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月26日下午3:03:45
 */
public interface StoreAuthUserDao {
    
    void add(StoreAuthUser auUser);
    void deleteById(Long id);
    void update(StoreAuthUser auUser);
    StoreAuthUser findById(Long id);
    List<StoreAuthUser> findAll();
 	void updateIsDisabled(StoreAuthUser auUser);
    
    /***
     * 判断用户是不是系统用户
     * @param id
     * @return
     */
    StoreAuthUser isSystemUser(Long  id);
    /***
     * 得到用户的角色,通过用户id
     * @param userId
     * @return
     */
    List<StoreAuthRole> listAuRoleByUserId(Long userId);
    /***
     * 通过用户名/门店号查找用户
     * @param userName
     * @return
     */
    StoreAuthUser findStoreAuthUserByUserNameAndStoreNumber(@Param("name") String userName,@Param("storeNumber") String storeNumber);
    /***
     * 通过用户名/门店编码查找用户
     * @param userName
     * @return
     */
    StoreAuthUser findStoreAuthUserByUserNameAndStoreCode(@Param("name") String userName,@Param("storeCode") String storeCode);
    
    /**
	 * 分页查询
	 */
	List<StoreAuthUser> getListPage(Map<String, Object> map);
	/**
	 * 分页查询的总记录数
	 * 
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);

	StoreAuthUser findSysUserByStoreCode(String storeCode);
    
}