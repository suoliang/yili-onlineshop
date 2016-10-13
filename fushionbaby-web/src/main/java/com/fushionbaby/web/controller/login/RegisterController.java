package com.fushionbaby.web.controller.login;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberEmail;
import com.fushionbaby.member.model.MemberExtraInfo;
import com.fushionbaby.member.model.MemberTelephone;
import com.fushionbaby.member.service.MemberEmailService;
import com.fushionbaby.member.service.MemberExtraInfoService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.member.service.MemberTelephoneService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;

/***
 * 注册
 * @author King 索亮
 *
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
	
	/**记录日志*/
	private static final Log LOGGER = LogFactory.getLog(RegisterController.class);
	/**注入短信*/
	@Autowired
	private SmsService<Sms> smsService;
	
	/**注入会员*/
	@Autowired
	private MemberService<Member> memberService;
	
	/**注入会员手机号码*/
	@Autowired
	private MemberTelephoneService<MemberTelephone> memberTelephoneService;
	
	/**注入会员邮箱*/
	@Autowired
	private MemberEmailService<MemberEmail> memberEmailService;
	
	/***注入额外会员信息***/
	@Autowired
	private MemberExtraInfoService<MemberExtraInfo> memberExtraInfoService;
	
	/***
	 * 用户点击获取验证码
	 * @param telephone
	 * @return
	 */
	@ResponseBody
	@RequestMapping("registerSendMobileCode")
	public Object registerSendMobileCode(
			@RequestParam(value="telephone",defaultValue="")String telephone,
			HttpServletRequest request){
		try {
			//拿到cookie，判断用户是否进行注册操作
			String registerVerification = CookieUtil.getCookieValue(request, CookieConstant.REGISTER_COOKIE);
			if (ObjectUtils.equals(registerVerification, null)) {
				return Jsonp.error("请返回登录页重新注册");
			}
			
			Member member = memberService.findByPhone(telephone);
			if (member != null) {
				return Jsonp.error("此手机号已注册,您可以直接登录");
			}
			/**获取纯数字的验证码*/
			String registerRandomNum = RandomNumUtil.getRandom(RandomNumUtil.NUM, RandomNumUtil.REGISTER_USER_LENGTH);
			SessionCache.put(registerVerification, registerRandomNum);
		
			sendSmsRegisterCode(registerRandomNum, telephone);
			
			return Jsonp_data.success(registerVerification);
		} catch (Exception e) {
			LOGGER.debug(e.getMessage(),e);
			return Jsonp.error();
		}
	}
	/**
	 * web注册发送验证码
	 * @param registerRandomNum
	 * @param telephone
	 * @return
	 */
	private Object sendSmsRegisterCode(String registerRandomNum,String telephone){
		try {
			smsService.sendSmsRegisterCode(registerRandomNum, telephone,SourceConstant.WEB_CODE);
		} catch (RemoteException e) {
			LOGGER.debug(e.getMessage(),e);
			return Jsonp.error("短信服务器响应超时");
		}
		return null;
	}
	/***
	 * 用户点击同意注册 -- 手机
	 * @param register_mobile
	 * @param password
	 * @param register_smscode
	 * 				用户输入的验证码
	 * @param register_verification 
	 * 				放注册验证码的唯一标识码
	 * 
	 */
	@ResponseBody
	@RequestMapping("registerMobile")
	public Object registerMobile(
			@RequestParam(value="telephone",defaultValue="")String registerMobile,
			@RequestParam(value="password",defaultValue="")String password,
			@RequestParam(value="register_smscode",defaultValue="")String registerSmscode,
			@RequestParam(value="register_verification",defaultValue="")String registerVerification,
			HttpServletRequest request){
		try {
			
			//判断验证码是否正确 是否进行注册操作
			if (StringUtils.isEmpty(registerVerification)) {
				return Jsonp.error("请获取验证码");
			}
			Member member = null;
			member = memberService.findByPhone(registerMobile);
			if (member != null) {
				return Jsonp.error("此手机号已注册,您可以直接登录");
			}
			String registerCode = (String) SessionCache.get(registerVerification);
			if (!StringUtils.equals(registerCode, registerSmscode)) {
				return Jsonp.error("验证码输入有误");
			}
			
			member = memberService.addMember(registerMobile, password, SourceConstant.WEB_CODE, request);
			
			memberTelephoneService.addMemberPhone(member.getId(), registerMobile);

			memberExtraInfoService.addMemberExtraInfo(member.getId());
			
			return Jsonp.success();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage(),e);
			return Jsonp.error();
		}
	}
	
	/***
	 * 用户点击同意注册 -- 邮箱
	 * @param email
	 * @param password
	 */
	@ResponseBody
	@RequestMapping("registerEmail")
	public Object registerEmail(
			@RequestParam(value="email",defaultValue="")String email,
			@RequestParam(value="password",defaultValue="")String password,
			@RequestParam(value="register_emailcode",defaultValue="")String registerEmailcode,
			HttpServletRequest request){
		try {
			
			Member memEmail = memberService.findByEmail(email);
			if (ObjectUtils.notEqual(null, memEmail)) {
				return Jsonp.error("此邮箱号已注册,您可以直接登录");
			}
			//拿到cookie，判断用户是否进行注册操作
			String registerVerification = CookieUtil.getCookieValue(request, CookieConstant.REGISTER_COOKIE);
			if (ObjectUtils.equals(registerVerification, null)) {
				return Jsonp.error("请返回登录页重新注册");
			}
			
			String registerCode = (String)SessionCache.get(registerVerification);
			if(StringUtils.isNotBlank(registerCode)  && !StringUtils.equalsIgnoreCase(registerCode, registerEmailcode)){
				return Jsonp.error("验证码输入有误");//长度勿变动
			}
			
			Member member = memberService.addMember(email, password, SourceConstant.WEB_CODE, request);
			
			memberEmailService.addMemberEmail(member.getId(), email);

			memberExtraInfoService.addMemberExtraInfo(member.getId());
				
			return Jsonp.success();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage(),e);
			return Jsonp.error();
		}
		
	}

	/***
	 * 与第三方登陆用户绑定
	 * 
	 * @param email
	 * @param password
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("blindEmail")
	public Object blindEmail(
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "password", defaultValue = "") String password,
			@RequestParam(value = "openId", defaultValue = "") String openId,
			HttpServletRequest request) {
		try {

			Member memEmail = memberService.findByEmail(email);
			if (ObjectUtils.notEqual(null, memEmail)) {
				return Jsonp.error("此邮箱号已注册,您可以直接登录");
			} 

			/** 添加到会员表 */
			Member member = new Member();
			member.setLoginName(email);
			member.setEmail(email);
			member.setPassword(MD5Util.MD5(password));
			member.setSourceCode(SourceConstant.WEB_CODE);// 来源
			member.setOpenId(openId);
			member.setWalletMoney(BigDecimal.valueOf(0));
			member.setAvailableMoney(BigDecimal.valueOf(0));
			memberService.add(member);
			/** 添加到会员邮箱表 */
			MemberEmail memberEmail = new MemberEmail();
			memberEmail.setMemberId(member.getId());
			memberEmail.setEmail(email);
			memberEmailService.add(memberEmail);
			/** 添加到额外会员表 */
			MemberExtraInfo memberExtraInfo = new MemberExtraInfo();
			memberExtraInfo.setMemberId(member.getId());// 额外会员表添加一个id
			memberExtraInfoService.add(memberExtraInfo);
			return Jsonp.success();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage(),e);
			return Jsonp.error();
		}

	}

}
