package com.fushionbaby.member.dao;

import java.util.List;

import com.fushionbaby.member.model.MemberExtraInfo;
/**
 * 
 * @author King
 *
 */
public interface MemberExtraInfoDao {
		
		void add(MemberExtraInfo maMemberInfo);
		
		void deleteById(Long id);
		
		void update(MemberExtraInfo maMemberInfo);
		
		MemberExtraInfo findById(Long id);
		
		List<MemberExtraInfo> findAll();

		/***
		 * 通过会员id拿到用户额外信息
		 * @param memberId
		 * @return
		 */
		MemberExtraInfo findByMemberId(Long member_id);

		/**
		 * 通过会员ID修改额外信息
		 * @param maMemberInfo
		 */
		void updateByMemberId(MemberExtraInfo maMemberInfo);
		
		void deleteByMemberId(Long memberId);
}
