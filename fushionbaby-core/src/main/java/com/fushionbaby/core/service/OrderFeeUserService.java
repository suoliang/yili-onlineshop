package com.fushionbaby.core.service;

import java.util.Map;

import com.fushionbaby.core.model.OrderFeeUser;

public interface OrderFeeUserService<T extends OrderFeeUser> {
	/** 添加 */
	void addOrderFeeUser(OrderFeeUser orderFeeUser);
	/** 通过会员id和订单号查询对象 */
	OrderFeeUser findByMIdAndOrdCode(Long memberId,String orderCode);
	/** 通过会员id和订单号查询对象 */
	OrderFeeUser findByParam(Map<String, Object> map);
	/** 根据会员id和订单号修改 */
	void updateMIdAndOrdCode(OrderFeeUser orderFeeUser);
}
