package com.fushionbaby.member.service;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.member.model.MemberExtraInfo;
/**
 * 
 * @author King
 *
 */
public interface MemberExtraInfoService<T extends MemberExtraInfo> extends BaseService<T> {
	/***
	 * 通过会员id拿到用户额外信息
	 * @param member_id
	 * @return
	 */
	MemberExtraInfo findByMemberId(Long member_id);
	
	/**
	 * 通过会员ID修改额外信息
	 * @param maMemberInfo
	 */
	void updateByMemberId(MemberExtraInfo maMemberInfo);

	/***
	 * app和web公用的用户注册时的额外信息添加
	 * 额外会员表添加一个id
	 * @param id
	 */
	void addMemberExtraInfo(Long memberId);
	
	
	void deleteByMemberId(Long memberId);
}
