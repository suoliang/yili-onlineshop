package com.fushionbaby.order.dao;


import java.util.List;
import java.util.Map;

import com.fushionbaby.order.model.OrderEpoint;
/***
 * 
 * @author 余浩
 *
 *
 */
public interface OrderEpointDao {
	public void add(OrderEpoint integral);
	
	public void deleteById(Long id);
	
	List<OrderEpoint> getListPage(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
}
