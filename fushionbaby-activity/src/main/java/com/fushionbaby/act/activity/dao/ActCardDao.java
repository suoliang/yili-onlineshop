package com.fushionbaby.act.activity.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.act.activity.model.ActCard;

/**
 * 
 * @author cyla
 * 
 */
public interface ActCardDao {
	
	void add(ActCard sysmgrActCard);

	void deleteById(Long id);

	void update(ActCard sysmgrActCard);

	ActCard findById(Long id);

	List<ActCard> findAll();
	
	ActCard findActCardByCardNo(String cardNo);

	Integer getTotal(Map<String, Object> map);

	List<ActCard> getListPage(Map<String, Object> map);

	void deleteByTypeId(Long id);
	/***
	 * 通过用户的id 查询用户的优惠券列表
	 * @param memberId
	 * @return
	 */
	List<ActCard> findByMemberId(Long memberId);

	/***
	 * 通过
	 * @param cardNo
	 * @param password
	 * @return
	 */
	ActCard findByCardNoAndPassword(Map<String, Object> map);

}
