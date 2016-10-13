package com.fushionbaby.core.service;

import java.util.List;
import java.util.Map;

import com.fushionbaby.core.model.OrderLineUser;

/**
 * @author 张明亮
 * @param <T>
 * 销售订单行，行中记录有当时销售的单价和数量情况，如果和某个促销匹配，还会记录对应的促销信息。
 * 订单明细
 */
public interface OrderLineUserService<T extends OrderLineUser>{
	public void add(OrderLineUser entity);
	public void deleteById(Long id);

	/**
	 * 根据订单code和是否评论状态查询订单行信息，评论状态可为空
	 * @param orderId
	 * @return List<SoSoLine>
	 */
	public List<OrderLineUser> findByOrderCode(String orderCode,String isComment);
	
	public OrderLineUser findById(Long id);
	public void updateIsComment(Long id,String isComment);
	
	/**
	 * 分页查询订单行基本信息
	 * @param map
	 * @return
	 */
	List<OrderLineUser> getListPage(Map<String, Object> map);
	
	/**
	 * 分页查询订单行信息的总记录数
	 * @param map
	 * @return
	 */
	Integer getTotal(Map<String, Object> map);
	
	
}
