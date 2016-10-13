package com.fushionbaby.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.order.model.OrderTrans;


public interface OrderTransDao {
	/** 用于测试 */
	void add(OrderTrans orderTrans);
	
    void updateByMemberIdAndOrderCode(OrderTrans orderTrans);

    OrderTrans findByOrderCode(String orderCode);

    OrderTrans findByOrderTransId(Long id);

    OrderTrans findByMemberIdAndOrderCode(@Param("memberId")Long memberId,@Param("orderCode")String orderCode);
	
    List<OrderTrans> findListByTransStatus(String transStatus);

    void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);
}