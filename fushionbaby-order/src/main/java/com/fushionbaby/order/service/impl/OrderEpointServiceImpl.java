package com.fushionbaby.order.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.order.dao.OrderEpointDao;
import com.fushionbaby.order.model.OrderEpoint;
import com.fushionbaby.order.service.OrderEpointService;

@Service
public class OrderEpointServiceImpl implements OrderEpointService<OrderEpoint> {
	@Autowired
	private OrderEpointDao orderEpointDao; 
	
	public void add(OrderEpoint integral) {
		orderEpointDao.add(integral);
	}

	public void deleteById(Long id) {
		orderEpointDao.deleteById(id);
		
	}
	
	public BasePagination getListPage(BasePagination page)throws DataAccessException {
		Integer total = orderEpointDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<OrderEpoint> list = orderEpointDao
					.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<OrderEpoint>());
		}
		return page;
	}

	public List<OrderEpoint> getListPage(Map<String, Object> map) {
		
		return orderEpointDao.getListPage(map);
	}



}
