package com.fushionbaby.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.core.model.OrderFinanceUser;

public interface OrderFinanceUserDao {

	void addOrderFinanceUser(OrderFinanceUser orderFinanceUser);

	OrderFinanceUser findByParam(Map<String, Object> param);
	/** 通过会员id和订单号查询订单财务信息 */
	OrderFinanceUser findByMemberIdAndOrderCode(@Param("memberId")Long memberId, @Param("orderCode")String orderCode);
	/** 通过memberId和订单号修改付款方式 */
	void updateByMemberIdAndOrderCode(OrderFinanceUser orderFinanceUser);
	/** 通过memberId和是否支付成功查询订单集合 */
	List<OrderFinanceUser> findByMIdAndStatus(@Param("memberId")Long memberId, @Param("financeStatus")String financeStatus);
}
