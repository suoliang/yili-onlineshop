/**
 * 
 */
package com.aladingshop.card.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.card.model.MemberCardBack;

/**
 * @description 退卡明细dao
 * @author 孙涛
 * @date 2015年10月10日下午2:26:52
 */
public interface MemberCardBackDao {
	void add(MemberCardBack cardBack);

	void deleteById(Long id);

	void update(MemberCardBack cardBack);

	List<MemberCardBack> findAll();

	MemberCardBack findById(Long id);

	/**
	 * 分页查询
	 * 
	 */
	List<MemberCardBack> getListPage(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * 
	 */
	Integer getTotal(Map<String, Object> map);
	
	MemberCardBack findByCardNo(String cardNo);
}
