package com.fushionbaby.order.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.order.model.OrderBase;

public interface OrderBaseDao {
	
	void update(OrderBase orderBase);
	
	OrderBase findByMemberIdAndOrderCode(@Param("memberId")Long memberId,@Param("orderCode")String OrderCode);
	
	List<OrderBase> findListByOrderStatus(String orderStatus);
	
	OrderBase findByOrderCode(String orderCode);
	
	/**
	 * 分页查询订单基本信息
	 * @param map
	 * @return
	 */
	List<OrderBase> getListPage(Map<String, Object> map);
	
	/**
	 * 分页查询订单基本信息的总记录数
	 * @param map
	 * @return
	 */
	Integer getTotal(Map<String, Object> map);

	/**
	 * 交易完成订单和是否处理过积分的信息
	 * @param orderStatus
	 * @param isHandlePoint
	 * @return
	 */
	List<OrderBase> findListByStatusAndIsHandlePoint(@Param("orderStatus")String orderStatus,
			@Param("isHandlePoint")String isHandlePoint);
	
	/** 只在统计财务信息时使用*/
	List<OrderBase> getListStatisticsUse(HashMap<String , String> map);
	/**
	 * 分页查询订单基本信息
	 * @param map
	 * @return
	 */
	List<OrderBase> findListPageStatisticsUse(Map<String, Object> map);
	
	/**
	 * 分页查询订单基本信息的总记录数
	 * @param map
	 * @return
	 */
	Integer getTotalStatisticsUse(Map<String, Object> map);
	
	
	List<OrderBase>  findListByOrderStatusAndCurrent(String orderStatus)  ;
	
	void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);

	/***
	 * 查询后台登陆用户相关的订单
	 * @param map
	 * @return
	 */
	Integer getUserTotal(Map<String, Object> map);
	/***
	 * 查询后台登陆用户相关的订单
	 * @param map
	 * @return
	 */
	List<OrderBase> getUserListPage(Map<String, Object> map);

	/***
	 * 更新订单分派的订单状态
	 * @param orderBase
	 */
	void updateOrderDistributionWithdraw(OrderBase orderBase);

	Integer getStoreOrderTotal(Map<String, Object> map);

	List<OrderBase> getStoreOrderPageList(Map<String, Object> map);
}
