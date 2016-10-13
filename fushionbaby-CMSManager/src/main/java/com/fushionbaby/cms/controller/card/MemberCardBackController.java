package com.fushionbaby.cms.controller.card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.aladingshop.card.model.MemberCardBack;
import com.aladingshop.card.service.MemberCardBackService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.controller.card.excel.MemberCardBackExcelReport;
import com.fushionbaby.cms.dto.MemberCardBackDto;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;

/***
 * 益多宝退卡记录
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年9月30日下午3:38:24
 */
@RequestMapping("memberCardBack")
@Controller
public class MemberCardBackController extends BaseController {

	private static final Log LOGGER=LogFactory.getLog(MemberCardBackController.class);
	
	/** 益多宝订单*/
	@Autowired
	private MemberCardBackService<MemberCardBack> memberCardBackService;
	
	private static final String PRE_="models/card/back/";
	
	@SuppressWarnings("unchecked")
	@RequestMapping("cardBackList")
	public String cardBackList(MemberCardBackDto cardBackDto,Model model,BasePagination page){
		LOGGER.info("MemberCardBackController.java  益多宝退卡记录查询开始");
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("acount", cardBackDto.getAcount());
			params.put("bankCardHolder", cardBackDto.getBankCardHolder());
			params.put("bankCardNo", cardBackDto.getBankCardNo());
			params.put("bankName", cardBackDto.getBankName());
			params.put("cardNo", cardBackDto.getCardNo());
			params.put("createTimeFrom", cardBackDto.getCreateTimeFrom());
			params.put("createTimeTo", cardBackDto.getCreateTimeTo());
			params.put("backStatus", cardBackDto.getBackStatus());
			page.setParams(params);
			page = this.memberCardBackService.getListPage(page);
			List<MemberCardBack> memberCardBacklist = (List<MemberCardBack>) page.getResult();
			model.addAttribute("memberCardBacklist", memberCardBacklist);
			model.addAttribute("page", page);
			model.addAttribute("cardBackDto", cardBackDto);
			return PRE_+"cardBackList";
		} catch (Exception e) {
			LOGGER.error("MemberCardBackController.java  益多宝退卡记录查询异常", e);
			return "";
		}
		
	}
	
	@RequestMapping("updateStatus/{id}/{status}/{time}")
	public String updateStatus(@PathVariable("id") Long id,@PathVariable("status") String status,RedirectAttributes redirect,HttpSession session){
		try {
			AuthUser user=CMSSessionUtils.getSessionUser(session);
			MemberCardBack cardBack=this.memberCardBackService.findById(id);
			cardBack.setBackStatus(status);
			cardBack.setUpdateId(user.getId());
			this.memberCardBackService.update(cardBack);
			addMessage(redirect, "修改成功");
		} catch (Exception e) {
			LOGGER.error("MemberCardBackController.java  益多宝退卡修改状态异常", e);
			addMessage(redirect, "修改失败");
		}
		return "redirect:"+Global.getAdminPath()+"/memberCardBack/cardBackList";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("cardBackListExportExcel")
	public void cardBackListExportExcel(HttpServletResponse response,MemberCardBackDto cardBackDto,Model model,BasePagination page){
		LOGGER.info("益多宝退卡记录导出开始");
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("acount", cardBackDto.getAcount());
			params.put("bankCardHolder", cardBackDto.getBankCardHolder());
			params.put("bankCardNo", cardBackDto.getBankCardNo());
			params.put("bankName", cardBackDto.getBankName());
			params.put("cardNo", cardBackDto.getCardNo());
			params.put("createTimeFrom", cardBackDto.getCreateTimeFrom());
			params.put("createTimeTo", cardBackDto.getCreateTimeTo());
			params.put("backStatus", cardBackDto.getBackStatus());
			params.put("isExportUse", "y");
			page.setParams(params);
			page = this.memberCardBackService.getListPage(page);
			List<MemberCardBack> memberCardBacklist = (List<MemberCardBack>) page.getResult();
			MemberCardBackExcelReport report = new MemberCardBackExcelReport();
			report.getReport("益多宝退卡记录导出列表", memberCardBacklist, response);
		} catch (Exception e) {
			LOGGER.error("益多宝退卡记录导出异常", e);
		}
		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateCardType", method = RequestMethod.POST)
	public Object updateOrderDetails(@RequestParam String cardNo,@RequestParam String cardType,
			RedirectAttributes redirectAttributes,
			ModelMap model) {
		JsonResponseModel jsonModel = new JsonResponseModel();
		try {
			MemberCardBack memberCardBack = memberCardBackService.findByCardNo(cardNo);
			memberCardBack.setCardType(cardType);
			memberCardBackService.update(memberCardBack);
			jsonModel.Success("卡类型修改成功!");
		} catch (Exception e) {
			jsonModel.Fail("卡类型修改失败!");
		}
		return jsonModel;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteCardOrder", method = RequestMethod.POST)
	public Object deleteOrder(@RequestParam String cardNo,RedirectAttributes redirectAttributes,
			ModelMap model) {
		JsonResponseModel jsonModel = new JsonResponseModel();
		try {
			MemberCardBack memberCardBack=memberCardBackService.findByCardNo(cardNo);
			memberCardBackService.deleteById(memberCardBack.getId());
			jsonModel.Success("删除退卡成功!");
		} catch (Exception e) {
			jsonModel.Fail("删除退卡失败!");
		}
		return jsonModel;
	}
}
