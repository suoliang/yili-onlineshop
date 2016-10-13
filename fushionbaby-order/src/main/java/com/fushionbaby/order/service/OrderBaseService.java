package com.fushionbaby.order.service;

import java.util.HashMap;
import java.util.List;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.order.model.OrderBase;

public interface OrderBaseService<T extends OrderBase> {
	
	void update(OrderBase orderBase);
	
	OrderBase findByMemberIdAndOrderCode(Long memberId,String orderCode);
	
	List<OrderBase> findListByOrderStatus(String orderStatus);
	
	OrderBase findByOrderCode(String orderCode);
	/** 交易完成订单和是否处理过积分的信息 */
	List<OrderBase> findListByStatusAndIsHandlePoint(String orderStatus,String isHandlePoint);
	/** 分页查询订单基本表信息 */
	BasePagination findOrderBaseListByPage(BasePagination pageParams);
	/** 只在统计财务信息时使用*/
	List<OrderBase> getListStatisticsUse(HashMap<String , String> map);
	/** 只在统计财务信息时使用 */
	BasePagination findListPageStatisticsUse(BasePagination pageParams);
	
	/**
	 * 查询昨天到现在的订单状态的订单信息
	 * @param orderStatus
	 * @return
	 */
	List<OrderBase>  findListByOrderStatusAndCurrent(String orderStatus);
	
	void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);

	/***
	 * 查询跟登陆用户有关的订单
	 * @param page
	 * @param orderCodeList 
	 * @return
	 */
	BasePagination findUserOrderBaseListByPage(BasePagination page, List<String> orderCodeList);
	
	
	void updateOrderDistributionWithdraw(OrderBase orderBase);

	BasePagination findStoreOrderPageList(BasePagination page);
	
	
	
}
