package com.fushionbaby.cms.controller.alabao; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.alabao.model.AlabaoAccountBank;
import com.aladingshop.alabao.model.AlabaoBankConfig;
import com.aladingshop.alabao.service.AlabaoAccountBankService;
import com.aladingshop.alabao.service.AlabaoBankConfigService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.util.constant.Constant;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;

@Controller
@RequestMapping("/alabaoAccountBank")
public class AlabaoBankAccountController extends BaseController { 


	@Autowired
	private AlabaoBankConfigService<AlabaoBankConfig> alabaoBankConfigService;
	@Autowired
	private AlabaoAccountBankService<AlabaoAccountBank> alabaoAccountBankService;
	@Autowired
	private MemberService<Member> memberService;
	private static final Log logger = LogFactory.getLog(AlabaoBankAccountController.class);
	
	private static final String PRE_="models/alabao/accountBank/";
	
	/**列表页*/
	//private static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/alabaoBankConfig/alabaoBankConfigList";
	
	@SuppressWarnings("unchecked")
	@RequestMapping("alabaoAccountBankList")
	public String findAll(
			@RequestParam(value = "account", defaultValue = "") String account,
			@RequestParam(value = "cardNo", defaultValue = "") String cardNo,
			@RequestParam(value = "cardHolder", defaultValue = "") String cardHolder,
			@RequestParam(value = "createTimeFrom", defaultValue = "") String createTimeFrom,
			@RequestParam(value = "createTimeTo", defaultValue = "") String createTimeTo,			
			BasePagination page, Model model) {
		try {
			model.addAttribute("account",account);
			model.addAttribute("cardNo",cardNo);
			model.addAttribute("cardHolder",cardHolder);
			model.addAttribute("createTimeFrom", createTimeFrom);
			model.addAttribute("createTimeTo",createTimeTo);
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("account", account.trim());
			params.put("cardNo", cardNo.trim());
			params.put("cardHolder", cardHolder.trim());
			params.put("createTimeFrom", createTimeFrom.trim());
			params.put("createTimeTo", createTimeTo.trim());
			page.setParams(params);
			page = this.alabaoAccountBankService.getListPage(page);
			List<AlabaoAccountBank> alabaoAccountBankList=(List<AlabaoAccountBank>) page.getResult(); 
			for(AlabaoAccountBank alabaoAccountBank : alabaoAccountBankList){
				Member member=(Member) memberService.findById(alabaoAccountBank.getMemberId());
				if(ObjectUtils.notEqual(member, null)){
				alabaoAccountBank.setLoginName(member.getLoginName());
				}
			}
			model.addAttribute("page", page);
			return PRE_+"alabaoAccountBankList";
		} catch (Exception e) {
			logger.error("查询如意宝账户银行列表失败", e);
			return "";
		}
	}

	@ResponseBody
	@RequestMapping("/updateBankBranch")
	public Object updateBankBranch( Long id,String bankBranchName,
			Model model, HttpSession session) {
		JsonResponseModel jsonModel = new JsonResponseModel();
		try {
			AlabaoAccountBank alabaoAccountBank =alabaoAccountBankService.findById(id);
			alabaoAccountBank.setBankBranchName(bankBranchName);
			alabaoAccountBankService.update(alabaoAccountBank);
			jsonModel.Success("修改银行支行成功!");
		} catch (Exception e) {
			logger.error("修改银行支行失败", e);
			jsonModel.Fail("修改银行支行失败!");
		}
		return jsonModel;
	}
	
	@ResponseBody
	@RequestMapping("/deleteAccountBank")
	public String deleteAccountBank( Long id) {
		try {
			alabaoAccountBankService.deleteById(id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
		
	}
	
	
	@RequestMapping("updateAccountIsValidate/{id}/{validateStatus}/{time}")
	public String updateAccountIsValidate(@PathVariable(value="id")Long id,@RequestParam(value="validateStatus")String validateStatus){
		
		try {
			AlabaoAccountBank alabaoAccountBank = alabaoAccountBankService.findById(id);
			alabaoAccountBank.setIsValidate(validateStatus);
			alabaoAccountBankService.update(alabaoAccountBank);
		} catch (Exception e) {
			logger.error("修改银行认证状态失败！", e);
			return "";
		}
		return "redirect:"+Global.getAdminPath()+"/alabaoAccountBank/alabaoAccountBankList";
	}
}
