package com.fushionbaby.cms.controller.alabao; 

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

import com.aladingshop.alabao.model.AlabaoBankConfig;
import com.aladingshop.alabao.service.AlabaoBankConfigService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.cms.util.ImagePathUtil;
import com.fushionbaby.common.util.BasePagination;

@Controller
@RequestMapping("/alabaoBankConfig")
public class AlabaoBankConfigController extends BaseController { 


	@Autowired
	private AlabaoBankConfigService<AlabaoBankConfig> alabaoBankConfigService;
	

	private static final Log logger = LogFactory.getLog(AlabaoBankConfigController.class);
	
	private static final String PRE_="models/alabao/bankConfig/";
	
	/**列表页*/
	private static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/alabaoBankConfig/alabaoBankConfigList";
	
	@SuppressWarnings("unchecked")
	@RequestMapping("alabaoBankConfigList")
	public String findAll(
			@RequestParam(value = "bankCode", defaultValue = "") String bankCode,
			@RequestParam(value = "bankName", defaultValue = "") String bankName,
			BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("bankName", bankName.trim());
			params.put("bankCode", bankCode.trim());
			page.setParams(params);
			page = this.alabaoBankConfigService.getListPage(page);
			List<AlabaoBankConfig> listFindAll = (List<AlabaoBankConfig>) page.getResult();
			model.addAttribute("list", listFindAll);
			model.addAttribute("page", page);
			model.addAttribute("bankCode",bankCode);
			model.addAttribute("bankName",bankName);
			model.addAttribute("imagePath", Global.getImagePath());
			return PRE_+"alabaoBankConfigList";
		} catch (Exception e) {
			logger.error("查询权限列表失败", e);
			return "";
		}
	}
	

	@RequestMapping("alabaoBankConfigAddJsp")
	public String alabaoBankConfigAddJsp(Model model) {
		return PRE_+"alabaoBankConfigAdd";
	}
	
	@RequestMapping("alabaoBankConfigAdd")
	public String alabaoBankConfigAdd(AlabaoBankConfig alabaoBankConfig, RedirectAttributes redirectAttributes, HttpSession session) {
		try {

			AuthUser auUser = CMSSessionUtils.getSessionUser(session);
			alabaoBankConfig.setUpdateId(auUser.getId());
			alabaoBankConfig.setBankIconUrl(ImagePathUtil.getImageName(alabaoBankConfig.getBankIconUrl()));
			this.alabaoBankConfigService.add(alabaoBankConfig);
			addMessage(redirectAttributes, "添加储值卡配置成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("添加配置失败", e);
			return "";
		}
	}
	
	@RequestMapping("alabaoBankConfigEditJsp/{id}/{time}")
	public String alabaoBankConfigEditJsp(@PathVariable("id") Long id, Model model) {
		try {
			AlabaoBankConfig alabaoBankConfig = this.alabaoBankConfigService.findById(id);
			model.addAttribute("alabaoBankConfig", alabaoBankConfig);
			model.addAttribute("imagePath", Global.getImagePath());
			return PRE_+"alabaoBankConfigEdit";
		} catch (Exception e) {
			logger.error("修改配置失败", e);
			return "";
		}
	}
	
	
	@RequestMapping("alabaoBankConfigEdit")
	public String alabaoBankConfigEdit(AlabaoBankConfig alabaoBankConfig, RedirectAttributes redirectAttributes, HttpSession session) {
		try {
			AuthUser auUser = CMSSessionUtils.getSessionUser(session);
			alabaoBankConfig.setUpdateId(auUser.getId());
			alabaoBankConfig.setBankIconUrl(ImagePathUtil.getImageName(alabaoBankConfig.getBankIconUrl()));
			this.alabaoBankConfigService.update(alabaoBankConfig);
			addMessage(redirectAttributes, "修改储值卡配置成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("修改配置失败", e);
			return "";
		}
	}

	@RequestMapping("alabaoBankConfigDel/{id}/{time}")
	public String alabaoBankConfigDel(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			this.alabaoBankConfigService.deleteById(id);
			addMessage(redirectAttributes, "删除储值卡配置成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("删除配置失败", e);
			return "";
		}
	}
	

	@RequestMapping("/findBankImage/{bankCode}")
	public String findBankImage( @PathVariable String bankCode,RedirectAttributes redirectAttributes,
			Model model, HttpSession session) {
		try {
			AlabaoBankConfig alabaoBankConfig = this.alabaoBankConfigService.findByBankCode(bankCode);
			model.addAttribute("alabaoBankConfig", alabaoBankConfig);
			model.addAttribute("imagePath", Global.getImagePath());
			return "/models/alabao/bankConfig/bankImageAdd";
		} catch (Exception e) {
			logger.error("查询银行卡失败", e);
			return "";
		}
	}
	
	@RequestMapping("/updateBankImage")
	public String updateBankImage( 
			AlabaoBankConfig alabaoBankConfig,
			RedirectAttributes redirectAttributes,
			Model model, HttpSession session) {
		try {
			alabaoBankConfig.setBankIconUrl(ImagePathUtil.getImageName(alabaoBankConfig.getBankIconUrl()));
			this.alabaoBankConfigService.update(alabaoBankConfig);
			model.addAttribute("alabaoBankConfig", alabaoBankConfig);
			return "redirect:"+Global.getAdminPath()+"/alabaoBankConfig/findBankImage/"+alabaoBankConfig.getBankCode();
		} catch (Exception e) {
			logger.error("查询银行卡失败", e);
			return "";
		}
	}
}
