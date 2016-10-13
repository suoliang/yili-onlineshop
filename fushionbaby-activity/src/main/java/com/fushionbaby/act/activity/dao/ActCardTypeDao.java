package com.fushionbaby.act.activity.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.act.activity.model.ActCardType;

/**
 * 
 * @author cyla
 * 
 */
public interface ActCardTypeDao {

	void add(ActCardType ActCardType);

	void deleteById(Long id);

	void update(ActCardType ActCardType);

	List<ActCardType> findAll();

	ActCardType findById(Long id);

	Integer getTotal(Map<String, Object> searchParamsMap);

	List<ActCardType> getListPage(Map<String, Object> searchParamsMap);

	/***
	 * 获得一个可以可以使用的优惠券（没过期，可用）
	 * 
	 * @param cardTypeId
	 * @return
	 */
	ActCardType findUsableById(Long cardTypeId);
	
	/***
	 * 通过代金券的类型编码查询
	 * @param code
	 * @return
	 */
	ActCardType findByCode(String code);
	
}
