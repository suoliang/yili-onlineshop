package com.fushionbaby.core.service;

import java.util.List;
import java.util.Map;

import com.fushionbaby.core.model.OrderFinanceUser;

public interface OrderFinanceUserService<T extends OrderFinanceUser> {
	/** 添加订单财务 */
	void addOrderFinanceUser(OrderFinanceUser orderFinanceUser);
	
	/** 通过订单号和会员id查询订单财务信息 */
	OrderFinanceUser findByMemberIdAndOrderCode(Long memberId,String orderCode);
	
	/** 通过会员id和是否支付成功查询订单财务集合 */
	List<OrderFinanceUser> findByMIdAndStatus(Long memberId,String financeStatus);
	
	/** 通过memberId和订单号修改 */
	void updateByMemberIdAndOrderCode(OrderFinanceUser orderFinanceUser);
	
	OrderFinanceUser findByParam(Map<String, Object> param);
}
