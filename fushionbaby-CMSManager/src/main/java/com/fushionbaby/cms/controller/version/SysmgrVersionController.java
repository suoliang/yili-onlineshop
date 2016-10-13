package com.fushionbaby.cms.controller.version;

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

import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.auth.service.AuthUserService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrSourceConfig;
import com.fushionbaby.config.model.SysmgrVersionConfig;
import com.fushionbaby.config.service.SysmgrSourceConfigService;
import com.fushionbaby.config.service.SysmgrVersionConfigService;

/**
 * 版本
 * 
 * @author xupeijun
 * 
 */
@Controller
@RequestMapping("/version")
public class SysmgrVersionController  extends BaseController{

	/**版本*/
	@Autowired
	private SysmgrVersionConfigService<SysmgrVersionConfig> sysmgrVersionService;

	/**版本来源*/
	@Autowired
	private SysmgrSourceConfigService<SysmgrSourceConfig> sysmgrSourceService;

	@Autowired
	private AuthUserService<AuthUser> authUserService;

	/** 日志*/
	private static final Log logger = LogFactory.getLog(SysmgrVersionController.class);
	
	/** 页面*/
    private static final String PRE_="models/version/";
	
    /**列表页*/
	private static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/version/versionList";
	

	/**
	 * 版本列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("versionList")
	public String findAll(
			@RequestParam(value = "versionNum", defaultValue = "") String versionNum,
			@RequestParam(value = "sourceCode", defaultValue = "") String sourceCode,
			BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("versionNum", versionNum.trim());
			params.put("sourceCode", sourceCode.trim());
			page.setParams(params);
			page = this.sysmgrVersionService.getListPage(page);
			List<SysmgrVersionConfig> sysmgrVersionList = (List<SysmgrVersionConfig>) page.getResult();
			List<SysmgrSourceConfig> sysmgrSourceList = this.sysmgrSourceService.findAll();
			model.addAttribute("sysmgrSourceList", sysmgrSourceList);
			model.addAttribute("sysmgrVersionList", sysmgrVersionList);
			model.addAttribute("page", page);
			model.addAttribute("versionNum", versionNum);
			model.addAttribute("sourceCode", sourceCode);
			return PRE_+"versionList";
		} catch (Exception e) {
			logger.error("查询版本列表出错", e);
			return "";
		}

	}

	/***
	 * 跳转到版本添加的页面
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("goAdd")
	public String goAdd(HttpSession session, Model model) {
		try {
			AuthUser user = CMSSessionUtils.getSessionUser(session);
			List<SysmgrSourceConfig> sysmgrSourceList = this.sysmgrSourceService.findAll();
			model.addAttribute("sysmgrSourceList", sysmgrSourceList);
			model.addAttribute("user", user);
		} catch (Exception e) {
			logger.error("跳转到添加页面出错", e);
		}
		return PRE_+"versionAdd";
	}
	
	/***
	 * 版本的新增
	 * 
	 * @param sysmgrVersion
	 * @return
	 */
	@RequestMapping("addVersion")
	public String Add(SysmgrVersionConfig sysmgrVersion,RedirectAttributes redirectAttributes) {
		try {
			this.sysmgrVersionService.add(sysmgrVersion);
			addMessage(redirectAttributes, "版本添加成功");
		} catch (Exception e) {
			logger.error("版本添加失败", e);
			addMessage(redirectAttributes, "版本添加失败");
		}
		return REDIRECT_LIST;
	}

	/***
	 * 跳转到修改页面
	 * @param id
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("goEdit/{id}/{time}")
	public String goEdit(@PathVariable("id") Long id, Model model,
			HttpSession session) {
		try {
			List<SysmgrSourceConfig> sysmgrSourceList = this.sysmgrSourceService.findAll();
			SysmgrVersionConfig sysmgrVersion = this.sysmgrVersionService.findById(id);
			// 创建人信息
			AuthUser user = this.authUserService.findById(sysmgrVersion.getCreateId());
			model.addAttribute("user", user);
			// 当前登录者信息
			AuthUser loginUser = CMSSessionUtils.getSessionUser(session);
			model.addAttribute("loginUser", loginUser);
			model.addAttribute("sysmgrSourceList", sysmgrSourceList);
			model.addAttribute("sysmgrVersion", sysmgrVersion);
		} catch (Exception e) {
			logger.error("跳转到修改页面出错", e);
		}
		return PRE_+"versionEdit";
	}
	
	/***
	 * 版本修改
	 * @param sysmgrVersion
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("updateVersion")
	public String update(SysmgrVersionConfig sysmgrVersion, RedirectAttributes redirectAttributes) {
		try {
			this.sysmgrVersionService.update(sysmgrVersion);
			addMessage(redirectAttributes, "版本修改成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "版本修改失败");
			logger.error("修改版本出错", e);
		}
		return REDIRECT_LIST;
	}
	
	/***
	 * 版本的删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteVersion/{id}/{time}")
	public String  deleteVersion(@PathVariable Long id,RedirectAttributes redirectAttributes,HttpSession session) {
		try {
			SysmgrVersionConfig sysmgrVersion = new SysmgrVersionConfig();
			sysmgrVersion.setId(id);
			sysmgrVersion.setIsDeleted(CommonConstant.YES);
			sysmgrVersion.setUpdateId(CMSSessionUtils.getSessionUser(session).getId());
			sysmgrVersionService.update(sysmgrVersion);
			addMessage(redirectAttributes, "删除成功");
		} catch (Exception e) {
			logger.error("删除操作失败", e);
			addMessage(redirectAttributes, "删除失败");
		}
		return REDIRECT_LIST;
	}


}
