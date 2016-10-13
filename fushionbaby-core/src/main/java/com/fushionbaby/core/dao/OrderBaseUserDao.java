package com.fushionbaby.core.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.core.model.OrderBaseUser;

public interface OrderBaseUserDao {

	void addOrderBase(OrderBaseUser orderBaseUser);

	List<OrderBaseUser> getWaitingListByMemberId(Long memberId);

	void updateOrderStatus(OrderBaseUser orderBaseUser);

	Integer calcCountByParam(Map<String, Object> param);
	
	/**
	 * 分页查询订单基本信息
	 * @param map
	 * @return
	 */
	List<OrderBaseUser> getListPage(Map<String, Object> map);
	
	/**
	 * 分页查询订单基本信息的总记录数
	 * @param map
	 * @return
	 */
	Integer getTotal(Map<String, Object> map);

	/** 通过memberId和订单号查询对象 */
	OrderBaseUser findObjectByMemIdAndOrdCode(Map<String, Object> map);
}
