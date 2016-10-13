package com.fushionbaby.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.core.dao.OrderMemberAddressUserDao;
import com.fushionbaby.core.model.OrderMemberAddressUser;
import com.fushionbaby.core.service.OrderMemberAddressUserService;

/**
 * @author 张明亮
 * 订单收货地址操作
 */
@Service
public class OrderMemberAddressUserServiceImpl implements OrderMemberAddressUserService<OrderMemberAddressUser> {
    @Autowired
	private OrderMemberAddressUserDao soSoMemberWebDao;
	public void add(OrderMemberAddressUser soSoMember) throws DataAccessException {
		soSoMemberWebDao.add(soSoMember);
	}
	public OrderMemberAddressUser getOrderAddress(String orderCode) {
		return soSoMemberWebDao.findByOrderCode(orderCode);
	}
	
	/**
     * 根据id删除订单收货地址,这个删除只用于Test测试
     * @param id
     */
    public void deleteById(Long id){
    	soSoMemberWebDao.deleteById(id);
    }
}
