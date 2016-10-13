package com.fushionbaby.web.controller.membercenter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberExtraInfo;
import com.fushionbaby.member.model.MemberSign;
import com.fushionbaby.member.service.MemberExtraInfoService;
import com.fushionbaby.member.service.MemberGradeConfigService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.member.service.MemberSignService;
/**
 * @author  cyla
 * 
 */
@Controller
@RequestMapping("/memberSign")
public class MemberSignController {
	/**
	 * 会员签到service
	 */
	@Autowired
	private MemberSignService memberSignService;
	/**
	 * 会员service
	 */
	@Autowired
	private MemberService<Member> memberService;
	/**
	 * 会员额外信息service
	 */
	@Autowired
	private MemberExtraInfoService<MemberExtraInfo> memberExtraInfoService;
	/**
	 * 会员等级service
	 */
	@Autowired
	private MemberGradeConfigService memberGradeService;
	
	
	@ResponseBody
	@RequestMapping("/sign")
	public Object sign(HttpServletRequest request) {
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		UserDto userDto = (UserDto) SessionCache.get(sid);
		
		String type = request.getParameter("type");
		
		if (userDto == null) {
			return Jsonp.noLoginError("未登录。");
		}
		long memberId = userDto.getMember_id();
		MemberSign memberSign = memberSignService.findByMemberId(memberId);
		
		if(memberSign != null && memberSign.getLastModifyTime() != null) {

			/*	
			 * 比较最后签到时间和当前时间是否相等，如果相等则表示，该用户今天已经签到
			 */
			SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String lastSignDate = sdfDateFormat.format(memberSign.getLastModifyTime());
			String nowDate = sdfDateFormat.format(new Date());
	
			if(lastSignDate.equals(nowDate)) {
				return Jsonp.error("今天已签到,不能重复签到.");
			}
			if(type != null && !"".equals(type)) {
				return Jsonp.success();
			}
			
			/*
			 * 获取之前的签到次数,将其加1,作为新的次数
			 */
			long tempCount = memberSign.getSignCount();
			MemberSign sign = new MemberSign();
			sign.setMemberName(userDto.getLogin_name());
			sign.setSignCount(tempCount + 1);
			sign.setId(memberSign.getId());
			memberSignService.update(sign);
		}else {
			memberSign = new MemberSign();
			memberSign.setSignCount(1L);
			memberSign.setMemberName(userDto.getLogin_name());
			memberSign.setCreateTime(new Date());
			memberSign.setLastModifyTime(new Date());
			memberSign.setMemberId(memberId);
			memberSignService.add(memberSign);
		}
		
		
		/*
		 * 根据会员编号获取会员等级
		 */
		MemberExtraInfo memberExtraInfo = memberExtraInfoService.findByMemberId(memberId);
		String gradeCode =memberExtraInfo.getGradeCode();
		
		/*
		 * 根据gradeCode获取gradeValue
		 */
		int level = Integer.valueOf(memberGradeService.findByGradeCode(gradeCode).getGradeValue());
	
		Member member = memberService.findById(memberId);
		int epoints = member.getEpoints() + level;
		memberService.updateMemberEpointsById(memberId, epoints);
		
		return Jsonp_data.success(level);
	}
}
