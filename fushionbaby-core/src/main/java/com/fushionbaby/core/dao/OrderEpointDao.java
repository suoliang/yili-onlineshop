package com.fushionbaby.core.dao;

import java.util.List;

import com.fushionbaby.core.model.OrderEpoint;
/***
 * 
 * @author 余浩
 *
 *
 */
public interface OrderEpointDao {
	
	public void add(OrderEpoint integral);

	public List<OrderEpoint> findByMemberId(Long memberId);
	
}
