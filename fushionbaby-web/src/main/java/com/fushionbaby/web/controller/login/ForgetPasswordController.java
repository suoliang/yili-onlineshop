package com.fushionbaby.web.controller.login;

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

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sms.model.Email;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.model.SmsTypeConfig;
import com.fushionbaby.sms.send.SendMailCode;
import com.fushionbaby.sms.service.EmailService;
import com.fushionbaby.sms.service.SmsService;
import com.fushionbaby.sms.service.SmsTypeConfigService;

/***
 * 忘记密码
 * @author King 索亮
 *
 */
@Controller
@RequestMapping("/forget")
public class ForgetPasswordController {
	/**注入日志*/
	private  static final Log logger = LogFactory.getLog(ForgetPasswordController.class);
	
	/**注入会员*/
	@Autowired
	private MemberService<Member> memberService;
	
	/**注入短信模板*/
	@Autowired
	private SmsTypeConfigService<SmsTypeConfig> smsTypeService;
	
	/**注入短信*/
	@Autowired
	private SmsService<Sms> smsService;
	
	/**注入邮件*/
	@Autowired
	private EmailService<Email> emailService;
	
	/***
	 * 忘记密码 -- 获取验证码操作
	 * @param phoneOrEmail
	 * @return
	 * responseCode 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("getForgetCode")
	public Object forgetPassword(
			@RequestParam(value="phoneOrEmail",defaultValue="")String phoneOrEmail,
			HttpServletRequest request){
		try {
			//拿到cookie，判断用户是否进行忘记密码操作
			String forgetpassword_verification = CookieUtil.getCookieValue(request, CookieConstant.FORGETPASSWORD_COOKIE);
			if (ObjectUtils.equals(forgetpassword_verification, null)) {
				return Jsonp.error("请返回登录页重新操作");
			}
			
			//如果输入的是手机号
			if (phoneOrEmail.matches("1\\d{10}")) {
				Member member = memberService.findByPhone(phoneOrEmail);
				if (ObjectUtils.equals(member, null)) {
					return Jsonp.error("对不起，该用户不存在");
				} else {//发送短信验证码
					
					/**获取纯数字的验证码*/
					String randomNum = RandomNumUtil.getRandom(RandomNumUtil.NUM,RandomNumUtil.UPDATE_PASSWARD_LENGTH);
					SessionCache.put(forgetpassword_verification, randomNum);
					//忘记密码发送验证码
					smsService.sendSmsForgetCode(randomNum, phoneOrEmail, String.valueOf(SourceConstant.WEB_CODE));
					
				}//else end
			}//最外层if end
			
			//如果匹配邮箱
			if (phoneOrEmail.matches("([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,}")) {
				Member member = memberService.findByEmail(phoneOrEmail);
				if (ObjectUtils.equals(member, null)) {
					return Jsonp.error("对不起，该用户不存在");
				}else {//发送邮件验证码
					
					/**获取纯数字的验证码*/
					String randomNum = RandomNumUtil.getRandom(RandomNumUtil.NUM,RandomNumUtil.UPDATE_PASSWARD_LENGTH);
					SessionCache.put(forgetpassword_verification, randomNum);
					//替换邮件的内容
					String email_content = WebConstant.EMAIL_CONTENT.replace(WebConstant.EMAIL_CODE, randomNum);
					Email email = new Email();//保存发送的邮件
					email.setReceiverEmail(phoneOrEmail);
					email.setMemberName(phoneOrEmail);
					email.setSourceCode(SourceConstant.WEB_CODE);//来源
					email.setEmailType(WebConstant.EMAIL_TYPE);//邮件类型
					email.setEmailContent(randomNum);//只存验证码
					emailService.add(email);
					SendMailCode.sendHtmlEmail(WebConstant.SENDER_EMAIL, phoneOrEmail, WebConstant.EMAIL_SUBJECT, email_content);//发送邮件
					
				}//内层if else 结束
			}//if 结束
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			return Jsonp.error();
		}
	}
	/**
	 * 跳转到确认密码页面
	 * @return
	 */
	@RequestMapping("toConfirmPassword")
	public String toConfirmPassword(){
		return "confirm-password";
	}
	
	/**
	 * 点击"下一步"
	 * @param phoneOrEmail
	 * @param input_code
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkVerfiyCode")
	public Object loginCheckVerfiyCode(
			@RequestParam(value="phoneOrEmail",defaultValue="")String phoneOrEmail,
			@RequestParam(value="input_code",defaultValue="")String input_code,
			HttpServletRequest request,
			HttpServletResponse response){
		try {
			if (StringUtils.isEmpty(phoneOrEmail)) {
				return Jsonp.error();
			}
			//拿到cookie，判断用户是否进行忘记密码操作
			String forgetpassword_verification = CookieUtil.getCookieValue(request, CookieConstant.FORGETPASSWORD_COOKIE);
			if (ObjectUtils.equals(forgetpassword_verification, null)) {
				return Jsonp.error("请返回登录页重新操作");
			}
			/**获得SessionCache里的验证码的值*/
			String random_verfiy_code = (String) SessionCache.get(forgetpassword_verification);
			if (StringUtils.isNotBlank(random_verfiy_code)) {
				if (!StringUtils.equals(input_code, random_verfiy_code)) {
					return Jsonp.error("验证码输入有误");
				}
			} else {
				return Jsonp.error("请获取验证码");
			}
			/**************传值*****************/
			String forgetpassword = RandomNumUtil.getCharacterAndNumber(WebConstant.UNIQUE_CODE);//获取存放用户忘记密码时输入的手机邮箱的标识码
			CookieUtil.setCookie(response, CookieConstant.FORGETPASSWORD_COOKIE_LOGINNAME, forgetpassword);
			SessionCache.put(forgetpassword, phoneOrEmail);//将用户输入的手机号码或邮箱放在SessionCache中
			
			return Jsonp.success();
		} catch (Exception e) {
			return Jsonp.error();
		}
	}
	
	
	/***
	 * 忘记密码—设置新密码  输入新密码，确认密码
	 * @param phoneOrEmail
	 * @param password
	 * @param forget_code
	 * @return
	 * response_code 0为成功，500为失败
	 */
	@ResponseBody
	@RequestMapping("setNewPassword")
	public Object loginSetNewPwd(
			@RequestParam(value ="password",defaultValue ="")String password,
			HttpServletRequest request){
		try {
			//拿到cookie，判断用户是否进行忘记密码操作
			String forgetpassword_verification = CookieUtil.getCookieValue(request, CookieConstant.FORGETPASSWORD_COOKIE);
			if (ObjectUtils.equals(forgetpassword_verification, null)) {
				return Jsonp.error("请返回登录页重新操作");
			}
			String phoneOrEmail_code = CookieUtil.getCookieValue(request, CookieConstant.FORGETPASSWORD_COOKIE_LOGINNAME);
			String phoneOrEmail = (String) SessionCache.get(phoneOrEmail_code);//用标识码获得用户输入的值
			
			//如果输入的是手机号
			if (phoneOrEmail.matches("1\\d{10}")) {
				Member member = memberService.findByPhone(phoneOrEmail);
				if (member != null) {
					member.setTelephone(phoneOrEmail);
					member.setPassword(MD5Util.MD5(password));
					memberService.updatePassword(phoneOrEmail, MD5Util.MD5(password));//手机号
				}
			}
			
			//如果匹配邮箱
			if (phoneOrEmail.matches("([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,}")) {
				Member member = memberService.findByEmail(phoneOrEmail);
				if (member != null) {
					member.setEmail(phoneOrEmail);
					member.setPassword(MD5Util.MD5(password));
					memberService.updatePasswordEmail(phoneOrEmail, MD5Util.MD5(password));//邮箱
				}
			}
		
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ForgetPasswordController忘记密码-设置新密码有误" + e);
			return Jsonp.error("对不起，设置新密码操作失败!");
		}
	}
	
}
