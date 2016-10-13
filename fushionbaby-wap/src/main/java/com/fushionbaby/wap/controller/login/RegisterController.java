package com.fushionbaby.wap.controller.login;

import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberExtraInfo;
import com.fushionbaby.member.model.MemberTelephone;
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
	
	/**注入短信*/
	@Autowired
	private SmsService<Sms> smsService;
	
	/**注入会员*/
	@Autowired
	private MemberService<Member> memberService;
	
	/**注入会员手机号码*/
	@Autowired
	private MemberTelephoneService<MemberTelephone> memberTelephoneService;
	
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
			String register_verification = CookieUtil.getCookieValue(request, CookieConstant.REGISTER_COOKIE);
			if (ObjectUtils.equals(register_verification, null)) {
				return Jsonp.error("请返回登录页重新注册");
			}
			
			Member member = memberService.findByPhone(telephone);
			if (member != null) {
				return Jsonp.error("此手机号已注册,您可以直接登录");
			}
			/**获取纯数字的验证码*/
			String registerRandomNum = RandomNumUtil.getRandom(RandomNumUtil.NUM, RandomNumUtil.REGISTER_USER_LENGTH);
			SessionCache.put(register_verification, registerRandomNum);
		
			sendSmsRegisterCode(registerRandomNum, telephone);
			
			return Jsonp_data.success(register_verification);
		} catch (Exception e) {
			e.printStackTrace();
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
			smsService.sendSmsRegisterCode(registerRandomNum, telephone,SourceConstant.WAP_CODE);
		} catch (RemoteException e) {
			e.printStackTrace();
			return Jsonp.error("短信服务器响应超时");
		}
		return null;
	}
	/***
	 * 用户点击同意注册 -- 手机
	 * @param telephone
	 * @param password
	 * @param userInputSmscode
	 * 				用户输入的验证码
	 * @param registerVerification 
	 * 				放注册验证码的唯一标识码
	 * 
	 */
	@ResponseBody
	@RequestMapping("registerMobile")
	public Object registerMobile(
			@RequestParam(value="telephone",defaultValue="")String telephone,
			@RequestParam(value="password",defaultValue="")String password,
			@RequestParam(value="userInputSmscode",defaultValue="")String userInputSmscode,
			@RequestParam(value="registerVerification",defaultValue="")String registerVerification,
			HttpServletRequest request){
		try {
			
			//判断验证码是否正确 是否进行注册操作
			if (StringUtils.isEmpty(registerVerification)) {
				return Jsonp.error("请获取验证码");
			}
			Member member = null;
			member = memberService.findByPhone(telephone);
			if (member != null) {
				return Jsonp.error("此手机号已注册,您可以直接登录");
			}
			String register_code = (String) SessionCache.get(registerVerification);
			if (!StringUtils.equals(register_code, userInputSmscode)) {
				return Jsonp.error("验证码输入有误");
			}
			
			member = memberService.addMember(telephone, password, SourceConstant.WAP_CODE, request);
			
			memberTelephoneService.addMemberPhone(member.getId(), telephone);

			memberExtraInfoService.addMemberExtraInfo(member.getId());
			
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Jsonp.error();
		}
	}
	
}
