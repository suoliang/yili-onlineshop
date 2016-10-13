package com.fushionbaby.member.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.member.model.MemberEmail;
/**
 * 
 * @author King
 *
 */
public interface MemberEmailDao {

	void add(MemberEmail maMemberEmail);

	void deleteById(Long id);

	void update(MemberEmail maMemberEmail);

	MemberEmail findById(Long id);

	List<MemberEmail> findAll();
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	List<MemberEmail> getListPage(Map<String, Object> map);
	
	/**
	 *  分页查询的总记录数
	 * @param map
	 * @return
	 */
	Integer getTotal(Map<String, Object> map);

	void deleteByMemberId(Long memberId);
}
