package com.fushionbaby.order.service;

import java.util.List;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.order.model.OrderLine;

/**
 * @author 张明亮
 * @param <T>
 * 销售订单行，行中记录有当时销售的单价和数量情况，如果和某个促销匹配，还会记录对应的促销信息。
 * 订单明细
 */
public interface OrderLineService<T extends OrderLine>{
	public void add(OrderLine entity);
	public void deleteById(Long id);

	/**
	 * 根据订单code和是否评论状态查询订单行信息，评论状态可为空
	 * @param orderId
	 * @return List<SoSoLine>
	 */
	public List<OrderLine> findByOrderCode(String orderCode,String isComment);
	
	public OrderLine findById(Long id);
	public void updateIsComment(Long id,String isComment);
	
	/**
	 * 根据商品code查订单code集合
	 * 
	 */
	public List<String>  findSoOrderCodeBySkuCode(String skuCode);
	
	BasePagination getListPage(BasePagination pageParams);
	
	public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId);
}
