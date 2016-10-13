package com.fushionbaby.member.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.member.dto.MemberTelephoneDto;
import com.fushionbaby.member.model.MemberTelephone;
/**
 * 
 * @author King
 *
 */
public interface MemberTelephoneDao {
		
		void add(MemberTelephone maMemberTelephone);
		
		void deleteById(Long id);
		
		void update(MemberTelephone maMemberTelephone);
		
		MemberTelephone findById(Long id);
		
		List<MemberTelephone> findAll();
		
		/**
		 * 分页查询
		 * @param map
		 * @return
		 */
		List<MemberTelephone> getListPage(Map<String, Object> map);
		
		/**
		 *  分页查询的总记录数
		 * @param map
		 * @return
		 */
		Integer getTotal(Map<String, Object> map);
		/**根据条件查询数据集合*/
		List<MemberTelephone> findByCondition(MemberTelephoneDto telephoneDto);
	
		void deleteByMemberId(Long memberId);
}
