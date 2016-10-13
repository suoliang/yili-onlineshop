package com.fushionbaby.cms.controller.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.SysmgrGlobalConfigDto;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrGlobalConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
/**
 * @author glc 全局设置
 */
@Controller
@RequestMapping("/globalConfig")
public class SysmgrGlobalConfigController extends BaseController {

	@Autowired
	private SysmgrGlobalConfigService sysmgrGlobalConfigService;
	
	private static final Log LOGGER = LogFactory.getLog(SysmgrGlobalConfigController.class);
/**
 * 全局设置列表查询
 * @param sysmgrGlobalConfigDto 全局设置信息
 * @param page 分页
 * @param request
 * @param model
 * @version 2015-6-15
 * @return
 */
	@RequestMapping(value="/globalConfigList")
	public String findList(	SysmgrGlobalConfigDto sysmgrGlobalConfigDto,
			BasePagination page, HttpServletRequest request, ModelMap model) {
		try {
			BasePagination basePage = new BasePagination();
			if (page != null) {
				basePage = page;
			}
			Map<String, String> params = new HashMap<String, String>();
			if (sysmgrGlobalConfigDto.getName() != null)
				params.put("name", sysmgrGlobalConfigDto.getName().trim());
			if (sysmgrGlobalConfigDto.getCode() != null)
				params.put("code", sysmgrGlobalConfigDto.getCode().trim());
			basePage.setParams(params);

			basePage = sysmgrGlobalConfigService.getListPage(basePage);
			model.addAttribute("page", basePage);
			model.addAttribute("sysmgrGlobalConfigDto", sysmgrGlobalConfigDto);
		} catch (Exception e) {
			LOGGER.error("全局配置列表加载失败", e);
		}
		return "models/globalConfig/globalConfigList";
	}

	
	/**
	 *  全局设置添加页面
	 * @param model
	 * @version 2015-6-15
	 * @return
	 */
	@RequestMapping(value="/golbalConfigAddPage")
	public String golbalConfigAddPage(){
		return "models/globalConfig/globalConfigDetail";
	}
	
	
	@RequestMapping(value="/saveGlobalConfig",method=RequestMethod.POST)
	public String saveGlobalConfig(@RequestParam("name")String name,
			@RequestParam("value")String value,@RequestParam("code") String code,
			@RequestParam("remark")String remark,
			RedirectAttributes redirectAttributes){
		
		SysmgrGlobalConfig sysGlobalConfig=new SysmgrGlobalConfig();
		sysGlobalConfig.setName(name);
		sysGlobalConfig.setValue(value);
		sysGlobalConfig.setCode(code);
		sysGlobalConfig.setRemark(remark);
		sysmgrGlobalConfigService.add(sysGlobalConfig);
		addMessage(redirectAttributes, "添加成功");
		
		return "redirect:"+Global.getAdminPath()+"/globalConfig/globalConfigList" ;
	}
	

	
	@RequestMapping(value="/deleteGlobalConfig/{id}")
	public String delGlobalConfig(@PathVariable(value="id") Long id,
		RedirectAttributes redirectAttributes){
		sysmgrGlobalConfigService.deleteSysmgrGlobalConfigById(id);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/globalConfig/globalConfigList" ;
	}

	
	@RequestMapping(value="/editPage")
	public String editPage(String code,Model model){
		
		SysmgrGlobalConfig sysGlobalConfig=sysmgrGlobalConfigService.findByCode(code);
		
		model.addAttribute("sysGlobalConfig", sysGlobalConfig);
		
		
		return "models/globalConfig/editGlobalConfigPage";
	}
	@ResponseBody
	@RequestMapping(value="editGlobalValue")
	public Object editGlobalValue(SysmgrGlobalConfig sysGlobalConfig){
		
		SysmgrGlobalConfig newSysGlobalConfig = sysmgrGlobalConfigService.findByCode(sysGlobalConfig.getCode());
		newSysGlobalConfig.setValue(sysGlobalConfig.getValue());
		
		sysmgrGlobalConfigService.update(newSysGlobalConfig);
		
		return true;
	}

}
