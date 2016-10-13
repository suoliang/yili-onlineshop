package com.fushionbaby.cms.controller.alabao; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoRollOffRecord;
import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.aladingshop.alabao.model.AlabaoTurnOut;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoRollOffRecordService;
import com.aladingshop.alabao.service.AlabaoShiftToRecordService;
import com.aladingshop.alabao.service.AlabaoTurnOutService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.controller.alabao.excel.AlabaoTurnOutExcelReport;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.cms.util.constant.Constant;
import com.fushionbaby.common.constants.AlabaoPayTypeConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.enums.alabao.AlabaoTurnToBankEnum;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.GetSerialNumUtil;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.model.SmsTypeConfig;
import com.fushionbaby.sms.service.SmsService;
import com.fushionbaby.sms.service.SmsTypeConfigService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/alabaoTurnOut")
public class AlabaoTurnOutController extends BaseController {

	/** 转出申请*/
	@Autowired
	private AlabaoTurnOutService<AlabaoTurnOut> alabaoTurnOutService;
	
	/** 如意消费卡账户信息 */
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	@Autowired
	private AlabaoShiftToRecordService<AlabaoShiftToRecord> alabaoShiftToRecordService;
	/** 如意消费卡转出记录*/
	@Autowired
	private AlabaoRollOffRecordService<AlabaoRollOffRecord> alabaoRollOffRecordService;
	/** 会员信息*/
	@Autowired
	private MemberService<Member> memberService;
	/**短信服务*/
	@Autowired
	private SmsService<Sms> smsService;
	/** 短信类型 */
	@Autowired
	private SmsTypeConfigService<SmsTypeConfig> smsTypeConfigService;
	
	
	private static final Log logger = LogFactory.getLog(AlabaoTurnOutController.class);
	
	private static final String PRE_="models/alabao/turnOut/";
	
