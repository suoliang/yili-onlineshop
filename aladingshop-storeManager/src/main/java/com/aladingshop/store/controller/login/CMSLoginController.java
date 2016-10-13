package com.aladingshop.store.controller.login;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.store.auth.model.StoreAuthPrivilege;
import com.aladingshop.store.auth.model.StoreAuthUser;
import com.aladingshop.store.auth.service.StoreAuthUserService;
import com.aladingshop.store.config.Global;
import com.aladingshop.store.controller.BaseController;
import com.aladingshop.store.model.Store;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SessionKeyConstant;
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
	private StoreAuthUserService<StoreAuthUser> authUserService;
	
	@Autowired
	private StoreService<Store> storeService;
	/** 登录日志 */
	@Autowired
	private LogCmsLoginService<LogCmsLogin> logCmsLoginService;
    
	private static final String PRE_MAIN_URL="redirect:"+Global.getAdminPath();
	/**登陆页面*/
	private static final String INDEX_URL="models/authorization";
	

	private static final Log LOGGER=LogFactory.getLog(CMSLoginController.class);
	
	@RequestMapping(value="/index")
	public String Index(HttpServletRequest request){
		request.getSession().removeAttribute(SessionKeyConstant.CMS_AUTH_USER);
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
			@RequestParam(value = "storeNumber", defaultValue = "") String storeNumber,
			HttpServletRequest request,HttpSession session, Map<String, Object> map,RedirectAttributes redirectAttributes) {
		List<Map<StoreAuthPrivilege, List<StoreAuthPrivilege>>> menuPrivilegeList = new ArrayList<Map<StoreAuthPrivilege, List<StoreAuthPrivilege>>>();
		try {
			Store store=this.storeService.findByNumber(storeNumber);
			if(ObjectUtils.equals(null, store)){
				addMessage(redirectAttributes, "登录失败，该门店不存在！");
				return PRE_MAIN_URL+"/login/index";
			}else if(CommonConstant.YES.equals(store.getIsDeleted())){
				addMessage(redirectAttributes, "登录失败，该门店已禁用！");
				return PRE_MAIN_URL+"/login/index";
			}
			StoreAuthUser auUser = this.authUserService.findStoreAuthUserByUserNameAndStoreNumber(username,storeNumber);
			String ipAddress=GetIpAddress.getIpAddr(request);
			LogCmsLogin cms = new LogCmsLogin();
			if (auUser != null && password.equals(auUser.getPassword())&&storeNumber.equals(auUser.getStoreNumber())) {
				session.setAttribute(SessionKeyConstant.CMS_AUTH_USER, auUser);
				if (this.authUserService.isSystemUser(auUser.getId()) != null) {
					menuPrivilegeList = this.authUserService.loginAsSystem();
					LOGGER.info("后台登陆会员以系统用户登录");
				} else {
					menuPrivilegeList = this.authUserService.loginAsNormalUser(auUser.getId());
					LOGGER.info("后台登陆会员以普通用户身份登录");
					
				}
				
				map.put("menuPrivilegeList", menuPrivilegeList);
				map.put("userName", auUser.getName());
				map.put("storeName", store.getName());
				
				
				if(auUser.getName() != null && auUser.getName() != ""){
					cms.setIpAddress(ipAddress);
					cms.setLoginName(auUser.getName());
					cms.setLoginStatus(CommonConstant.YES);
					cms.setStoreCode(auUser.getStoreCode());
					this.logCmsLoginService.addLoginMessage(cms);
				}
				LOGGER.info("后台登陆完成");
				return INDEX_URL+"/main";
			} else {
				
				if(auUser.getName() != null && auUser.getName() != ""){
					cms.setIpAddress(ipAddress);
					cms.setLoginName(auUser.getName());
					cms.setLoginStatus(CommonConstant.YES);
					cms.setStoreCode(auUser.getStoreCode());
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
