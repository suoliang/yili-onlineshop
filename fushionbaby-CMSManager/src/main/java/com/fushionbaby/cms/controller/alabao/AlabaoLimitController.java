package com.fushionbaby.cms.controller.alabao; 

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.axis.utils.SessionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.alabao.model.AlabaoLimit;
import com.aladingshop.alabao.service.AlabaoLimitService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.cms.util.constant.Constant;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;

@Controller
@RequestMapping("/alabaoLimit")
public class AlabaoLimitController extends BaseController { 


	@Autowired
	private AlabaoLimitService<AlabaoLimit> alabaoLimitService;
	private static final Log logger = LogFactory.getLog(AlabaoLimitController.class);
	
	
	@RequestMapping("alabaoLimitList")
	public String findAll(
			@RequestParam(value = "account", defaultValue = "") String account,
			@RequestParam(value = "accountLevel", defaultValue = "") String accountLevel,
			@RequestParam(value = "createTimeFrom", defaultValue = "") String createTimeFrom,
			@RequestParam(value = "createTimeTo", defaultValue = "") String createTimeTo,
			BasePagination page, Model model) {
		try {
			model.addAttribute("account",account);
			model.addAttribute("accountLevel",accountLevel);
			model.addAttribute("createTimeFrom",createTimeFrom);
			model.addAttribute("createTimeTo",createTimeTo);
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("account", account.trim());
			params.put("accountLevel",accountLevel);
			params.put("createTimeFrom",createTimeFrom);
			params.put("createTimeTo",createTimeTo);
			page.setParams(params);
			page = this.alabaoLimitService.getListPage(page);
			model.addAttribute("page", page);
			return "models/alabao/limit/alabaoLimitList";
		} catch (Exception e) {
			logger.error("查询如意宝转出限制列表失败", e);
			return "";
		}
	}

	@RequestMapping("/toUpdateAlabaoLimit")
	public String toUpdateAlabaoLimit(String account, Model model) {
		AlabaoLimit alabaoLimit=alabaoLimitService.findByAccount(account);
		model.addAttribute("alabaoLimit", alabaoLimit);
		return "models/alabao/limit/alabaoLimitUpdate";
		
	}
	
	@RequestMapping("/updateAlabaoLimit")
	public String updateAlabaoLimit( AlabaoLimit alabaoLimit,RedirectAttributes redirectAttributes) {
		try {
			AlabaoLimit alabaoLimitNew =alabaoLimitService.findByAccount(alabaoLimit.getAccount());
			alabaoLimitNew.setAccountLevel(alabaoLimit.getAccountLevel());
			alabaoLimitNew.setMoneyLimit(alabaoLimit.getMoneyLimit());
			alabaoLimitNew.setNumberLimit(alabaoLimit.getNumberLimit());
			alabaoLimitNew.setRemark(alabaoLimit.getRemark());
			alabaoLimitService.update(alabaoLimitNew);
			addMessage(redirectAttributes,"修改阿拉宝限制成功!");
		} catch (Exception e) {
			logger.error("修改阿拉宝限制失败", e);
			addMessage(redirectAttributes,"修改阿拉宝限制失败!");
		}
		return "redirect:" + Global.getAdminPath() +"/alabaoLimit/alabaoLimitList";
	}
	
	@ResponseBody
	@RequestMapping("/deleteAlabaoLimit")
	public String deleteAlabaoLimit( Long id) {
		try {
			alabaoLimitService.deleteById(id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
		
	}
	
	@RequestMapping("/toAddAlabaoLimit")
	public String toAddAlabaoLimit() {
		return "models/alabao/limit/alabaoLimitAdd";
		
	}
	
	@RequestMapping("/addAlabaoLimit")
	public String addAlabaoLimit(AlabaoLimit alabaoLimit,RedirectAttributes redirectAttributes,HttpSession session) {
		try {
			AuthUser authUser=CMSSessionUtils.getSessionUser(session);
			alabaoLimit.setCreateId(authUser.getId());
			alabaoLimitService.add(alabaoLimit);
			addMessage(redirectAttributes,"添加阿拉宝转出限制成功");
		} catch (Exception e) {
			addMessage(redirectAttributes,"添加阿拉宝转出限制失败");
		}
		return "redirect:" + Global.getAdminPath() +"/alabaoLimit/alabaoLimitList";
		
	}
	
	@ResponseBody
	@RequestMapping("/checkAccountExist")
	public String checkAccountExist(@RequestParam(value = "account", defaultValue = "") String account) {
		try {
			AlabaoLimit alabaoLimit=alabaoLimitService.findByAccount(account);
			if(ObjectUtils.equals(null, alabaoLimit)){
				return Constant.SUCCESS;
			}else{
				return Constant.FAILURE;
			}
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			return Constant.FAILURE;
		}
		
	}
}
