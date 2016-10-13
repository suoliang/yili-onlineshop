package com.fushionbaby.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.order.model.OrderMemberAddress;

/**
 * @description 订单的客户信息。记录有客户当时的基本信息，记录此信息的目的是保留客户购买当时的必要联系信息。
 * @author 索亮
 * @date 2015年7月28日上午9:18:36
 */
public interface OrderMemberAddressDao {

	/**
	 * 根据会员id和订单编号查询对象
	 * @param memberId
	 * @param orderCode
	 * @return
	 */
	OrderMemberAddress findByMemberIdAndOrderCode(Map<String, Object> map);
	
    /**
     * 根据订单编码
     * @param orderCode
     * @return
     */
    OrderMemberAddress findByOrderCode(String orderCode);
    
    /**
     * 更新record属性不空的字段
     * @param record
     */
    void updateByOrderCodeAddress(OrderMemberAddress record);
    
    /**
     * 根据id删除订单收货地址,这个删除只用于Test测试
     * @param id
     */
    void deleteById(Long id);

    /**
     * 只用于Test测试
     * @param soSoMember
     */
	void add(OrderMemberAddress soSoMember);
	
	/**
	 * 分页查询订单基本信息的总记录数
	 * @param map
	 * @return
	 */
	Integer getTotal(Map<String, Object> map);
	
	/**
	 * 分页查询订单基本信息
	 * @param map
	 * @return
	 */
	List<OrderMemberAddress> getListPage(Map<String, Object> map);
	
	void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);

}