	/**列表页*/
	private static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/alabaoTurnOut/alabaoTurnOutList";
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("alabaoTurnOutList")
	public String findAll(
			@RequestParam(value = "createTimeFrom", defaultValue = "") String createTimeFrom,
			@RequestParam(value = "createTimeTo", defaultValue = "") String createTimeTo,
			@RequestParam(value = "account", defaultValue = "") String account,
			@RequestParam(value = "cardHolder", defaultValue = "") String cardHolder,
			@RequestParam(value = "turnOutStatus", defaultValue = "") String turnOutStatus,
			BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("createTimeFrom", createTimeFrom.trim());
			params.put("createTimeTo", createTimeTo.trim());
			params.put("account", account.trim());
			params.put("cardHolder", cardHolder.trim());
			params.put("turnOutStatus", turnOutStatus.trim());
			page.setParams(params);
			page = this.alabaoTurnOutService.getListPage(page);
			List<AlabaoTurnOut> listFindAlls = (List<AlabaoTurnOut>) page.getResult();
			for (AlabaoTurnOut alabaoAccount : listFindAlls) {
				Member member = memberService.findById(alabaoAccount.getMemberId());
				if(member!=null)
				alabaoAccount.setMemberName(member.getLoginName());;
			}
			model.addAttribute("createTimeFrom", createTimeFrom);
			model.addAttribute("createTimeTo", createTimeTo);
			model.addAttribute("account", account);
			model.addAttribute("cardHolder", cardHolder);
			model.addAttribute("turnOutStatus", turnOutStatus);
			model.addAttribute("list", listFindAlls);
			model.addAttribute("page", page);
			return PRE_+"alabaoTurnOutList";
		} catch (Exception e) {
			logger.error("如意消费卡 提现申请列表查询异常  cms:AlabaoTurnOutController.java", e);
			return "";
		}
	}
	
	
	@RequestMapping("updateStatus/{id}/{turnOutStatus}")
	public String updateStatus(@PathVariable("id") Long id,
			@PathVariable("turnOutStatus") String turnOutStatus,
			RedirectAttributes redirectAttributes,HttpSession session) {
		try {
			AuthUser user=CMSSessionUtils.getSessionUser(session);
			AlabaoTurnOut alabaoTurnOut=this.alabaoTurnOutService.findById(id);
			alabaoTurnOut.setTurnOutStatus(turnOutStatus);
			alabaoTurnOut.setUpdateId(user.getId());
			this.alabaoTurnOutService.update(alabaoTurnOut);
			/** 审核不通过将金额退回到如意消费卡 */
			if (AlabaoTurnToBankEnum.TURN_BANK_AUDIT_FAIL.getCode().equalsIgnoreCase(turnOutStatus)) {
				String serialNum =StringUtils.isBlank(alabaoTurnOut.getSerialNum())?GetSerialNumUtil.generateSerialNum():alabaoTurnOut.getSerialNum();
				AlabaoShiftToRecord alabaoShiftToRecord = new AlabaoShiftToRecord();
				alabaoShiftToRecord.setAccount(alabaoTurnOut.getAccount());
				alabaoShiftToRecord.setMemberId(alabaoTurnOut.getMemberId());
				alabaoShiftToRecord.setTransferMoney(alabaoTurnOut.getTransferMoney());
				alabaoShiftToRecord.setShiftToAccountType(AlabaoPayTypeConstant.ALABAO_APP_REFUND);
				alabaoShiftToRecord.setSerialNum(serialNum);
				alabaoShiftToRecord.setMemo("提现转出到银行卡，审核不通过退款");
				
				AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(alabaoTurnOut.getAccount());
				
				alabaoShiftToRecord.setAfterChangeMoney(alabaoAccount.getBalance().add(alabaoTurnOut.getTransferMoney()));
				alabaoShiftToRecord.setBeforeChangeMoney(alabaoAccount.getBalance());
				alabaoShiftToRecordService.add(alabaoShiftToRecord);
				
				alabaoAccount.setBalance(alabaoAccount.getBalance().add(alabaoTurnOut.getTransferMoney()));
				alabaoAccountService.updateByAccount(alabaoAccount);
			}
			/**交易完成要更改一下 转出的状态*/
			if(AlabaoTurnToBankEnum.TURN_BANK_SUCCESS.getCode().equals(turnOutStatus)){
				AlabaoRollOffRecord rollOffRecord = alabaoRollOffRecordService.findBySerialNum(alabaoTurnOut.getSerialNum());
				rollOffRecord.setIsSuccess(CommonConstant.YES);
				this.alabaoRollOffRecordService.update(rollOffRecord);
			}
			//this.alabaoTurnOutService.updateStatus(turnOutStatus, id);
			addMessage(redirectAttributes, "修改转出申请状态成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("修改转出申请状态失败", e);
			return "";
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("alabaoTurnOutListExport")
	public void alabaoTurnOutListExport(
			@RequestParam(value = "createTimeFrom", defaultValue = "") String createTimeFrom,
			@RequestParam(value = "createTimeTo", defaultValue = "") String createTimeTo,
			@RequestParam(value = "account", defaultValue = "") String account,
			@RequestParam(value = "cardHolder", defaultValue = "") String cardHolder,
			@RequestParam(value = "turnOutStatus", defaultValue = "") String turnOutStatus,
			HttpServletResponse response,BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("createTimeFrom", createTimeFrom.trim());
			params.put("createTimeTo", createTimeTo.trim());
			params.put("account", account.trim());
			params.put("cardHolder", cardHolder.trim());
			params.put("turnOutStatus", turnOutStatus.trim());
			params.put("isExportUse", "y");
			page.setParams(params);
			page = this.alabaoTurnOutService.getListPage(page);
			List<AlabaoTurnOut> listFindAlls = (List<AlabaoTurnOut>) page.getResult();
			for (AlabaoTurnOut alabaoAccount : listFindAlls) {
				Member member = memberService.findById(alabaoAccount.getMemberId());
				alabaoAccount.setMemberName(member.getLoginName());;
			}
			AlabaoTurnOutExcelReport report = new AlabaoTurnOutExcelReport();
			report.getReport("如意宝金额转出Excel列表", listFindAlls, response);
		} catch (Exception e) {
			logger.error("查询如意宝金额转出Excel列表失败", e);
		}
	}
	
	@ResponseBody
	@RequestMapping("/updateBankBranch")
	public Object updateBankBranch( Long id,String bankBranchName,
			Model model, HttpSession session) {
		JsonResponseModel jsonModel = new JsonResponseModel();
		try {
			AlabaoTurnOut alabaoTurnOut =alabaoTurnOutService.findById(id);
			alabaoTurnOut.setBankBranchName(bankBranchName);
			alabaoTurnOutService.update(alabaoTurnOut);
			jsonModel.Success("修改银行支行成功!");
		} catch (Exception e) {
			logger.error("修改银行支行失败", e);
			jsonModel.Fail("修改银行支行失败!");
		}
		return jsonModel;
	}
	
	@RequestMapping("goToAuditFail")
	public String goToAdit(@RequestParam(value="id")Long id,ModelMap model){
		AlabaoTurnOut alabaoTurnOut =alabaoTurnOutService.findById(id);
		model.addAttribute("alabaoTurnOut", alabaoTurnOut);
		return PRE_+"updateStatusToFail";
	}
	
	
	@ResponseBody
	@RequestMapping("updateStatusToFail")
	public Object updateToFail(String jsonStr){
		try {
			Gson gson = new Gson();
			AlabaoTurnOut alabaoTurnOut = gson.fromJson(jsonStr, new TypeToken<AlabaoTurnOut>(){}.getType());
			if(alabaoTurnOut.getId() != null){
				alabaoTurnOut = alabaoTurnOutService.findById(alabaoTurnOut.getId());
				alabaoTurnOut.setMemo("审核不通过原因："+alabaoTurnOut.getMemo());
				alabaoTurnOut.setTurnOutStatus("3");
				alabaoTurnOutService.update(alabaoTurnOut);
				
				//审核不通过，需要更新转出记录列表
				String serialNum =StringUtils.isBlank(alabaoTurnOut.getSerialNum())?GetSerialNumUtil.generateSerialNum():alabaoTurnOut.getSerialNum();
				AlabaoShiftToRecord alabaoShiftToRecord = new AlabaoShiftToRecord();
				alabaoShiftToRecord.setAccount(alabaoTurnOut.getAccount());
				alabaoShiftToRecord.setMemberId(alabaoTurnOut.getMemberId());
				alabaoShiftToRecord.setTransferMoney(alabaoTurnOut.getTransferMoney());
				alabaoShiftToRecord.setShiftToAccountType(AlabaoPayTypeConstant.ALABAO_APP_REFUND);
				alabaoShiftToRecord.setSerialNum(serialNum);
				alabaoShiftToRecord.setMemo("提现转出到银行卡，审核不通过退款");
				
				//审核不通过，需要将金额加上到如意宝账户中
				AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(alabaoTurnOut.getAccount());
				
				alabaoShiftToRecord.setAfterChangeMoney(alabaoAccount.getBalance().add(alabaoTurnOut.getTransferMoney()));
				alabaoShiftToRecord.setBeforeChangeMoney(alabaoAccount.getBalance());
				alabaoShiftToRecordService.add(alabaoShiftToRecord);
				
				alabaoAccount.setBalance(alabaoAccount.getBalance().add(alabaoTurnOut.getTransferMoney()));
				alabaoAccountService.updateByAccount(alabaoAccount);
				
				//发送短信通知用户，提现申请未通过
				String smsContent=this.smsTypeConfigService.findById(SmsConstant.SMS_TYPE_USERDEFINED_ID).getSmsTemplate();
				smsContent = smsContent.replace("[Account]", alabaoTurnOut.getAccount()).replace("[memo]", alabaoTurnOut.getMemo());
				smsService.sendSmsUserDefined(alabaoTurnOut.getAccount(), smsContent, SourceConstant.CMS_CODE);
				
			}
		} catch (Exception e) {
			logger.error("响应前端页面审核阿拉宝转账不通过失败，失败原因：", e);
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
	}
}
