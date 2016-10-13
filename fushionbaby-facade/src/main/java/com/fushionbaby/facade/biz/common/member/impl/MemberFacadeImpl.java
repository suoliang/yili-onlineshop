package com.fushionbaby.facade.biz.common.member.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.facade.biz.common.member.MemberFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月4日下午5:32:46
 */
@Service
public class MemberFacadeImpl implements MemberFacade {

	@Autowired
	private MemberService<Member> memberService;
	
	@Autowired
	private SysmgrGlobalConfigService globalConfigService;

	public Member loginByLoginNamePassword(String userName, String password) {
		return memberService.loginByLoginNamePassword(userName, password);
	}

	public Member findByUserName(String userName) {
		return this.memberService.findByLoginName(userName);
	}

	public void add(Member member) {
		this.memberService.add(member);
	}

	public UserDto setLoginUserInfo(String sid, Member member) {
		UserDto userDto = new UserDto();
		userDto.setSid(sid);
		userDto.setMemberId(member.getId());
		userDto.setLoginName(member.getLoginName());
		userDto.setLoginStatus(WebConstant.LOGIN_SUCCESS);
		BigDecimal epoints = (member.getEpoints() == null ? BigDecimal.ZERO : member.getEpoints());
		userDto.setEpoints(epoints);
		userDto.setImgPath(ObjectUtils.equals(member.getImgPath(), null) ? StringUtils.EMPTY : member.getImgPath());
		return userDto;
	}

	public Member findByOpenId(String openId) {
		return this.memberService.findByOpenId(openId);
	}

	public Long addLoginMember(String openId, String source, String chanel, String ipAddr) {
		return this.memberService.addLoginMember(openId, source, chanel, ipAddr);
	}

	public Member findById(Long memberId) {
		return this.memberService.findById(memberId);
	}

	public void updatePassword(String userName, String password) {
		this.memberService.updatePassword(userName, password);
	}

	/***
	 * 默认 一个设备一天最多注册三个，一共可以注册十个，还可以进行配置
	 */
//	public String findBySignId(String signId) {
//		String message="";
//		List<Member> memberList = memberService.findBySignId(signId);
//		if(memberList==null||memberList.size()<0)
//			return message;
//		SysmgrGlobalConfig config = globalConfigService.findByCode("MAX_COUNT_TOTAL");
//		Integer max_count=10;
//		if(config!=null&&StringUtils.isNotBlank(config.getValue()))
//			max_count=Integer.valueOf(config.getValue());
//		if(max_count<memberList.size())
//		    return "该设备注册用户不能超过"+max_count+"位";
//		List<Member> tempMember=new ArrayList<Member>();
//  		for (Member member : memberList) {
//			if(DateFormat.dateTimeToDateString(new Date()).equals(DateFormat.dateTimeToDateString(member.getCreateTime()))){
//				tempMember.add(member);
//			}
//		}
//  		SysmgrGlobalConfig config2 = globalConfigService.findByCode("MAX_COUNT_ONEDAY");
//  		Integer oneday_count=3;
//  		if(config2!=null&&StringUtils.isNotBlank(config2.getValue())){
//  			oneday_count=Integer.valueOf(config2.getValue());
//  		}
//  		if(tempMember.size()>oneday_count)
//		    return "该设备当天注册用户不能超过"+oneday_count+"位";
//  		return message;
//	}
}
