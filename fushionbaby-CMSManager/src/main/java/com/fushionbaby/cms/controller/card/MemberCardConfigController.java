package com.fushionbaby.cms.controller.card;

import java.util.Date;
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

import com.aladingshop.card.model.MemberCardConfig;
import com.aladingshop.card.service.MemberCardConfigService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.util.BasePagination;


@Controller
@RequestMapping("/memberCardConfig")
public class MemberCardConfigController extends BaseController {

	@Autowired
	private MemberCardConfigService<MemberCardConfig> memberCardConfigService;
	

	private static final Log logger = LogFactory.getLog(MemberCardConfigController.class);
	
	private static final String PRE_="models/card/config/";
	
	/**列表页*/
	private static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/memberCardConfig/configList";
	
	@SuppressWarnings("unchecked")
	@RequestMapping("configList")
	public String findAll(
			@RequestParam(value = "createTimeFrom", defaultValue = "") String createTimeFrom,
			@RequestParam(value = "createTimeTo", defaultValue = "") String createTimeTo,
			@RequestParam(value = "type", defaultValue = "") String type,
			BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("type", type.trim());
			params.put("createTimeFrom", createTimeFrom.trim());
			params.put("createTimeTo", createTimeTo.trim());
			page.setParams(params);
			page = this.memberCardConfigService.getListPage(page);
			List<MemberCardConfig> listFindAll = (List<MemberCardConfig>) page.getResult();
			model.addAttribute("createTimeFrom", createTimeFrom);
			model.addAttribute("createTimeTo", createTimeTo);
			model.addAttribute("list", listFindAll);
			model.addAttribute("page", page);
			model.addAttribute("type",type);
			return PRE_+"configList";
		} catch (Exception e) {
			logger.error("查询权限列表失败", e);
			return "";
		}
	}
	

	@RequestMapping("configAddJsp")
	public String configAddJsp(Model model) {
		/*try {
			addMessage(redirectAttributes, "添加储值卡配置成功");
			return PRE_+"privilegeList";
		} catch (Exception e) {
			logger.error("添加配置失败", e);
			return "";
		}*/
		return PRE_+"configAdd";
	}
	
	@RequestMapping("configAdd")
	public String configAdd(MemberCardConfig memberCardConfig, RedirectAttributes redirectAttributes, HttpSession session) {
		try {
			AuthUser auUser = CMSSessionUtils.getSessionUser(session);
			memberCardConfig.setUpdateTime(new Date());
			memberCardConfig.setUpdateId(auUser.getId());
			memberCardConfig.setCreateId(auUser.getId());
			memberCardConfig.setCreateTime(new Date());
			memberCardConfig.setIsDisabled("n");
			this.memberCardConfigService.add(memberCardConfig);
			addMessage(redirectAttributes, "添加储值卡配置成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("添加配置失败", e);
			return "";
		}
	}
	
	@RequestMapping("configEditJsp/{id}/{time}")
	public String configEditJsp(@PathVariable("id") Long id, Model model) {
		try {
			MemberCardConfig memberCardConfig = this.memberCardConfigService.findById(id);
			model.addAttribute("memberCardConfig", memberCardConfig);
			return PRE_+"configEdit";
		} catch (Exception e) {
			logger.error("修改配置失败", e);
			return "";
		}
	}
	
	
	@RequestMapping("configEdit")
	public String configEdit(MemberCardConfig memberCardConfig, RedirectAttributes redirectAttributes, HttpSession session) {
		try {
			AuthUser auUser = CMSSessionUtils.getSessionUser(session);
			memberCardConfig.setUpdateTime(new Date());
			memberCardConfig.setUpdateId(auUser.getId());
			this.memberCardConfigService.update(memberCardConfig);
			addMessage(redirectAttributes, "修改储值卡配置成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("修改配置失败", e);
			return "";
		}
	}

	@RequestMapping("configDel/{id}/{time}")
	public String configDel(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			this.memberCardConfigService.deleteById(id);
			addMessage(redirectAttributes, "删除储值卡配置成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("删除配置失败", e);
			return "";
		}
	}
	
	
	
	@RequestMapping("updateStatus/{id}/{time}")
	public String updateStatus(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			MemberCardConfig memberCardConfig = this.memberCardConfigService.findById(id);
			String isDisabled = memberCardConfig.getIsDisabled();
			
			
			isDisabled=CommonConstant.NO.equals(isDisabled)?CommonConstant.YES:CommonConstant.NO;
			memberCardConfig.setIsDisabled(isDisabled);
			this.memberCardConfigService.updateIsDisabled(memberCardConfig);
			addMessage(redirectAttributes, "修改储值卡配置成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("修改配置失败", e);
			return "";
		}
	}
}
