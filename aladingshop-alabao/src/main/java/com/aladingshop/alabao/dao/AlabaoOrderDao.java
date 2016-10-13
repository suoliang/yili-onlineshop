package com.aladingshop.alabao.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.alabao.model.AlabaoOrder;
import com.fushionbaby.common.util.BasePagination;

public interface AlabaoOrderDao {
	
	public void add(AlabaoOrder alabaoOrder);
	
	public void deleteById(Long id);
	
	public void updateById(AlabaoOrder alabaoOrder);	
	
	public AlabaoOrder findById(Long id);

	/**
	 * 分页查询
	 * 
	 */
	List<AlabaoOrder> getListPage(Map<String, Object> map);
	
	/**
	 * 分页查询的总记录数
	 * 
	 */
	Integer getTotal(Map<String, Object> map);
	

	/**
	 * 根据memberId和orderCode查询信息
	 * @param map
	 * @return
	 */
	AlabaoOrder findByMemberIdOrderCode(Map<String, Object> map);
	

	/**
	 * 根据memberId和orderCode修改信息
	 * @return
	 */
	void updateByMemberIdOrderCode(AlabaoOrder alabaoOrder);
	
	
	Map<String, Object> getListPageTotalActual(Map<String, Object> map);
}