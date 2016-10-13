package com.fushionbaby.facade.biz.common.member;

import com.fushionbaby.member.model.MemberExtraInfo;

/***
 * 
 * @description 类描述... 会员额外信息
 * @author 徐培峻
 * @date 2015年8月5日上午9:55:49
 */
public interface MemberExtraInfoFacade {

	MemberExtraInfo findByMemberId(Long memberId);

	void updateByMemberId(MemberExtraInfo memberExtraInfo);

	void addMemberExtraInfo(Long id);

}
