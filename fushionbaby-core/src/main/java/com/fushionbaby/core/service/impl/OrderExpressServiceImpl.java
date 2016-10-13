package com.fushionbaby.core.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.core.dao.OrderExpressDao;
import com.fushionbaby.core.model.OrderExpress;
import com.fushionbaby.core.service.OrderExpressService;

@Service
public class OrderExpressServiceImpl<T extends OrderExpress> implements
		OrderExpressService<T> {

	@Autowired
	private OrderExpressDao expressDao;

	public void add(OrderExpress express) {
		expressDao.add(express);

	}

	public void update(OrderExpress express) {
		expressDao.update(express);

	}

	public void deleteById(Long id) {
		expressDao.deleteById(id);

	}

	public OrderExpress findByCode(String code) {
		return expressDao.findByCode(code);
	}

	public List<OrderExpress> getListPage(Map<String, Object> map) {
		return expressDao.getListPage(map);
	}

	public Integer getTotal(Map<String, Object> map) {
		return expressDao.getTotal(map);
	}

}
