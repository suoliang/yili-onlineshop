package com.fushionbaby.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.order.dao.OrderMemberAddressDao;
import com.fushionbaby.order.model.OrderMemberAddress;
import com.fushionbaby.order.service.OrderMemberAddressService;

/**
 * @author 张明亮
 * 订单收货地址操作
 */
@Service
public class OrderMemberAddressServiceImpl implements OrderMemberAddressService<OrderMemberAddress> {
    @Autowired
	private OrderMemberAddressDao orderMemberAddressDao;
    
    public OrderMemberAddress findByMemberIdAndOrderCode(Long memberId,String orderCode) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("memberId", memberId);
    	map.put("orderCode", orderCode);
		return orderMemberAddressDao.findByMemberIdAndOrderCode(map);
	}
    
    /**根据订单编码查询出订单客户信息*/
	public OrderMemberAddress findByOrderCode(String orderCode) {
		return orderMemberAddressDao.findByOrderCode(orderCode);
	}
	
	public void updateByOrderCodeAddress(OrderMemberAddress record) {
		orderMemberAddressDao.updateByOrderCodeAddress(record);
	}
	
	/**
     * 根据id删除订单收货地址,这个删除只用于Test测试
     * @param id
     */
    public void deleteById(Long id){
    	orderMemberAddressDao.deleteById(id);
    }
    /**
     * 添加 -- 只用于Test测试
     */
	public void add(OrderMemberAddress soSoMember) {
		orderMemberAddressDao.add(soSoMember);
	}

	public BasePagination getListPage(
			BasePagination page) {
		Integer total = orderMemberAddressDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<OrderMemberAddress> list = orderMemberAddressDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<OrderMemberAddress>());
		}
		return page;
	}

	public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId){
		orderMemberAddressDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
	}


}
