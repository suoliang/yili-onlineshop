package com.fushionbaby.cms.controller.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fushionbaby.cms.config.Global;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sms.dto.Sms5cReqDto;
import com.fushionbaby.sms.dto.Sms5cResDto;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.model.SmsTypeConfig;
import com.fushionbaby.sms.service.Sms5cService;
import com.fushionbaby.sms.service.SmsService;
import com.fushionbaby.sms.service.SmsTypeConfigService;

/**
 * 
 * @author King suoliang
 *
 */
@Controller
@RequestMapping("/sms")
public class SmsController {
	
	/**记录日志*/
	private static final Log logger = LogFactory.getLog(Sms.class);
	
	@Autowired
	private SmsService<Sms> smsService;
	
	/**短信类型*/
	@Autowired
	private SmsTypeConfigService<SmsTypeConfig> smsTypeService;
	
	/**美联软通短信服务*/
	@Autowired
	private Sms5cService sms5cService;
	
	/**加载短信类型列表*/
	public void loadSmsType(ModelMap model){
		try {
			List<SmsTypeConfig> smsTypeList = smsTypeService.findAll();
			model.addAttribute("smsTypeList",smsTypeList);
			logger.debug("");
		} catch (Exception e) {
			logger.error("");
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("smsList")
	public String findList(
			@RequestParam(value = "memberName",defaultValue = "")String memberName,
			@RequestParam(value = "mobile",defaultValue = "")String mobile,
			@RequestParam(value = "sourceCode",defaultValue = "")String sourceCode,
			@RequestParam(value = "smsTypeId",defaultValue = "")String smsTypeId,
			@RequestParam(value = "createTimeFrom",defaultValue = "")String createTimeFrom,
			@RequestParam(value = "createTimeTo",defaultValue = "")String createTimeTo,
			BasePagination page,HttpServletRequest request,ModelMap model){
		try {
			/**加载短信类型列表*/
			loadSmsType(model);
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("memberName", memberName);
			params.put("mobile", mobile);
			params.put("sourceCode", sourceCode);
			params.put("smsTypeId", smsTypeId);
			params.put("createTimeFrom", createTimeFrom);
			params.put("createTimeTo", createTimeTo);
			page.setParams(params);
			page = smsService.getListPage(page);
			List<Sms> smsList = (List<Sms>) page.getResult();
			
			model.addAttribute(smsList);
			model.addAttribute("page", page);
			model.addAttribute("memberName", memberName);
			model.addAttribute("mobile", mobile);
			model.addAttribute("sourceCode", sourceCode);
			model.addAttribute("smsTypeId", smsTypeId);
			model.addAttribute("createTimeFrom", createTimeFrom);
			model.addAttribute("createTimeTo", createTimeTo);
			/**美联软通短信信息*/
			Sms5cReqDto dto=new Sms5cReqDto();
			Sms5cResDto resDto = sms5cService.querySmsMessage(dto);
			model.addAttribute("SmsResDto", resDto);
			
			//日志
			logger.debug("");
			
		} catch (Exception e) {
			//错误日志
			logger.error(e + "");
		}
		return "models/sms/smsList";
	}
	
	@RequestMapping("delete/{id}/{time}")
	public String deleteSms(@PathVariable(value="id")Long id){
		smsService.deleteById(id);
		return "redirect:"+Global.getAdminPath()+"/sms/smsList";
	}
}
