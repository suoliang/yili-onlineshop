package com.fushionbaby.core.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.core.dao.OrderFinanceUserDao;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.service.OrderFinanceUserService;
/**
 * 订单财务
 * @author Leon
 *
 */
@Service
public class OrderFinanceUserServiceImpl implements OrderFinanceUserService<OrderFinanceUser> {

	@Autowired
	private OrderFinanceUserDao orderFinanceUserDao;

	public void addOrderFinanceUser(OrderFinanceUser orderFinanceUser) {
		orderFinanceUserDao.addOrderFinanceUser(orderFinanceUser);
	}

	public OrderFinanceUser findByMemberIdAndOrderCode(Long memberId,String orderCode) {
		return orderFinanceUserDao.findByMemberIdAndOrderCode(memberId,orderCode);
	}

	public List<OrderFinanceUser> findByMIdAndStatus(Long memberId,
			String financeStatus) {
		return orderFinanceUserDao.findByMIdAndStatus(memberId,financeStatus);
	}
	
	public void updateByMemberIdAndOrderCode(OrderFinanceUser orderFinanceUser) {
		orderFinanceUserDao.updateByMemberIdAndOrderCode(orderFinanceUser);
	}

	public OrderFinanceUser findByParam(Map<String, Object> param) {
		return orderFinanceUserDao.findByParam(param);
	}

}