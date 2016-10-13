package com.fushionbaby.app.controller.login;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.EmailConstant;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.facade.biz.common.sms.SmsServiceFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sms.model.Email;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.send.SendMailCode;
import com.fushionbaby.sms.service.EmailService;
import com.fushionbaby.sms.service.SmsService;

/***
 * 忘记密码
 * @author xupeijun
 *
 */
@Controller
@RequestMapping("/login")
public class ForgetPasswordController {

	/**注入日志*/
	private static final Log LOGGER = LogFactory.getLog(ForgetPasswordController.class);
	
	/**注入会员*/
	@Autowired
	private MemberService<Member> memberService;
	
	/**注入短信*/
	@Autowired
	private SmsService<Sms> smsService;
	
	/**注入邮件*/
	@Autowired
	private EmailService<Email> emailService;
	
	@Autowired
	private SmsServiceFacade smsServiceFacade;

	/***
	 * 忘记密码—请求验证码
	 * @param phone  手机号
	 * @param sourceCode 来源
	 * @return
	 * response_code 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("getSmsCode")
	public Object loginGetSmsCode(
			@RequestParam(value="phone",defaultValue="")String phone,
			@RequestParam(value="forgetCode",defaultValue="")String forgetCode,
			@RequestParam(value="sourceCode",defaultValue="")String sourceCode,
			@RequestParam(value="isPhone",defaultValue="")String isPhone){
		try {
			if (CheckIsEmpty.isEmpty(phone, forgetCode, sourceCode, isPhone)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			Member member = memberService.findByLoginName(phone);
			if (member == null) {
				return Jsonp.error("对不起，该用户不存在!");
			}
			if (smsServiceFacade.getNumByPhone(phone)) {
				return Jsonp.smsNumberLimit();
			}
			/**获取纯数字的验证码*/
			String randomNum = RandomNumUtil.getRandom(RandomNumUtil.NUM,RandomNumUtil.UPDATE_PASSWARD_LENGTH);
			/**存放在DataCache中*/
			DataCache.put(forgetCode, randomNum);
			
			if (StringUtils.equalsIgnoreCase(isPhone, CommonConstant.YES)) {
				/**忘记密码发送短信验证码*/
				smsService.sendSmsCode(randomNum, phone, sourceCode,SmsConstant.SMS_TYPE_FORGET_ID);
				return Jsonp.success();
			}
			
			Email email = new Email();//保存发送的邮件
			email.setReceiverEmail(phone);
			email.setMemberName(phone);
			email.setSourceCode(sourceCode);//来源
			email.setEmailType(EmailConstant.EMAIL_TYPE_FORGET);//邮件类型
			email.setEmailContent(randomNum);//只存验证码
			emailService.add(email);
			/**忘记密码发送邮箱验证码*/
			SendMailCode.sendHtmlEmail(EmailConstant.SENDER_EMAIL, phone, EmailConstant.EMAIL_SUBJECT, EmailConstant.EMAIL_CONTENT.replace(EmailConstant.EMAIL_CODE, randomNum));
			
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("LoginController忘记密码-请求验证码有误APP" + e);
			return Jsonp.error();
		}
		
	}

	/***
	 * 忘记密码—效验验证码
	 * @param phone    手机号
	 * @param smsCode 验证码
	 * @return
	 * response_code 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("checkVerfiyCode")
	public Object loginCheckVerfiyCode(
			@RequestParam(value="phone",defaultValue="")String phone,
			@RequestParam(value="smsCode",defaultValue="")String smsCode,
			@RequestParam(value="forgetCode",defaultValue="")String forgetCode,
			@RequestParam(value="isPhone",defaultValue="")String isPhone){
		try {
			if (CheckIsEmpty.isEmpty(phone, smsCode, forgetCode, isPhone)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			
			/**获得DataCache里的验证码的值*/
			String random_sms_code = (String) DataCache.get(forgetCode);
			
			if (!StringUtils.equals(smsCode, random_sms_code)) {
				return Jsonp.error("验证码输入有误!");
			}
			
			return Jsonp.success();
		} catch (Exception e) {
			LOGGER.error("LoginController忘记密码-校验验证码有误APP" + e);
			return Jsonp.error();
		}
	}

	/***
	 * 忘记密码—设置新密码
	 * @param phone
	 * @param password
	 * @return
	 * response_code 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("setNewPwd")
	public Object loginSetNewPwd(
			@RequestParam(value="phone",defaultValue="")String phone,
			@RequestParam(value ="password",defaultValue ="")String password,
			@RequestParam(value="forgetCode",defaultValue="")String forgetCode,
			@RequestParam(value="isPhone",defaultValue="")String isPhone){
		try {
			if (CheckIsEmpty.isEmpty(forgetCode, phone, password, isPhone)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			Member member = memberService.findByLoginName(phone);
			if (member != null) {
				memberService.updatePassword(phone, MD5Util.MD5(password));
			}
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("LoginController忘记密码-设置新密码有误APP" + e);
			return Jsonp.error("出错了，设置新密码有误!");
		}
	}
	
}
