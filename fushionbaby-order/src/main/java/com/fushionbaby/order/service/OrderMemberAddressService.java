package com.fushionbaby.order.service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.order.model.OrderMemberAddress;

/**
 * @author 张明亮
 * @param <T>
 * 订单的客户信息。记录有客户当时的基本信息，记录此信息的目的是保留客户购买当时的必要联系信息。
 */
public interface OrderMemberAddressService<T extends OrderMemberAddress>{
	void add(OrderMemberAddress orderMemberAddress);
	
	/**
	 * 根据会员id和订单编号查询对象
	 * @param memberId
	 * @param orderCode
	 * @return
	 */
	OrderMemberAddress findByMemberIdAndOrderCode(Long memberId,String orderCode);
	
	/**
     * 根据订单编码查询,订单收货地址
     * @param SoCode
     * @return
     */
	OrderMemberAddress findByOrderCode(String orderCode);
	
	/**
	 * 更新订单收货地址
	 * @param record
	 */
    void updateByOrderCodeAddress(OrderMemberAddress orderMemberAddress);
    
    /**
     * 根据id删除订单收货地址,这个删除只用于Test测试
     * @param id
     */
    void deleteById(Long id);
    
    /** 分页查询订单基本表信息 */
	BasePagination getListPage(BasePagination pageParams);
	
	void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);

}
