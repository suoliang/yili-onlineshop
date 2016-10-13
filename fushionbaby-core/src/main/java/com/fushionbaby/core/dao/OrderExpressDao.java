package com.fushionbaby.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.core.model.OrderExpress;

public interface OrderExpressDao {

	void add(OrderExpress express);

	void update(OrderExpress express);

	void deleteById(Long id);

	OrderExpress findByCode(@Param("code") String code);

	/**
	 * 分页查询物流公司基本信息
	 * 
	 * @param map
	 * @return
	 */
	List<OrderExpress> getListPage(Map<String, Object> map);

	/**
	 * 分页查询物流公司基本信息的总记录数
	 * 
	 * @param map
	 * @return
	 */
	Integer getTotal(Map<String, Object> map);
}
