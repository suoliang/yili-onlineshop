package com.fushionbaby.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.core.dao.OrderLineUserDao;
import com.fushionbaby.core.model.OrderLineUser;
import com.fushionbaby.core.service.OrderLineUserService;

/**
 * @author 张明亮
 *
 */
@Service
public class OrderLineUserServiceImpl implements
		OrderLineUserService<OrderLineUser> {
	@Autowired
	private OrderLineUserDao orderLineUserDao;

	public void add(OrderLineUser entity) throws DataAccessException {
		orderLineUserDao.add(entity);
	}

	public void deleteById(Long id) throws DataAccessException {
		orderLineUserDao.deleteById(id);
	}

	public OrderLineUser findById(Long id) {
		return orderLineUserDao.findById(id);
	}

	public void updateIsComment(Long id, String isComment) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("isComment", isComment);
		orderLineUserDao.updateIsComment(map);
	}

	/***
	 * 根据订单code和是否评论过该商品进行查询
	 */
	public List<OrderLineUser> findByOrderCode(String orderCode,
			String isComment) {
		// 暂时屏蔽 TODO
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderCode", orderCode);
		paramMap.put("isComment", isComment);
		return orderLineUserDao.findByParam(paramMap);
	}

	public List<OrderLineUser> getListPage(Map<String, Object> map) {
		return orderLineUserDao.getListPage(map);
	}

	public Integer getTotal(Map<String, Object> map) {
		return orderLineUserDao.getTotal(map);
	}
}
