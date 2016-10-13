package com.fushionbaby.app.controller.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.facade.biz.common.coupon.MemberCouponFacade;
import com.fushionbaby.facade.biz.common.member.MemberFacade;
import com.fushionbaby.facade.biz.common.sms.SmsServiceFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberDevice;
import com.fushionbaby.member.model.MemberEmail;
import com.fushionbaby.member.model.MemberExtraInfo;
import com.fushionbaby.member.model.MemberTelephone;
import com.fushionbaby.member.service.MemberDeviceService;
import com.fushionbaby.member.service.MemberEmailService;
import com.fushionbaby.member.service.MemberExtraInfoService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.member.service.MemberTelephoneService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/***
 * 注册
 * @author King 索亮
 *
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
	/**注入日志*/
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
	@Autowired
	private SmsServiceFacade smsServiceFacade;
	@Autowired
	private MemberCouponFacade memberCouponFacade;
	@Autowired
	private MemberFacade memberFacade;
	
	/**会员设备表*/
	@Autowired
	private MemberDeviceService<MemberDevice> memberDeviceService;
	@Autowired
	private SysmgrGlobalConfigService globalConfigService;
	/***
	 * 注册--请求短信验证码
	 * @param phone   手机号
	 * @param sourceCode  来源
	 * @param registerCode 注册标识码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getSmsCode")
	public Object registerGetSmsCode(
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac){
		try {
			LOGGER.info("注册获取验证码接口--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String phone = json.getAsJsonObject().get("phone").getAsString();
			String sourceCode = json.getAsJsonObject().get("sourceCode").getAsString();
			String registerCode = json.getAsJsonObject().get("registerCode").getAsString();
			if (CheckIsEmpty.isEmpty(phone, registerCode, sourceCode)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			
			if (memberService.findByLoginName(phone) != null) {
				return Jsonp.error("此手机号已注册,您可以直接登录!");
			}
			if (smsServiceFacade.getNumByPhone(phone)) {
				return Jsonp.smsNumberLimit();
			}
			/**获取纯数字的验证码*/
			String registerRandomNum = RandomNumUtil.getRandom(RandomNumUtil.NUM, RandomNumUtil.REGISTER_USER_LENGTH);
			DataCache.put(registerCode, registerRandomNum);
			try {
				//注册发送的短信验证码
				smsService.sendSmsCode(registerRandomNum, phone, sourceCode,SmsConstant.SMS_TYPE_REGISTER_ID);
			} catch (Exception e) {
				e.printStackTrace();
				return Jsonp.error("短信服务器响应超时");
			}
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("App注册getSmsCode请求出错" + e);
			return Jsonp.error();
		}
	}
	
	/***
	 * 注册 -- 校验
	 * @param phone     手机号
	 * @param smsCode	短信验证码
	 * @param password	密码
	 * @param registerCode 注册标识码
	 * @param sourceCode 来源
	 * @return
	 * response_code 0为成功，500为失败
	 * 
	 * * @param signID 唯一标识码
	 */
	@ResponseBody
	@RequestMapping("commit")
	public Object registerCommit(
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac,
			HttpServletRequest request){
		try {
			LOGGER.info("注册手机提交接口--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String phone = json.getAsJsonObject().get("phone").getAsString();
			String smsCode = json.getAsJsonObject().get("smsCode").getAsString();
			String password = json.getAsJsonObject().get("password").getAsString();
			String registerCode = json.getAsJsonObject().get("registerCode").getAsString();
			String sourceCode = json.getAsJsonObject().get("sourceCode").getAsString();
			/**设备标志  */
			String signId = json.getAsJsonObject().get("signID").getAsString();
			
			if (CheckIsEmpty.isEmpty(phone, smsCode, password, registerCode, sourceCode)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			if (memberService.findByLoginName(phone) != null) {
				return Jsonp.error("此手机号已注册,您可以直接登录!");
			}
			/**获得DataCache里的验证码的值*/
			String randomSmsCode = (String) DataCache.get(registerCode);
			if (!StringUtils.equals(smsCode,randomSmsCode)) {
				return Jsonp.error("验证码输入有误!");
			}
			/**注册设备次数限制*/
			String signMessage = memberDeviceService.getMemDevRegisterInfo(signId);
			if(StringUtils.isNotBlank(signMessage)){
				return Jsonp.error(signMessage);
			}
			
			/**添加到设备记录表*/
			MemberDevice memberDevice = new MemberDevice();
			memberDevice.setPhone(phone);
			memberDevice.setMac(signId);
			memberDevice.setSourceCode(sourceCode);
			memberDevice.setType("1");
			memberDeviceService.add(memberDevice);
			
			
			/**添加到会员表*/
			Member member = memberService.addMember(phone,password,sourceCode,request);
			member.setSignId(signId);
			memberService.update(member);
			
			/**添加到会员手机表*/
			memberTelephoneService.addMemberPhone(member.getId(), phone);
			/**添加到额外会员表*/
			memberExtraInfoService.addMemberExtraInfo(member.getId());
			LOGGER.info("新会员注册 "+member.getId()+"赠送优惠券十元活动  begin");
			memberCouponFacade.registerSendCoupon(member.getId());
			LOGGER.info("新会员注册"+member.getId()+" 赠送优惠券十元活动  end");
			/**销毁注册标识码*/
			DataCache.remove(registerCode);
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("App注册commit请求出错" + e);
			return Jsonp.error();
		}
	}
	/***
	 * 邮箱注册
	 * @param email
	 * @param password
	 * @param registerCode
	 * @param sourceCode
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("emailCommit")
	public Object registerEmailCommit(
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac,
			HttpServletRequest request){
		try {
			LOGGER.info("注册邮箱提交接口--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String email = json.getAsJsonObject().get("email").getAsString();
			String password = json.getAsJsonObject().get("password").getAsString();
			String registerCode = json.getAsJsonObject().get("registerCode").getAsString();
			String sourceCode = json.getAsJsonObject().get("sourceCode").getAsString();
			if (CheckIsEmpty.isEmpty(email, password, registerCode, sourceCode)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			
			if (memberService.findByLoginName(email) != null) {
				return Jsonp.error("此邮箱已注册,您可以直接登录!");
			}
			/**添加到会员表*/
			Member member = memberService.addMember(email, password, sourceCode,request);
			/**添加到会员邮箱表*/
			memberEmailService.addMemberEmail(member.getId(), email);
			/**添加到额外会员表*/
			memberExtraInfoService.addMemberExtraInfo(member.getId());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("App注册emailCommit请求出错" + e);
			return Jsonp.error();
		}
		return Jsonp.success();
	}

}
