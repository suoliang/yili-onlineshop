package com.aladingshop.wap.controller.login;

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

import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.coupon.MemberCouponFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberExtraInfo;
import com.fushionbaby.member.model.MemberTelephone;
import com.fushionbaby.member.service.MemberExtraInfoService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.member.service.MemberTelephoneService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;

/***
 * 
 * @description 类描述...用户注册
 * @author 徐培峻
 * @date 2015年8月10日下午4:01:36
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

	/** 记录日志 */
	private static final Log LOGGER = LogFactory.getLog(RegisterController.class);

	@Autowired
	private MemberCouponFacade memberCouponFacade;
	/***
	 * 用户点击获取验证码
	 * @param userPhone
	 * @return
	 */
	@ResponseBody
	@RequestMapping("sendCode")
	public Object registerSendMobileCode(
			@RequestParam(value="userPhone",defaultValue="")String userPhone,
			HttpServletRequest request){
		try {
			//拿到cookie，判断用户是否进行注册操作
			String registerCode = CookieUtil.getCookieValue(request, CookieConstant.REGISTER_COOKIE);
			if (ObjectUtils.equals(registerCode, null)) {
				return Jsonp.error("请返回登录页重新注册");
			}
			
			Member member = memberService.findByLoginName(userPhone);
			if (member != null) {
				return Jsonp.error("此手机号已注册,您可以直接登录");
			}
			/**获取纯数字的验证码*/
			String registerRandomNum = RandomNumUtil.getRandom(RandomNumUtil.NUM, RandomNumUtil.REGISTER_USER_LENGTH);
			DataCache.put(registerCode, registerRandomNum);
		
			sendSmsRegisterCode(registerRandomNum, userPhone);
			
			return Jsonp_data.success(registerCode);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取验证码出错", e);
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
			smsService.sendSmsCode(registerRandomNum, telephone, SourceConstant.WAP_CODE, SmsConstant.SMS_TYPE_REGISTER_ID);
		} catch (RemoteException e) {
			e.printStackTrace();
			return Jsonp.error("短信服务器响应超时");
		}
		return null;
	}
	
	/***
	 * 手机注册
	 * @param userPhone
	 * @param key 用户输入的验证码
	 * @param password
	 * @param registerNum 放注册验证码的唯一标识码
	 * 
	 */
	@ResponseBody
	@RequestMapping("registerMobile")
	public Object registerMobile(
			@RequestParam(value="userPhone",defaultValue="")String userPhone,
			@RequestParam(value="key",defaultValue="")String key,
			@RequestParam(value="password",defaultValue="")String password,
			@RequestParam(value="registerNum",defaultValue="")String registerNum,
			HttpServletRequest request){
		try {
			//判断验证码是否正确 是否进行注册操作
			if (StringUtils.isEmpty(registerNum)) {
				return Jsonp.error("请获取验证码");
			}
			Member member = null;
			member = memberService.findByLoginName(userPhone);
			if (member != null) {
				return Jsonp.error("此手机号已注册,您可以直接登录");
			}
			String register_code = (String) DataCache.get(registerNum);
			if (!StringUtils.equals(register_code, key)) {
				return Jsonp.error("验证码输入有误");
			}
			
			member = memberService.addMember(userPhone, password, SourceConstant.WAP_CODE, request);
			
			memberTelephoneService.addMemberPhone(member.getId(), userPhone);

			memberExtraInfoService.addMemberExtraInfo(member.getId());
			
			LOGGER.info("新会员注册 "+member.getId()+"赠送优惠券十元活动  begin");
			memberCouponFacade.registerSendCoupon(member.getId());
			LOGGER.info("新会员注册"+member.getId()+" 赠送优惠券十元活动  end");
			
			
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("注册失败", e);
			return Jsonp.error("注册失败");
		}
	}
	
	/*** 点击立即注册时跳转到注册页面 */
	@RequestMapping("/toBonus")
	public String toBonus() {
		return "login/bonus";
	}
	
}
