package com.fushionbaby.facade.biz.common.member.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.facade.biz.common.member.MemberExtraInfoFacade;
import com.fushionbaby.member.model.MemberExtraInfo;
import com.fushionbaby.member.service.MemberExtraInfoService;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月5日上午9:57:33
 */
@Service
public class MemberExtraInfoFacadeImpl implements MemberExtraInfoFacade{
	
	@Autowired
	private MemberExtraInfoService<MemberExtraInfo> memberExtraInfoService;
	public MemberExtraInfo findByMemberId(Long memberId) {
		return memberExtraInfoService.findByMemberId(memberId);
	}
	public void updateByMemberId(MemberExtraInfo memberExtraInfo) {
		memberExtraInfoService.updateByMemberId(memberExtraInfo);
	}
	public void addMemberExtraInfo(Long id) {
      this.memberExtraInfoService.addMemberExtraInfo(id);		
	}

}
