package com.fushionbaby.core.service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.core.model.OrderCardRelationUser;

/**
 * 
 * @author cyla
 *
 */
public interface OrderCardRelationUserService {
	void add(OrderCardRelationUser soOrderCardRelation);

	BasePagination findBySoCode(BasePagination page);
}
