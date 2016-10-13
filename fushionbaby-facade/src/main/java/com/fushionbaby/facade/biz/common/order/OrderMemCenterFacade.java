package com.fushionbaby.facade.biz.common.order;

import com.fushionbaby.core.model.OrderLineUser;
import com.fushionbaby.core.model.request.OrderUserReq;
import com.fushionbaby.core.model.response.EvaluateOrderUserRes;
import com.fushionbaby.core.model.response.OrderUserRes;
import com.fushionbaby.core.vo.OrderUser;

public interface OrderMemCenterFacade {
	/** 获取订单集合列表 */
	OrderUserRes getOrderListByParam(OrderUserReq orderUserReq);
	/** 查询待评价订单的集合列表 */
	EvaluateOrderUserRes getEvaluateOrderListByParam(OrderUserReq orderUserReq);
	/** 取消订单操作 -- 积分 优惠券 库存 订单状态 */
	void cancelOrder(Long memberId,String orderCode);
	/** 得到订单的详情信息-web */
	OrderUser getOrderInfo(Long memberId, String orderCode);
	/** */
	/***
	 * 
	 * @param orderLineId 通过订单行id得到订单行信息
	 * @param type  是否评论过  y 是，n 否
	 * @return
	 */
	OrderLineUser findByOrderLineId(String orderLineId,String type);
	/** 获取订单一些状态的数量 */
	OrderUserRes getEachOrderQuantity(OrderUserReq request);
}
