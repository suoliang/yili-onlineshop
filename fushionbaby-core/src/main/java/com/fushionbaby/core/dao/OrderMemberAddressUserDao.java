package com.fushionbaby.core.dao;

import com.fushionbaby.core.model.OrderMemberAddressUser;

/**
 * 
 * @author Luke
 * 
 * t_order_member
 * 订单的客户信息。记录有客户当时的基本信息，记录此信息的目的是保留客户购买当时的必要联系信息。
 */
public interface OrderMemberAddressUserDao {

	/**
	 * 添加记录,订单发货地址表,和订单表一对一关系
	 * @param record
	 */
    void add(OrderMemberAddressUser record);

    /**
     * 根据订单编码
     * @param orderCode 订单编码
     * @param memberId 会员id
     * @return SoSoMember 返回会员订单地址
     */
    OrderMemberAddressUser findByOrderCode(String orderCode);
    
    /**
     * 根据id删除订单收货地址,这个删除只用于Test测试
     * @param id
     */
    void deleteById(Long id);
}