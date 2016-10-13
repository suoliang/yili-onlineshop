package com.fushionbaby.order.service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.order.model.OrderCardRelation;

/**
 * 
 * @author cyla
 *
 */
public interface OrderCardRelationService {
	void add(OrderCardRelation soOrderCardRelation);

	BasePagination findBySoCode(BasePagination page);
	
	void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);
}
