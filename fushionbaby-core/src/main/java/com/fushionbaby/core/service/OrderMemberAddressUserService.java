package com.fushionbaby.core.service;

import com.fushionbaby.core.model.OrderMemberAddressUser;

/**
 * @author 张明亮
 * @param <T>
 * 订单的客户信息。记录有客户当时的基本信息，记录此信息的目的是保留客户购买当时的必要联系信息。
 */
public interface OrderMemberAddressUserService<T extends OrderMemberAddressUser>{
	void add(OrderMemberAddressUser soSoMember);
	
	/**
	 * 获得根据订单编码orderCode订单的收货地址
	 * @return
	 */
	public OrderMemberAddressUser getOrderAddress(String orderCode);
	
	 /**
     * 根据id删除订单收货地址,这个删除只用于Test测试
     * @param id
     */
    void deleteById(Long id);
}
