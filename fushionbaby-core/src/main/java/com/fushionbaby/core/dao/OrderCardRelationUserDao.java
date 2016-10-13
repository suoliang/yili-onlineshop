package com.fushionbaby.core.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.core.model.OrderCardRelationUser;

/**
 * 
 * @author cyla
 *
 */
public interface OrderCardRelationUserDao {

	void add(OrderCardRelationUser soOrderCardRelation);

	int getTotal(Map<String, Object> params);

	List<OrderCardRelationUser> getPageList(Map<String, Object> searchParamsMap);
}
