package com.fushionbaby.cms.controller.alabao;

import java.util.ArrayList;
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

import com.aladingshop.alabao.model.AlabaoConfig;
import com.aladingshop.alabao.service.AlabaoConfigService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.auth.service.AuthUserService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.common.util.BasePagination;

@Controller
@RequestMapping("/alabaoConfig")
public class AlabaoConfigController extends BaseController {

	@Autowired
	private AlabaoConfigService<AlabaoConfig> alabaoConfigService;
	
	@Autowired
	private AuthUserService<AuthUser> authUserService;
	
	private static final Log logger = LogFactory.getLog(AlabaoConfigController.class);

	private static final String PRE_="models/alabao/config/";
	
	/**列表页*/
	private static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/alabaoConfig/configList";
	
	@SuppressWarnings("unchecked")
	@RequestMapping("configList")
	public String findAll(
			@RequestParam(value = "createTimeFrom", defaultValue = "") String createTimeFrom,
			@RequestParam(value = "createTimeTo", defaultValue = "") String createTimeTo,
			BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("createTimeFrom", createTimeFrom.trim());
			params.put("createTimeTo", createTimeTo.trim());
			page.setParams(params);
			page = this.alabaoConfigService.getListPage(page);
			List<AlabaoConfig> listFindAlls = (List<AlabaoConfig>) page.getResult();
			List<AlabaoConfig> listFindAll = new ArrayList<AlabaoConfig>();
			for (AlabaoConfig alabaoConfig : listFindAlls) {
				AuthUser authUser = authUserService.findById(alabaoConfig.getUpdateId());
				if(authUser!=null)
				alabaoConfig.setUpdateName(authUser.getLoginName());
				listFindAll.add(alabaoConfig);
			}
			model.addAttribute("createTimeFrom", createTimeFrom);
			model.addAttribute("createTimeTo", createTimeTo);
			model.addAttribute("list", listFindAll);
			model.addAttribute("page", page);
			return PRE_+"configList";
		} catch (Exception e) {
			logger.error("查询权限列表失败", e);
			return "";
		}
	}
	
	@RequestMapping("configAddJsp")
	public String configAddJsp(Model model) {
		return PRE_+"configAdd";
	}
	

	@RequestMapping("configAdd")
	public String configAdd(AlabaoConfig alabaoConfig, RedirectAttributes redirectAttributes, HttpSession session) {
		try {
			AuthUser auUser = CMSSessionUtils.getSessionUser(session);
			alabaoConfig.setUpdateId(auUser.getId());
			alabaoConfig.setUpdateTime(new Date());
			this.alabaoConfigService.add(alabaoConfig);
			addMessage(redirectAttributes, "添加阿拉宝配置成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("添加配置失败", e);
			return "";
		}
	}
	
	@RequestMapping("configEditJsp/{id}/{time}")
	public String configEditJsp(@PathVariable("id") Long id, Model model) {
		try {
			/*MemberCardConfig memberCardConfig = this.memberCardConfigService.findById(id);
			model.addAttribute("memberCardConfig", memberCardConfig);*/
			AlabaoConfig alabaoConfig = this.alabaoConfigService.findById(id);
			model.addAttribute("alabaoConfig", alabaoConfig);
			return PRE_+"configEdit";
		} catch (Exception e) {
			logger.error("修改配置失败", e);
			return "";
		}
	}
	

	@RequestMapping("configEdit")
	public String configEdit(AlabaoConfig alabaoConfig, RedirectAttributes redirectAttributes, HttpSession session) {
		try {
			AuthUser auUser = CMSSessionUtils.getSessionUser(session);
			alabaoConfig.setUpdateTime(new Date());
			alabaoConfig.setUpdateId(auUser.getId());
			this.alabaoConfigService.updateById(alabaoConfig);
			addMessage(redirectAttributes, "修改储值卡配置成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("修改配置失败", e);
			return "";
		}
	}
}

