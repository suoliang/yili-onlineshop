package com.fushionbaby.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.order.model.OrderCardRelation;

/**
 * 
 * @author cyla
 *
 */
public interface OrderCardRelationDao {

	void add(OrderCardRelation soOrderCardRelation);

	int getTotal(Map<String, Object> params);

	List<OrderCardRelation> getPageList(Map<String, Object> searchParamsMap);
	
	void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);
}
