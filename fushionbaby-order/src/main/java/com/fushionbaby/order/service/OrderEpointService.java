package com.fushionbaby.order.service;

import java.util.List;
import java.util.Map;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.order.model.OrderEpoint;

public interface OrderEpointService<T extends OrderEpoint> {

	public void add(OrderEpoint integral);
	
	public void deleteById(Long id);
	
	
	BasePagination getListPage(BasePagination page);
	
	List<OrderEpoint> getListPage(Map<String, Object> map);
	
}
