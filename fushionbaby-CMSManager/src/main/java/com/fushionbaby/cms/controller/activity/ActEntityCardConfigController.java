package com.fushionbaby.cms.controller.activity; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fushionbaby.act.activity.model.ActEntityCardConfig;
import com.fushionbaby.act.activity.service.ActEntityCardConfigService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.util.BasePagination;

@Controller
@RequestMapping("/actEntityCardConfig")
public class ActEntityCardConfigController extends BaseController { 


	@Autowired
	private ActEntityCardConfigService<ActEntityCardConfig> actEntityCardConfigService;
	

	private static final Log logger = LogFactory.getLog(ActEntityCardConfigController.class);
	
	private static final String PRE_="models/entity/"; 
	
	/**列表页*/
	private static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/actEntityCardConfig/actEntityCardConfigList";
	
	@SuppressWarnings("unchecked")
	@RequestMapping("actEntityCardConfigList")
	public String findAll(
			@RequestParam(value = "faceMoney", defaultValue = "") String faceMoney,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "type", defaultValue = "") String type,
			BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("type", type.trim());
			params.put("faceMoney", faceMoney.trim());
			params.put("name", name.trim());
			page.setParams(params);
			page = this.actEntityCardConfigService.getListPage(page);
			List<ActEntityCardConfig> listFindAll = (List<ActEntityCardConfig>) page.getResult();
			model.addAttribute("list", listFindAll);
			model.addAttribute("page", page);
			model.addAttribute("type",type);
			model.addAttribute("faceMoney",faceMoney);
			model.addAttribute("name",name);
			return PRE_+"actEntityCardConfigList";
		} catch (Exception e) {
			logger.error("查询权限列表失败", e);
			return "";
		}
	}
	

	@RequestMapping("actEntityCardConfigAddJsp")
	public String actEntityCardConfigAddJsp(Model model) {
		/*try {
			addMessage(redirectAttributes, "添加实体卡配置成功");
			return PRE_+"actEntityCardConfigAdd";
		} catch (Exception e) {
			logger.error("添加配置失败", e);
			return "";
		}*/
		return PRE_+"actEntityCardConfigAdd";
	}
	
	@RequestMapping("actEntityCardConfigAdd")
	public String actEntityCardConfigAdd(ActEntityCardConfig actEntityCardConfig, RedirectAttributes redirectAttributes, HttpSession session) {
		try {
			actEntityCardConfig.setIsDisabled(CommonConstant.NO);
			this.actEntityCardConfigService.add(actEntityCardConfig);
			addMessage(redirectAttributes, "添加实体卡配置成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("添加配置失败", e);
			return "";
		}
	}
	
	@RequestMapping("actEntityCardConfigEditJsp/{id}/{time}")
	public String actEntityCardConfigEditJsp(@PathVariable("id") Long id, Model model) {
		try {
			ActEntityCardConfig actEntityCardConfig = this.actEntityCardConfigService.findById(id);
			model.addAttribute("actEntityCardConfig", actEntityCardConfig);
			return PRE_+"actEntityCardConfigEdit";
		} catch (Exception e) {
			logger.error("修改配置失败", e);
			return "";
		}
	}
	
	
	@RequestMapping("actEntityCardConfigEdit")
	public String actEntityCardConfigEdit(ActEntityCardConfig actEntityCardConfig, RedirectAttributes redirectAttributes, HttpSession session) {
		try {
			
			this.actEntityCardConfigService.update(actEntityCardConfig);
			addMessage(redirectAttributes, "修改实体卡配置成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("修改配置失败", e);
			return "";
		}
	}

	@RequestMapping("actEntityCardConfigDel/{id}/{time}")
	public String actEntityCardConfigDel(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			this.actEntityCardConfigService.deleteById(id);
			addMessage(redirectAttributes, "删除实体卡配置成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("删除配置失败", e);
			return "";
		}
	}

	@RequestMapping("updateStatus/{id}/{time}")
	public String updateStatus(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			ActEntityCardConfig actEntityCardConfig = actEntityCardConfigService.findById(id);
			String isDisabled = actEntityCardConfig.getIsDisabled();
			
			isDisabled=CommonConstant.NO.equals(isDisabled)?CommonConstant.YES:CommonConstant.NO;
			actEntityCardConfig.setIsDisabled(isDisabled);
			this.actEntityCardConfigService.updateIsDisabled(actEntityCardConfig);
			addMessage(redirectAttributes, "操作成功！");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("操作失败！", e);
			return "";
		}
	}
}
