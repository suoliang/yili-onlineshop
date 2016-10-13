package com.fushionbaby.core.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.core.model.OrderLineUser;

/**
 * 
 * @author 张明亮 t_order_line 销售订单行，行中记录有当时销售的单价和数量情况，如果和某个促销匹配，还会记录对应的促销信息。
 */
public interface OrderLineUserDao {

	void add(OrderLineUser orderLineUser);

	/**
	 * 删除一个订单行
	 * 
	 * @param id
	 */
	void deleteById(Long id);

	public OrderLineUser findById(Long id);

	public void updateIsComment(Map<String, Object> map);

	/***
	 * 根据订单code和评论状态查询订单明细
	 * 
	 * @param orderCode
	 * @param isComment
	 *            评论状态可为空
	 * @return
	 */
	List<OrderLineUser> findByParam(Map<String, Object> param);
	
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