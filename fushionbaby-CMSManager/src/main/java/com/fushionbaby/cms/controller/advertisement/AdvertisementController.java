package com.fushionbaby.cms.controller.advertisement;

import java.util.Date;
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
import com.fushionbaby.cms.util.ImagePathUtil;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.date.DateEndUtil;
import com.fushionbaby.config.model.SysmgrAdvertisementConfig;
import com.fushionbaby.config.model.SysmgrSourceConfig;
import com.fushionbaby.config.service.SysmgrAdvertisementConfigService;
import com.fushionbaby.config.service.SysmgrSourceConfigService;
import com.fushionbaby.sysmgr.model.SysmgrAdvertisement;
import com.fushionbaby.sysmgr.service.SysmgrAdvertisementService;

/***
 * 广告管理
 * 
 * @author xupeijun
 * 
 */
@Controller
@RequestMapping("/ad")
public class AdvertisementController extends BaseController{
	/** 注入广告service */
	@Autowired
	private SysmgrAdvertisementService<SysmgrAdvertisement> advertisementService;
	/** 注入广告来源service */
	@Autowired
	private SysmgrSourceConfigService<SysmgrSourceConfig> sysmgrSourceService;
	/** 广告配置的service */
	@Autowired
	private SysmgrAdvertisementConfigService<SysmgrAdvertisementConfig> adconfigService;
	
	
	/** 页面*/
    private static final String PRE_="models/advertisement/";
	
    /**列表页*/
	private static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/ad/adList";
	
	
	
	/** 日志*/
	private static final Log logg = LogFactory.getLog(AdvertisementController.class);

	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("adList")
	public String adList(
			@RequestParam(value = "adName", defaultValue = "") String adName,
			@RequestParam(value = "adCode", defaultValue = "") String adCode,
			BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			// 分页参数封装
			Map<String, String> params = new HashMap<String, String>();
			params.put("adName", adName.trim());
			params.put("adCode", adCode.trim());
			page.setParams(params);
			// 分页对象
			page = this.advertisementService.getListPage(page);
			// 分页结果
			List<SysmgrAdvertisement> ad_list = (List<SysmgrAdvertisement>) page.getResult();
			setAdNameToAdListByCode(ad_list);
			model.addAttribute("adList", ad_list);
			model.addAttribute("page", page);
			model.addAttribute("adName", adName);
			model.addAttribute("adCode", adCode);
			model.addAttribute("adPath",Global.getImagePath() );
			return PRE_+"adList";
		} catch (Exception e) {
			logg.error("广告列表查询出错", e);
			return "";
		}
	}
	/***
	 * 跳转到添加页面
	 * @param model
	 * @return
	 */
    @RequestMapping("goAdd")	
	public String goAdd(Model model){
		List<SysmgrSourceConfig> sysSourceList = this.sysmgrSourceService.findAll();
		List<SysmgrAdvertisementConfig> adConfigList = this.adconfigService.findAll();
		model.addAttribute("adConfigList", adConfigList);
		model.addAttribute("sysSourceList", sysSourceList);
		return PRE_+"adAdd";
	}
    /***
     * 广告添加
     * @param ad
     * @param adLogoUrl
     * @param redirectAttributes
     * @return
     */
	@RequestMapping("addAd")
    public String adAdd(SysmgrAdvertisement ad,RedirectAttributes redirectAttributes){
	try {
		ad.setEndTime(new Date(DateEndUtil.getEndDateLong(ad.getEndTime())));
		ad.setPicturePath(ImagePathUtil.getImageName(ad.getPicturePath()));
		this.advertisementService.add(ad);
		addMessage(redirectAttributes, "广告添加成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "广告添加失败");
			logger.error("cms:AdvertisementController.java 广告添加失败", e);
		}
    	return REDIRECT_LIST;
    }
    
    @RequestMapping("goEdit/{id}/{time}")
	public String goEdit(@PathVariable("id") String id,Model model){
		SysmgrAdvertisement ad=this.advertisementService.findById(Long.valueOf(id));
		List<SysmgrSourceConfig> sysSourceList = this.sysmgrSourceService.findAll();
		List<SysmgrAdvertisementConfig> adConfigList = this.adconfigService.findAll();
		model.addAttribute("adConfigList", adConfigList);
		model.addAttribute("sysSourceList", sysSourceList);
		model.addAttribute("ad", ad);
		model.addAttribute("adPath", Global.getImagePath());
    	return PRE_+"adEdit";
	}
	@RequestMapping("adEdit")
    public String adEdit(SysmgrAdvertisement ad,RedirectAttributes redirectAttributes){
	try {
		    ad.setEndTime(new Date(DateEndUtil.getEndDateLong(ad.getEndTime())));
			ad.setPicturePath(ImagePathUtil.getImageName(ad.getPicturePath()));
		   this.advertisementService.update(ad);
           addMessage(redirectAttributes, "广告修改成功");			
		} catch (Exception e) {
			addMessage(redirectAttributes, "广告修改失败");			
		}
		return REDIRECT_LIST;
    }
    
	
	@RequestMapping("deleteAd/{id}/{time}")
	public String deleteAd(@PathVariable("id") String id,RedirectAttributes redirectAttributes		){
		try {
			this.advertisementService.deleteById(Long.valueOf(id));
			addMessage(redirectAttributes, "删除成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "删除失败");
		}
		return REDIRECT_LIST;
	}
	
	
	private void setAdNameToAdListByCode(List<SysmgrAdvertisement> adList) {
		for (int i = 0; i < adList.size(); i++) {
			// this.setAdNameByCode(ad_list.get(i).getId());
			SysmgrAdvertisement ad = this.advertisementService.findById(adList.get(i).getId());
			String code = ad.getAdCode();
			SysmgrAdvertisementConfig adConfig = this.adconfigService.findByAdCode(code);
			String adName = adConfig.getAdName();
			ad.setAdName(adName);
			this.advertisementService.update(ad);
		}
	}
	
}
