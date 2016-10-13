package com.aladingshop.web.controller.login;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.ChannelConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.common.util.PhoneEmailFormatUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.facade.biz.common.member.MemberExtraInfoFacade;
import com.fushionbaby.facade.biz.common.member.MemberFacade;
import com.fushionbaby.facade.biz.common.sms.EmailServiceFacade;
import com.fushionbaby.facade.biz.common.sms.SmsServiceFacade;
import com.fushionbaby.member.model.Member;

/***
 * 
 * @description 类描述...用户注册
 * @author 徐培峻
 * @date 2015年8月10日下午4:01:36
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private SmsServiceFacade smsServiceFacade;

	@Autowired
	private MemberFacade memberFacade;

	@Autowired
	private MemberExtraInfoFacade memberExtraInfoFacade;

	@Autowired
	private EmailServiceFacade emailServiceFacade;
    /**全局设置*/
	@Autowired
	private SysmgrGlobalConfigService globalConfigService;
	/** 记录日志 */
	private static final Log RegisterLog = LogFactory.getLog(RegisterController.class);

	private static final String REGISTER = "register";
	private static final String FORGET = "forget";

	@RequestMapping("/register")
	public String register(@RequestParam("userName") String userName, @RequestParam("password") String password,Map<String, Object> model
			,HttpServletRequest request) {
		Member member=this.memberFacade. findByUserName(userName);
		if(member==null){
			 member = new Member();
			if (PhoneEmailFormatUtil.isEmail(userName)) {
				member.setEmail(userName);
			}
			if (PhoneEmailFormatUtil.isPhone(userName)) {
				member.setTelephone(userName);
			}
			member.setLoginName(userName);
			member.setPassword(MD5Util.MD5(password));
			member.setChannelCode(ChannelConstant.DEFAULT_CHANNEL);
			member.setSourceCode(SourceConstant.WEB_CODE);
			member.setVersion(new Date());
			member.setIpAddress(GetIpAddress.getIpAddr(request));
			memberFacade.add(member);
			memberExtraInfoFacade.addMemberExtraInfo(member.getId());
		}
		
		model.put("userName", userName);
		model.put("password", password);
		return "/login/login";
	}

	@RequestMapping("/sendSecurityCode")
	@ResponseBody
	public Object sendSecurityCode(@RequestParam("userName") String userName, @RequestParam("smsType") String smsType,
			@RequestParam("number") String number,HttpServletRequest request, HttpServletResponse response) {
		try {
			/**设置发短信时间间隔   begin*/
			Long now_time=System.currentTimeMillis();
			Integer time = globalConfigService.getConfigValueByCode(CookieConstant.LAST_SEND_CODE_TIME);
			Long last_time=(Long) DataCache.get(CookieConstant.LAST_SEND_CODE_TIME);
			if(last_time==null){
				last_time=Long.valueOf(0);
				DataCache.put(CookieConstant.LAST_SEND_CODE_TIME, now_time);
			}
			if(time==null||now_time-last_time<Long.valueOf(time))
				return Jsonp.error();
			/** 设置发短信时间间隔  end*/
			/**图片验证码校验 begin*/
			String numberOld=(String) request.getSession().getAttribute("code");
			if(StringUtils.isBlank(number)||ObjectUtils.notEqual(number.toUpperCase(), numberOld)){
				return Jsonp.error();
			}
			/**图片验证码校验 end*/
			String securityCode = RandomNumUtil.getRandom(RandomNumUtil.NUM, RandomNumUtil.REGISTER_USER_LENGTH);
			String scode=RandomNumUtil.getCharacterAndNumber(AppConstant.UNIQUE_CODE);
			Long smsTypeId = null;
			String cookieCode = null;
		//	int emailType = 1;
			if (StringUtils.equals(smsType, FORGET)) {
				smsTypeId = SmsConstant.SMS_TYPE_FORGET_ID;
				cookieCode = CookieConstant.FORGETPASSWORD_COOKIE;
				//emailType = EmailConstant.EMAIL_TYPE_FORGET;
			}
			if (StringUtils.equals(smsType, REGISTER)) {
				smsTypeId = SmsConstant.SMS_TYPE_REGISTER_ID;
				cookieCode = CookieConstant.REGISTER_COOKIE;
				//emailType = EmailConstant.EMAIL_TYPE_REGISTER;
			}
			CookieUtil.setCookie(response, cookieCode, scode);
			/** 默认七天 */
			DataCache.put(scode, securityCode);
			/** 默认半小时 */
//			if (PhoneEmailFormatUtil.isEmail(userName)) {
//				SendMailCode.sendHtmlEmail(EmailConstant.SENDER_EMAIL, userName, EmailConstant.EMAIL_SUBJECT,
//						EmailConstant.EMAIL_CONTENT.replace(EmailConstant.EMAIL_CODE, securityCode));
//				Email email = new Email();
//				email.setCreateTime(new Date());
//				email.setEmailContent(EmailConstant.EMAIL_CONTENT.replace(EmailConstant.EMAIL_CODE, securityCode));
//				email.setEmailType(emailType);
//				email.setMemberName(userName);
//				email.setReceiverEmail(userName);
//				email.setSourceCode(SourceConstant.WEB_CODE);
//				this.emailServiceFacade.add(email);
//			}
			if (PhoneEmailFormatUtil.isPhone(userName)) {
				smsServiceFacade.sendSmsRegisterCode(securityCode, userName, SourceConstant.WEB_CODE, smsTypeId);
			}
			DataCache.put(CookieConstant.LAST_SEND_CODE_TIME,now_time);
			return Jsonp.success();
		} catch (Exception e) {
			RegisterLog.error("web:RegisterController.java 发送验证码异常", e);
			return Jsonp.error();
		}
	}

	@RequestMapping("checkSecurityCode")
	public void checkSecurityCode(@RequestParam("code") String code,@RequestParam("status") String status,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			boolean result = false;
			response.reset();
			response.setContentType("text/html;charset=UTF-8");
			String oldCode = null;
			String scode=null;
			if (StringUtils.equals(status, REGISTER))
				scode = CookieUtil.getCookieValue(request, CookieConstant.REGISTER_COOKIE);
			if (StringUtils.equals(status, FORGET))
				scode = CookieUtil.getCookieValue(request, CookieConstant.FORGETPASSWORD_COOKIE);
			oldCode=(String) DataCache.get(scode);
			if (StringUtils.equals(code, oldCode))
				result = true;
			RegisterLog.info("web:RegisterController.java 验证验证码异常***********"+oldCode);
			response.getWriter().print(result);
		} catch (IOException e) {
			RegisterLog.error("web:RegisterController.java 验证验证码异常", e);
		}
	}

}
