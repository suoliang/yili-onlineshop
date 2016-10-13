package com.fushionbaby.order.service;
import java.util.Date;
import java.util.List;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.order.model.OrderFinance;

public interface OrderFinanceService <T extends OrderFinance>{
	
    void updateByMemberIdAndOrderCode(OrderFinance orderFinance);

    OrderFinance findByOrderCode(String orderCode);

    OrderFinance findByOrderFinanceId(Long id);

    OrderFinance findByMemberIdAndOrderCode(Long memberId,String orderCode);
	
    List<OrderFinance> findListByFinanceStatus(String transStatus);
    
    List<OrderFinance> findListByPaymentType(String paymentType);
    /** 得到某一天付款后的订单数量和订单总金额 */
    OrderFinance getOneDayCountByCreateTime(String createTime);
    /** 得到某一天之前的购买次数超过1次的用户数量 */
    Integer getBuyOverOneNumberByCreateTime(String createTime);
    
  
    /** 分页查询订单财务表信息 */
	BasePagination findOrderFinanceListPage(BasePagination pageParams);
	void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);
	
	/**
	 * 根据财务状态和付款成功时间查询出前一天的list集合
	 * @description 上一天的数据
	 * @param financeStatus
	 * @param paymentCompleteTime
	 * @return
	 */
	List<OrderFinance> findListByFinanceStatusAndLastPayTime(String financeStatus, Date paymentCompleteTime);
	
}
