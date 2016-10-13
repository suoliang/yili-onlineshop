package com.fushionbaby.core.service;

import java.util.List;
import java.util.Map;

import com.fushionbaby.core.model.OrderBaseUser;

public interface OrderBaseUserService<T extends OrderBaseUser> {

	void addOrderBase(OrderBaseUser orderBaseUser);
	/** 修改订单状态 */
	void updateOrderStatus(OrderBaseUser orderBaseUser);
	/** 分页查询订单基本信息 */
	List<OrderBaseUser> getListPage(Map<String,Object> map);
	/** 分页查询订单基本信息的总记录数 */
	Integer getTotal(Map<String, Object> map);
	/** 查询会员等待付款状态下的集合 */
	List<OrderBaseUser> getWaitingListByMemberId(Long memerId);
	/** 根据会员id和订单code查询出对象 */
	OrderBaseUser findObjectByMemIdAndOrdCode(Long memberId, String orderCode);
}
