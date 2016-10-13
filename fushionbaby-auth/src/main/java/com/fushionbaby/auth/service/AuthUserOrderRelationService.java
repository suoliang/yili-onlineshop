package com.fushionbaby.auth.service;

import java.util.List;

import com.fushionbaby.auth.model.AuthUserOrderRelation;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
/***
 * 后台用户和订单关联
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月28日上午9:36:37
 */
public interface AuthUserOrderRelationService <T extends AuthUserOrderRelation> extends BaseService<T> {
	
	 List<AuthUserOrderRelation> findAll();

	 BasePagination getListPage(BasePagination page);
	 
	 List<AuthUserOrderRelation> findByUserIdAndName(Long userId,String userName);

	 /***
	  * 得到该用户的相关的订单编码的集合
	  * @param id
	  * @param loginName
	  * @return
	  */
	List<String> findOrderCodeListByUserId(Long id, String loginName);

	/***
	 * 删除关联记录
	 * @param id
	 * @param orderCode
	 */
	void deleteByOrderCodeAndUserId(Long id, String orderCode);

}