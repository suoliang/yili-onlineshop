package com.fushionbaby.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.order.model.OrderFinance;


public interface OrderFinanceDao {
	
    void updateByMemberIdAndOrderCode(OrderFinance orderFinance);

    OrderFinance findByOrderCode(String orderCode);

    OrderFinance findByOrderFinanceId(Long id);

    OrderFinance findByMemberIdAndOrderCode(@Param("memberId")Long memberId,@Param("orderCode")String orderCode);
	
    List<OrderFinance> findListByFinanceStatus(String transStatus);
    
    List<OrderFinance> findListByPaymentType(String paymentType);
    /**
	 * 得到某一天付款后的订单数量和订单总金额
	 * @return
	 */
    OrderFinance getOneDayCountByCreateTime(Map<String, Object> map);
    /** 得到某一天之前的购买次数超过1次的用户数量 */
    Integer getBuyOverOneNumberByCreateTime(Map<String, Object> map);
    
    List<OrderFinance> findList(Map<String, Object> map);
    
    Integer getTotal(Map<String, Object> map);
    
    void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);

    /***
     * 根据财务状态和付款成功时间查询出前一天的list集合
	 * @description 上一天的数据
     * @param map
     * @return
     */
	List<OrderFinance> findListByFinanceStatusAndLastPayTime(Map<String, Object> map);
}