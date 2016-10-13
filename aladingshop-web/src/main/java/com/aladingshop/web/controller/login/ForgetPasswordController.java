package com.aladingshop.web.controller.login;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.facade.biz.common.member.MemberFacade;
import com.fushionbaby.facade.biz.common.sms.EmailServiceFacade;
import com.fushionbaby.facade.biz.common.sms.SmsServiceFacade;
/***
 * 
 * @description 类描述... 忘记密码
 * @author 徐培峻
 * @date 2015年8月14日下午12:32:20
 */
@Controller
@RequestMapping("/forget")
public class ForgetPasswordController {
	/**注入日志*/
	private  static final Log Forget_Password_Log = LogFactory.getLog(ForgetPasswordController.class);
	
	@Autowired
	private MemberFacade memberFacade;
	/**注入短信*/
	@Autowired
	private SmsServiceFacade smsServiceFacade;
	
	/**注入邮件*/
	@Autowired
	private EmailServiceFacade emailServiceFacade;
	
	@RequestMapping("/toResetPassword")
	public String toResetPassword(){
		return "login/resetStep1";
	}
	
	@RequestMapping("/toResetPassword2")
	public String toResetPassword2(@RequestParam("userName") String userName,Model model){
		model.addAttribute("userName", userName);
		return "login/resetStep2";
		
	}
	@RequestMapping("/resetFinish")
	public String resetFinish(@RequestParam("userName") String userName,@RequestParam("password") String password){
	  try {
		   memberFacade.updatePassword(userName,MD5Util.MD5(password));
		} catch (Exception e) {
			Forget_Password_Log.error("web:ForgetPasswordController.java 重置密码异常", e);
		} 
		return "login/resetStep3";
		
	}
}
