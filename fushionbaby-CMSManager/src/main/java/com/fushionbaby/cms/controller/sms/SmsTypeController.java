package com.fushionbaby.cms.controller.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fushionbaby.cms.config.Global;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sms.model.SmsTypeConfig;
import com.fushionbaby.sms.service.SmsTypeConfigService;

/**
 * @author cyla
 * 
 */
@Controller
@RequestMapping("/smsType")
public class SmsTypeController {

	/**
	 * 注入短信类型service
	 */
	@Autowired
	private SmsTypeConfigService<SmsTypeConfig> smsTypeService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/findAll")
	public String query(
			@RequestParam(value = "smsName", defaultValue = "") String smsName,
			ModelMap model, BasePagination page){
		
		if (page == null) {
			page = new BasePagination();
		}
		// 分页参数封装
		Map<String, String> params = new HashMap<String, String>();
		params.put("smsName", smsName);
		page.setParams(params);
		
		// 分页对象
		page = (BasePagination) smsTypeService.findBySmsName(page);
		
		// 分页结果
		List<SmsTypeConfig> smsTypes = (List<SmsTypeConfig>) page
				.getResult();
		model.addAttribute("smsTypes", smsTypes);
		model.addAttribute("page", page);
		model.addAttribute("smsName", smsName);

		return "models/sms/smsTypeList";
		
	}
	
	
	@RequestMapping("editSms/{id}/{time}")
	public String editSms(@PathVariable(value="id")Long id,ModelMap model){
		
		SmsTypeConfig smsTypeConfig = (SmsTypeConfig) smsTypeService.findById(id);
		model.addAttribute("smsTypeConfig", smsTypeConfig);
		return "models/sms/smsTypeUpdate";
	}
	
	@RequestMapping("updateSms")
	public String updateSms(SmsTypeConfig smsTypeConfig){
		if(smsTypeConfig != null){
			smsTypeService.update(smsTypeConfig);
		}
		return "redirect:"+Global.getAdminPath()+"/smsType/findAll";
	}
}
