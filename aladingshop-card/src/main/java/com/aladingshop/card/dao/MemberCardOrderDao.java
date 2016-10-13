package com.aladingshop.card.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.card.model.MemberCardOrder;
/***
 * 益多宝订单
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年9月30日上午10:45:30
 */
public interface MemberCardOrderDao {

	void deleteById(Long id);

	void add(MemberCardOrder order);

	MemberCardOrder findById(Long id);

	List<MemberCardOrder> findAll();

	void update(MemberCardOrder order);

	/**
	 * 分页查询
	 * 
	 */
	List<MemberCardOrder> getListPage(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * 
	 */
	Integer getTotal(Map<String, Object> map);

	void updateByMemberIdOrderCode(MemberCardOrder cardOrder);

	MemberCardOrder findByMemberIdOrderCode(Map<String, Object> map);


}