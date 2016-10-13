package com.fushionbaby.cms.controller.login;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fushionbaby.auth.model.AuthPrivilege;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.auth.service.AuthUserService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SessionKeyConstant;
import com.fushionbaby.common.constants.store.StoreConstant;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.log.model.LogCmsLogin;
import com.fushionbaby.log.service.LogCmsLoginService;

/***
 * 
 * @author xupeijun
 *   
 *   后台登陆
 *
 */
@Controller
@RequestMapping("/login")
public class CMSLoginController extends BaseController{
	
	/** 用户*/
	@Autowired
	private AuthUserService<AuthUser> authUserService;
	
	/** 登录日志 */
	@Autowired
	private LogCmsLoginService<LogCmsLogin> logCmsLoginService;
    
	private static final String PRE_MAIN_URL="redirect:"+Global.getAdminPath();
	/**登陆页面*/
	private static final String INDEX_URL="models/authorization";
	
//	@RequestMapping(value="/login")
//	public String Login(@RequestParam(value="userName",defaultValue="") String userName,
//			@RequestParam(value="password",defaultValue="") String password,HttpSession session,
//			HttpServletRequest request, RedirectAttributes redirectAttributes){
//		AuthUser auUser = this.authUserService.findAuthUserByUserName(userName);
//		String ipAddress=GetIpAddress.getIpAddr(request);
//		if (auUser != null && password.equals(auUser.getPassword())) {
//			session.setMaxInactiveInterval(2000);
//			session.setAttribute(SessionKeyConstant.CMS_AUTH_USER, auUser);
//			this.logCmsLoginService.addLoginMessage(auUser.getLoginName(),CommonConstant.YES,ipAddress);
//			addMessage(redirectAttributes, "欢迎您！"+userName);
//			return PRE_MAIN_URL+"/role/roleList";
//		} else {
//			this.logCmsLoginService.addLoginMessage(userName,CommonConstant.NO,ipAddress);
//			addMessage(redirectAttributes, "登录失败，密码错误或该用户不存在");
//		    return PRE_MAIN_URL+"/login/index";
//		}
//	}
//	

	@RequestMapping(value="/index")
	public String Index(HttpServletRequest request){
		//request.getSession().removeAttribute(SessionKeyConstant.CMS_AUTH_USER);
		return INDEX_URL+"/login";
	}
	
	@RequestMapping(value="/exit")
	public String LoginOut(HttpServletRequest request){
		request.getSession().removeAttribute(SessionKeyConstant.CMS_AUTH_USER);
		return INDEX_URL+"/login";
	}
    @RequestMapping("main")
	public String MainLogin(
			@RequestParam(value = "userName", defaultValue = "") String username,
			@RequestParam(value = "password", defaultValue = "") String password,
			HttpServletRequest request,HttpSession session, Map<String, Object> map,RedirectAttributes redirectAttributes) {
		List<Map<AuthPrivilege, List<AuthPrivilege>>> menuPrivilegeList = new ArrayList<Map<AuthPrivilege, List<AuthPrivilege>>>();
		try {
			AuthUser auUser = this.authUserService.findAuthUserByUserName(username);
			String ipAddress=GetIpAddress.getIpAddr(request);
			LogCmsLogin cms = new LogCmsLogin();
			if (auUser != null && password.equals(auUser.getPassword())) {
				session.setAttribute(SessionKeyConstant.CMS_AUTH_USER, auUser);
				if (this.authUserService.isSystemUser(auUser.getId()) != null) {
					menuPrivilegeList = this.authUserService.loginAsSystem();
				} else {
					menuPrivilegeList = this.authUserService.loginAsNormalUser(auUser.getId());
				}
				map.put("menuPrivilegeList", menuPrivilegeList);
				map.put("userName", auUser.getLoginName());
				if(auUser.getLoginName() != null && auUser.getLoginName() != ""){
					cms.setIpAddress(ipAddress);
					cms.setLoginName(auUser.getLoginName());
					cms.setLoginStatus(CommonConstant.YES);
					cms.setStoreCode(StoreConstant.STORE_CODE);
					this.logCmsLoginService.addLoginMessage(cms);
				}
				return INDEX_URL+"/main";
			} else {
				
				if(auUser.getLoginName() != null && auUser.getLoginName() != ""){
					cms.setIpAddress(ipAddress);
					cms.setLoginName(auUser.getLoginName());
					cms.setLoginStatus(CommonConstant.NO);
					cms.setStoreCode(StoreConstant.STORE_CODE);
					this.logCmsLoginService.addLoginMessage(cms);
				}
				
				addMessage(redirectAttributes, "登录失败，密码错误或该用户不存在");
				return PRE_MAIN_URL+"/login/index";
			}
		} catch (Exception e) {
			logger.error("用户登录失败", e);
			addMessage(redirectAttributes, "登录失败，密码错误或该用户不存在");
			return PRE_MAIN_URL+"/login/index";
		}

	}
	

	
}
