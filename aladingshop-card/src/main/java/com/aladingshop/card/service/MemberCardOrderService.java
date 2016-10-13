package com.aladingshop.card.service;

import java.util.List;

import com.aladingshop.card.model.MemberCardOrder;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年9月30日上午10:46:23
 */
public interface MemberCardOrderService <T extends MemberCardOrder> extends BaseService<T>{
	/**
	 * 分页查询
	 * 
	 */
	BasePagination getListPage(BasePagination page);
	
	List<MemberCardOrder> findAll();

	/***
	 * 会员id和订单号查询
	 * @param memberId
	 * @param orderCode
	 * @return
	 */
	MemberCardOrder findByMemberIdOrderCode(Long memberId, String orderCode);

	/***
	 * 更新订单状态
	 * @param cardOrder
	 */
	void updateByMemberIdOrderCode(MemberCardOrder cardOrder);
	
	
}
