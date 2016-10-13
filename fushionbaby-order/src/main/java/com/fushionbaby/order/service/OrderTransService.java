package com.fushionbaby.order.service;
import java.util.List;

import com.fushionbaby.order.model.OrderTrans;

public interface OrderTransService <T extends OrderTrans>{
	/** 用于测试 */
	void add(OrderTrans orderTrans);
	
    void updateByMemberIdAndOrderCode(OrderTrans orderTrans);

    OrderTrans findByOrderCode(String orderCode);

    OrderTrans findByOrderTransId(Long id);

    OrderTrans findByMemberIdAndOrderCode(Long memberId, String orderCode);
	
    List<OrderTrans> findListByTransStatus(String transStatus);

    void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);
}
