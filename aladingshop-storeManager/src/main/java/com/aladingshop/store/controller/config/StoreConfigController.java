package com.aladingshop.store.controller.config;


import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aladingshop.store.auth.model.StoreAuthUser;
import com.aladingshop.store.config.Global;
import com.aladingshop.store.controller.BaseController;
import com.aladingshop.store.model.Store;
import com.aladingshop.store.model.StoreConfig;
import com.aladingshop.store.service.StoreConfigService;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.common.constants.SessionKeyConstant;
/**
 * 门店配置信息
 * @author chenyingtao
 *
 *
 */
@Controller
@RequestMapping("storeConfig")
public class StoreConfigController extends BaseController {

	private static final Log LOGGER = LogFactory.getLog(StoreConfigController.class);
	@Autowired
	private StoreConfigService<StoreConfig> storeConfigService;
	@Autowired
	private StoreService<Store> storeService;
	
	@RequestMapping("insert")
	public String insertConfig(StoreConfig storeConfig,HttpSession session,ModelMap model){
		
		try {
			StoreAuthUser storeAuthUser = (StoreAuthUser) session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
			if(storeAuthUser == null){
				return "models/authorization/login";
			}
			String storeCode = storeAuthUser.getStoreCode();
			storeConfig.setStoreCode(storeCode);
			
			StoreConfig ifExitStoreConfig = storeConfigService.findByStoreCode(storeCode);
			if(ifExitStoreConfig != null){
				storeConfig.setId(ifExitStoreConfig.getId());
				storeConfigService.update(storeConfig);
			}else{
				storeConfigService.add(storeConfig);
			}
			
			
			model.addAttribute("storeConfig", storeConfig);
			
			model.addAttribute("message", "添加门店配置成功");
		} catch (Exception e) {
			LOGGER.error("添加门店配置失败", e);
			return "";
		}
		
		return "redirect:"+Global.getAdminPath()+"storeConfig/storeConfigInfo";
		
	}
	
	@RequestMapping("storeConfigInfo")
	public String storeConfigInfo(StoreConfig storeConfig,HttpSession session,ModelMap model){
		
		try {
			StoreAuthUser storeAuthUser = (StoreAuthUser) session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
			if(storeAuthUser == null){
				return "models/authorization/login";
			}
			String storeCode = storeAuthUser.getStoreCode();
			
			storeConfig = storeConfigService.findByStoreCode(storeCode);
			Store store = storeService.findByCode(storeCode);
			
			model.addAttribute("storeName", store.getName());
			model.addAttribute("storeConfig", storeConfig);
			model.addAttribute("storeCode", storeCode);
		} catch (Exception e) {
			LOGGER.error("查询门店配置失败", e);
			return "";
		}
		
		return "models/config/storeConfigInfo";
		
	}
	
	@RequestMapping("goAddStoreConfig/{time}")
	public String goAddStoreConfig(){
		
		return "models/config/addStoreConfig";
	}
	
	@RequestMapping("goUpdateStoreConfig/{storeCode}/{time}")
	public String goUpdateStoreConfig(@PathVariable(value="storeCode")String storeCode,
			ModelMap model){
		
		StoreConfig storeConfig = storeConfigService.findByStoreCode(storeCode);
		model.addAttribute("storeConfig", storeConfig);
		
		return "models/config/addStoreConfig";
	}
	
	
	
}
