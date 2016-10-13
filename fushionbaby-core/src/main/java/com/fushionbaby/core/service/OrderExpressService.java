package com.fushionbaby.core.service;

import java.util.List;
import java.util.Map;

import com.fushionbaby.core.model.OrderExpress;

/**
 * 物流公司表查询接口
 * 
 * @author suntao
 *
 * @param <T>
 */
public interface OrderExpressService<T extends OrderExpress> {
	void add(OrderExpress express);

	void update(OrderExpress express);

	void deleteById(Long id);

	OrderExpress findByCode(String code);

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
