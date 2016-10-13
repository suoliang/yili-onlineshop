package com.fushionbaby.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.order.dao.OrderTransDao;
import com.fushionbaby.order.model.OrderTrans;
import com.fushionbaby.order.service.OrderTransService;

/**
 * @author 张明亮
 * 订单收货地址操作
 */
@Service
public class OrderTransServiceImpl implements OrderTransService<OrderTrans> {
    @Autowired
	private OrderTransDao orderTransDao;

    public void updateByMemberIdAndOrderCode(OrderTrans orderTrans){
    	orderTransDao.updateByMemberIdAndOrderCode(orderTrans);
    }

    public OrderTrans findByOrderCode(String orderCode){
    	return orderTransDao.findByOrderCode(orderCode);
    }

    public OrderTrans findByOrderTransId(Long id){
    	return orderTransDao.findByOrderTransId(id);
    }

    public void add(OrderTrans orderTrans){
		orderTransDao.add(orderTrans);
    }

	public List<OrderTrans> findListByTransStatus(String transStatus) {
		return orderTransDao.findListByTransStatus(transStatus);
	}

	public OrderTrans findByMemberIdAndOrderCode(Long memberId,String orderCode) {
		return orderTransDao.findByMemberIdAndOrderCode(memberId, orderCode);
	}
	
	public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId){
		orderTransDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
	}
}
