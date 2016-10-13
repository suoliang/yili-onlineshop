package com.fushionbaby.core.service;

import java.util.List;

import com.fushionbaby.core.model.OrderEpoint;

public interface OrderEpointService<T extends OrderEpoint> {

	/**添加操作*/
	public void add(OrderEpoint integral);
	/**通过会员id查询出购买积分商品记录*/
	public List<OrderEpoint> findByMemberId(Long memberId);
	
}
