package com.fushionbaby.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.order.dao.OrderFeeDao;
import com.fushionbaby.order.model.OrderFee;
import com.fushionbaby.order.service.OrderFeeService;

/**
 * @author 张明亮 订单收货地址操作
 */
@Service
public class OrderFeeServiceImpl implements OrderFeeService<OrderFee> {
	@Autowired
	private OrderFeeDao orderFeeDao;

	public OrderFee findByOrderCode(String orderCode) {
		return orderFeeDao.findByOrderCode(orderCode);
	}

	public OrderFee findByOrderFeeId(Long id) {
		return orderFeeDao.findByOrderFeeId(id);
	}

	public OrderFee findByMemberIdAndOrderCode(Long memberId, String orderCode) {
		return orderFeeDao.findByMemberIdAndOrderCode(memberId, orderCode);
	}

	public void add(OrderFee orderFee) {
		orderFeeDao.add(orderFee);
	}

	public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId){
		orderFeeDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
	}
}
