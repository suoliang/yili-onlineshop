package com.fushionbaby.cms.controller.log;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;








import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aladingshop.store.model.Store;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.auth.service.AuthUserService;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.LogCmsLoginDto;
import com.fushionbaby.common.constants.store.StoreConstant;
import com.fushionbaby.common.util.BasePagination;
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
@RequestMapping("/log")
public class LogCMSLoginController extends BaseController{
	/** 记录日志 */
	private static final Log logger = LogFactory.getLog(LogCmsLogin.class);
	/** 用户*/
	@Autowired
	private AuthUserService<AuthUser> authUserService;
	
	/** 登录日志 */
	@Autowired
	private LogCmsLoginService<LogCmsLogin> logCmsLoginService;
	@Autowired
	private StoreService<Store> storeService;
    
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/logLoginList")
	public String Login(BasePagination page,LogCmsLoginDto logCmsLoginDto, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("loginName", logCmsLoginDto.getLoginName());
			params.put("loginStatus", logCmsLoginDto.getLoginStatus());
			params.put("loginTimeFrom", logCmsLoginDto.getLoginTimeFrom());
			params.put("loginTimeTo", logCmsLoginDto.getLoginTimeTo());
			page.setParams(params);
			page = logCmsLoginService.getListPage(page);
			List<LogCmsLogin> logCmsLoginList = (List<LogCmsLogin>)page.getResult();
			List<LogCmsLoginDto> list = new ArrayList<LogCmsLoginDto>();
			
			for (LogCmsLogin logCmsLogin : logCmsLoginList) {
				
				LogCmsLoginDto cmsDto = new LogCmsLoginDto();
				
				if(StoreConstant.STORE_CODE.equals(logCmsLogin.getStoreCode())||StringUtils.isBlank(logCmsLogin.getStoreCode())){
					cmsDto.setStoreName("CMS用户");
				}else{
					Store store = storeService.findByCode(logCmsLogin.getStoreCode());
					cmsDto.setStoreName(store==null?"":store.getName());
				}
				cmsDto.setId(logCmsLogin.getId());
				cmsDto.setIpAddress(logCmsLogin.getIpAddress());
				cmsDto.setLoginName(logCmsLogin.getLoginName());
				cmsDto.setLoginStatus(logCmsLogin.getLoginStatus());
				cmsDto.setLoginTime(logCmsLogin.getLoginTime());
				list.add(cmsDto);
				
			}
			page = logCmsLoginService.getListPage(page);
			model.addAttribute("list", list);
			model.addAttribute("page", page);
			model.addAttribute("logCmsLoginDto", logCmsLoginDto);
			
		} catch (Exception e) {
			logger.error("后台用户列表加载失败", e);
		}
		return "models/log/logLoginList";
	}
	

	
}
