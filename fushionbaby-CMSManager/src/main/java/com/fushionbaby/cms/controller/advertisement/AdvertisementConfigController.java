package com.fushionbaby.cms.controller.advertisement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrAdvertisementConfig;
import com.fushionbaby.config.model.SysmgrSourceConfig;
import com.fushionbaby.config.service.SysmgrAdvertisementConfigService;
import com.fushionbaby.config.service.SysmgrSourceConfigService;

/**
 * 
 * @author xupeijun
 * 
 */
@Controller
@RequestMapping("/adConfig")
public class AdvertisementConfigController extends BaseController {
	@Autowired
	private SysmgrAdvertisementConfigService<SysmgrAdvertisementConfig> advertisementConfigService;

	/** 广告来源 */
	@Autowired
	private SysmgrSourceConfigService<SysmgrSourceConfig> sysmgrSourceService;

	private static final Log logg = LogFactory.getLog(AdvertisementConfigController.class);

	
	/** 页面*/
    private static final String PRE_="models/advertisement/";
	
    /**列表页*/
	private static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/adConfig/adConfigList";
	
	/***
	 * 广告配置的列表
	 * 
	 * @param adName
	 * @param adCode
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("adConfigList")
	public String findAll(
			@RequestParam(value = "adName", defaultValue = "") String adName,
			@RequestParam(value = "adCode", defaultValue = "") String adCode,
			BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("adCode", adCode.trim());
			params.put("adName", adName.trim());
			page.setParams(params);
			page = this.advertisementConfigService.getListPage(page);
			List<SysmgrAdvertisementConfig> adConfigList = (List<SysmgrAdvertisementConfig>) page.getResult();
			List<SysmgrSourceConfig> sysSourceList = this.sysmgrSourceService.findAll();
			model.addAttribute("sysSourceList", sysSourceList);
			model.addAttribute("adConfigList", adConfigList);
			model.addAttribute("page", page);
			model.addAttribute("adName", adName);
			model.addAttribute("adCode", adCode);
			return PRE_+"adConfigList";
		} catch (Exception e) {
			logg.error("折扣商品查询出错", e);
			return "";
		}
	}

	/***
	 * 
	 * @param id
	 * @param isUse
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("changeIsUse/{id}/{isUse}/{time}")
	public String ChangeIsUse(@PathVariable("id") String id,@PathVariable("isUse") String isUse,RedirectAttributes redirectAttributes) {
		try {
			this.advertisementConfigService.updateIsUse(Long.valueOf(id),isUse);
			addMessage(redirectAttributes, "修改状态成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "修改状态失败");
			logg.error("广告属性修改失败", e);
		}
		return REDIRECT_LIST;
	}
		/***
		 * 跳转到添加页面
		 * @param model
		 * @return
		 */
	   @RequestMapping("goAdd")
		public String goAdd(Model model){
		   List<SysmgrSourceConfig> sysSourceList = this.sysmgrSourceService.findAll();
			model.addAttribute("sysSourceList", sysSourceList);
			return PRE_+"adConfigAdd";
		}
	
	   /***
	    * 广告配置的添加
	    * @param adConfig
	    * @param redirectAttributes
	    * @return
	    */
	    @RequestMapping("addAdConfig")
		public String addAdConfig(SysmgrAdvertisementConfig adConfig,RedirectAttributes redirectAttributes){
		try {
				this.advertisementConfigService.add(adConfig);
				addMessage(redirectAttributes, "广告配置添加成功");
			} catch (Exception e) {
				addMessage(redirectAttributes, "广告配置添加失败");
			}
			return REDIRECT_LIST;
		}
		
	    /***
	     * 跳转到修改页面
	     * @param id
	     * @param model
	     * @return
	     */
	    @RequestMapping("goEdit/{id}/{time}")
	    public String goEit(@PathVariable("id") String id,Model model){
	    	List<SysmgrSourceConfig> sysSourceList = this.sysmgrSourceService.findAll();
			SysmgrAdvertisementConfig adConfig = this.advertisementConfigService.findById(Long.valueOf(id));
			model.addAttribute("sysSourceList", sysSourceList);
			model.addAttribute("adConfig", adConfig);
	    	return PRE_+"adConfigEdit";
	    }
	    /***
	     * 修改广告配置
	     * @param adConfig
	     * @param redirectAttributes
	     * @return
	     */
	    @RequestMapping("updateAdConfig")
	    public String updateAdConfig(SysmgrAdvertisementConfig adConfig,RedirectAttributes redirectAttributes){
	    	try {
	    		this.advertisementConfigService.update(adConfig);
      			addMessage(redirectAttributes, "广告配置修改成功");
	    	} catch (Exception e) {
	    		addMessage(redirectAttributes, "广告配置修改失败");
			}
	    	return REDIRECT_LIST;
	    }
	    /***
	     * 删除广告配置
	     * @param id
	     * @param redirectAttributes
	     * @return
	     */
	    @RequestMapping("deleteAdConfig/{id}/{time}")
	    public String deleteAdConfig(@PathVariable("id") String id,RedirectAttributes redirectAttributes){
	    try {
	    		this.advertisementConfigService.deleteById(Long.valueOf(id));
				addMessage(redirectAttributes, "删除广告配置成功");
			} catch (Exception e) {
				addMessage(redirectAttributes, "删除广告配置失败");
			}
	    	return REDIRECT_LIST;
	    }
}
