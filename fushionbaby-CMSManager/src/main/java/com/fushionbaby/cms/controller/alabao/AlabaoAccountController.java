package com.fushionbaby.cms.controller.alabao;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
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
import com.aladingshop.alabao.model.AlabaoAccountBank;
import com.aladingshop.alabao.model.AlabaoConsumeRecord;
import com.aladingshop.alabao.model.AlabaoIncomeRecord;
import com.aladingshop.alabao.model.AlabaoRollOffRecord;
import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.aladingshop.alabao.model.AlabaoTurnOut;
import com.aladingshop.alabao.model.AlabaoTurnToAlabao;
import com.aladingshop.alabao.service.AlabaoAccountBankService;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoConsumeRecordService;
import com.aladingshop.alabao.service.AlabaoIncomeRecordService;
import com.aladingshop.alabao.service.AlabaoRollOffRecordService;
import com.aladingshop.alabao.service.AlabaoShiftToRecordService;
import com.aladingshop.alabao.service.AlabaoTurnOutService;
import com.aladingshop.alabao.service.AlabaoTurnToAlabaoService;
import com.aladingshop.alabao.vo.AlabaoBillDto;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.controller.alabao.excel.AlabaoAccountExcelReport;
import com.fushionbaby.cms.controller.util.SortGroupBillUtil;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.constants.VerificationConstant;
import com.fushionbaby.common.enums.alabao.AlabaoShiftTypeEnum;
import com.fushionbaby.common.enums.alabao.AlabaoTurnOutEnum;
import com.fushionbaby.common.enums.alabao.AlabaoTurnToBankEnum;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberExtraInfo;
import com.fushionbaby.member.model.MemberTelephone;
import com.fushionbaby.member.service.MemberExtraInfoService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.member.service.MemberTelephoneService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;

@Controller
@RequestMapping("/alabaoAccount")
public class AlabaoAccountController extends BaseController {

	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	
	@Autowired
	private MemberService<Member> memberService;
	
	@Autowired
	private AlabaoTurnOutService<AlabaoTurnOut> alabaoTurnOutService;
	@Autowired
	private AlabaoIncomeRecordService<AlabaoIncomeRecord> alabaoIncomeRecordService;
	
	@Autowired
	private AlabaoRollOffRecordService<AlabaoRollOffRecord> alabaoRollOffRecordService;
	@Autowired
	private AlabaoConsumeRecordService<AlabaoConsumeRecord> alabaoConsumeRecordService;
	@Autowired
	private AlabaoShiftToRecordService<AlabaoShiftToRecord> alabaoShiftToRecordService;
	@Autowired
	private AlabaoTurnToAlabaoService<AlabaoTurnToAlabao> alabaoTurnToAlabaoService;
	@Autowired
	private AlabaoAccountBankService<AlabaoAccountBank> alabaoAccountBankService;
	
	@Autowired
	private MemberTelephoneService<MemberTelephone> memberTelephoneService;
	@Autowired
	private MemberExtraInfoService<MemberExtraInfo> memberExtraInfoService;
	
	@Autowired
	private SmsService<Sms> smsService;
	
	private static final Log logger = LogFactory.getLog(AlabaoAccountController.class);
	
	private static final String PRE_="models/alabao/account/";
	
	
	/**列表页*/
	private static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/alabaoAccount/accountList";
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("accountList")
	public String findAll(
			@RequestParam(value = "account", defaultValue = "") String account,
			@RequestParam(value = "trueName", defaultValue = "") String trueName,
			@RequestParam(value = "identityCardNo", defaultValue = "") String identityCardNo,
			@RequestParam(value = "createTimeFrom", defaultValue = "") String createTimeFrom,
			@RequestParam(value = "createTimeTo", defaultValue = "") String createTimeTo,
			@RequestParam(value = "mobilePhone", defaultValue = "") String mobilePhone,
			@RequestParam(value = "balanceTo", defaultValue = "") String balanceTo,
			@RequestParam(value = "balanceFrom", defaultValue = "") String balanceFrom,
			@RequestParam(value = "level", defaultValue = "") String level,
			@RequestParam(value = "status", defaultValue = "") String status,
			BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("level", StringUtils.trimToEmpty(level));
			params.put("status", StringUtils.trimToEmpty(status));
			params.put("account", account.trim());
			params.put("trueName", trueName.trim());
			params.put("identityCardNo", identityCardNo.trim());
			params.put("createTimeFrom", createTimeFrom.trim());
			params.put("createTimeTo", createTimeTo.trim());
			params.put("mobilePhone", mobilePhone.trim());
			params.put("balanceTo", balanceTo.trim());
			params.put("balanceFrom", balanceFrom.trim());
			page.setParams(params);
			page = this.alabaoAccountService.getListPage(page);
			List<AlabaoAccount> listFindAlls = (List<AlabaoAccount>) page.getResult();
			List<AlabaoAccount> listFindAll = new ArrayList<AlabaoAccount>();
			for (AlabaoAccount alabaoAccount : listFindAlls) {
				Member member = memberService.findById(alabaoAccount.getMemberId());
				if(member!=null){
					alabaoAccount.setMemberName(member.getLoginName());
				}
				listFindAll.add(alabaoAccount);
			}
			
			model.addAttribute("list", listFindAll);
			model.addAttribute("page", page);
			model.addAttribute("account", account);
			model.addAttribute("trueName", trueName);
			model.addAttribute("level", level);
			model.addAttribute("status", status);
			model.addAttribute("identityCardNo", identityCardNo);
			model.addAttribute("createTimeFrom", createTimeFrom);
			model.addAttribute("createTimeTo", createTimeTo);
			model.addAttribute("mobilePhone", mobilePhone);
			model.addAttribute("balanceTo", balanceTo);
			model.addAttribute("balanceFrom", balanceFrom);
			return PRE_+"accountList";
		} catch (Exception e) {
			logger.error("查询权限列表失败", e);
			return "";
		}
	}
	/**
	 * 验证该账号是否存在
	 * @param mobilePhone 手机 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("exitAccount")
	public Object exitAccount(@RequestParam(value = "mobilePhone", defaultValue = "") String mobilePhone){
		
		AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(mobilePhone);
		if(alabaoAccount==null){
			return true;
		}
		
		
		return false;
	}
	
	/**
	 * 进入编辑账号页面
	 * @param model
	 * @return
	 */
	@RequestMapping("editAccountPage")
	public String editAccountPage(Model model){
		
		return "models/alabao/account/accountAdd";
	}
	
	/**
	 * 删除账户
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("deleteAccount")
	public String deleteAccount(Long id,Model model){
		
		if(null==id){
			addMessage(model, "删除失败！");
		}
		AlabaoAccount alabaoAccount = alabaoAccountService.findById(id);
		alabaoAccountService.deleteById(alabaoAccount.getId());
		addMessage(model, "删除成功！");
		return REDIRECT_LIST;
	}
	
	
	/**
	 * 添加账户
	 * @param mobilePhone
	 * @param loginPassword
	 * @param payPassword
	 * @param identityCardNo
	 * @param trueName
	 * @param level
	 * @param model
	 * @return
	 */
	@RequestMapping("addAccount")
	public String addAccount(
			@RequestParam("mobilePhone") String mobilePhone,
			@RequestParam("loginPassword")String loginPassword,
			@RequestParam("payPassword") String payPassword,
			@RequestParam("identityCardNo") String identityCardNo,
			@RequestParam("trueName") String trueName,
			@RequestParam("level") String level,
			@RequestParam("isInform") String isInform,RedirectAttributes redirectAttributes){
		
		Member member = memberService.findByLoginName(mobilePhone);
		
		if(member==null){
			/**添加到会员表*/
			member = memberService.addMember(mobilePhone,loginPassword,SourceConstant.OPERATE_CODE,request);
			/**添加到会员手机表*/
			memberTelephoneService.addMemberPhone(member.getId(), mobilePhone);
			/**添加到额外会员表*/
			memberExtraInfoService.addMemberExtraInfo(member.getId());
		}
		AlabaoAccount alabaoAccount = new AlabaoAccount();
		alabaoAccount.setMemberId(member.getId());
		alabaoAccount.setAccount(mobilePhone);
		alabaoAccount.setMobilePhone(mobilePhone);
		alabaoAccount.setTrueName(trueName);
		alabaoAccount.setIdentityCardNo(identityCardNo);
		alabaoAccount.setPayPassword(MD5Util.MD5(payPassword+MD5Util.getKey()));
		alabaoAccount.setLoginPassword(MD5Util.MD5(loginPassword+MD5Util.getKey()));
		alabaoAccount.setStatus("1");//默认待审核状态
		alabaoAccount.setSourceCode(SourceConstant.OPERATE_CODE);
		alabaoAccount.setLevel(level);
		alabaoAccount.setIsValidate(VerificationConstant.IS_VALIDATE_STATUS_NULL);
		
		
		BeanNullConverUtil.nullConver(alabaoAccount);
		alabaoAccount.setId(null);
		try {
			alabaoAccountService.add(alabaoAccount);
			if(StringUtils.isNotBlank(isInform) && StringUtils.equals(isInform, CommonConstant.YES)){
				smsService.sendSmsRegisterMessage(mobilePhone, loginPassword, SourceConstant.OPERATE_CODE);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "注册成功,短信发送失败！");
		}catch(Exception ex){
			ex.printStackTrace();
			addMessage(redirectAttributes, "注册失败");
			return "models/alabao/account/accountAdd";
		}
		
		addMessage(redirectAttributes, "注册成功！");
		
		return REDIRECT_LIST;
	}
	
	/**
	 * 修改状态
	 * @param id
	 * @param status
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("updateStatus/{id}/{status}")
	public String updateStatus(@PathVariable("id") Long id,
			@PathVariable("status") String status,
			RedirectAttributes redirectAttributes){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		this.alabaoAccountService.updateStatusById(map);
		addMessage(redirectAttributes, "状态修改成功");
		return REDIRECT_LIST;
	}
	
	@RequestMapping("alabaoInOutList/{account}")
	public String alabaoInOutList(
			@PathVariable(value = "account") String account,
			Model model) {
		try {
			AlabaoAccount alabaoAccount=this.alabaoAccountService.findByAccount(account);
			Member member = memberService.findById(alabaoAccount.getMemberId());
			String memberName=member.getLoginName();
			model.addAttribute("memberName", memberName);
			
			model.addAttribute("account", account);
			List<AlabaoRollOffRecord> listRollOffAll= this.alabaoRollOffRecordService.findAllByAccount(account);
			model.addAttribute("listRollOffAll", listRollOffAll);
			
			List<AlabaoShiftToRecord> listShiftToAll = this.alabaoShiftToRecordService.findAllByAccount(account);
			model.addAttribute("listShiftToAll", listShiftToAll);
			
			return PRE_+"alabaoInOutList";
		} catch (Exception e) {
			logger.error("查询阿拉宝转入转出列表失败", e);
			return "";
		}
	}
	
	@RequestMapping("toUpdateAccount/{id}/{time}")
	public String toUpdateAccount(@PathVariable("id") Long id, Model model) {
		try {
			AlabaoAccount alabaoAccount = this.alabaoAccountService.findById(id);
			model.addAttribute("alabaoAccount", alabaoAccount);
			return PRE_+"accountUpdate";
		} catch (Exception e) {
			logger.error("修改阿拉丁账户失败", e);
			return "";
		}
	}
	
	@ResponseBody
	@RequestMapping("updateAccount")
	public Object updateAccount(@RequestParam("account") String account,@RequestParam("trueName") String trueName,@RequestParam("identityCardNo") String identityCardNo) {
		try {
			AlabaoAccount alabaoAccount = this.alabaoAccountService
					.findByAccount(account);
			alabaoAccount.setTrueName(trueName);
			alabaoAccount.setIdentityCardNo(identityCardNo);
			alabaoAccountService.updateByAccount(alabaoAccount);
			return Jsonp.success();
		} catch (Exception e) {
			logger.error("修改阿拉丁账户失败", e);
			return Jsonp.error();
		}
	}
	
	@RequestMapping("export_account_list")
	public void exportExcelOrderList(
			@RequestParam(value = "account", defaultValue = "") String account,
			@RequestParam(value = "identityCardNo", defaultValue = "") String identityCardNo,
			@RequestParam(value = "createTimeFrom", defaultValue = "") String createTimeFrom,
			@RequestParam(value = "createTimeTo", defaultValue = "") String createTimeTo,
			@RequestParam(value = "mobilePhone", defaultValue = "") String mobilePhone,
			@RequestParam(value = "balanceTo", defaultValue = "") String balanceTo,
			@RequestParam(value = "balanceFrom", defaultValue = "") String balanceFrom,
			@RequestParam(value = "level", defaultValue = "") String level,
			@RequestParam(value = "status", defaultValue = "") String status,
			HttpServletResponse response, BasePagination page) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("level", StringUtils.trimToEmpty(level));
			params.put("account", account.trim());
			params.put("identityCardNo", identityCardNo.trim());
			params.put("createTimeFrom", createTimeFrom.trim());
			params.put("createTimeTo", createTimeTo.trim());
			params.put("mobilePhone", mobilePhone.trim());
			params.put("balanceTo", balanceTo.trim());
			params.put("balanceFrom", balanceFrom.trim());
			params.put("status", status.trim());
			params.put("isExportUse", CommonConstant.YES);
			page.setParams(params);
			page = this.alabaoAccountService.getListPage(page);
			
			@SuppressWarnings("unchecked")
			List<AlabaoAccount> listFindAll = (List<AlabaoAccount>) page.getResult();
			for (AlabaoAccount alabaoAccount : listFindAll) {
				Member member = memberService.findById(alabaoAccount.getMemberId());
				if(member!=null){
					alabaoAccount.setMemberName(member.getLoginName());
				}
				
			}
			
			AlabaoAccountExcelReport report = new AlabaoAccountExcelReport();
			report.getReport("如意宝会员列表Excel", listFindAll, response);
		} catch (Exception e) {
			logger.error("如意宝会员列表导出Excel失败", e);
		}
	}
	
	
	/**账单列表*/
	@SuppressWarnings("unchecked")
	@RequestMapping("listAccountDetail/{id}/{time}")
	public String listAccountDetail(@PathVariable(value="id")Long id,ModelMap model){
		
		try {
			AlabaoAccount alabaoAccount = alabaoAccountService.findById(id);
			Long memberId = alabaoAccount.getMemberId();
			//用户银行信息
			List<AlabaoAccountBank> alabaoAccountBankList = alabaoAccountBankService.findByMemberId(memberId);
			//转出记录表
			List<AlabaoRollOffRecord> alabaoRollOffRecordList = alabaoRollOffRecordService.findAllByAccount(alabaoAccount.getAccount());
			//转出总金额
			BigDecimal totalRollOff = new BigDecimal(0);
			for (AlabaoRollOffRecord alabaoRollOffRecord : alabaoRollOffRecordList) {
				
				if(alabaoRollOffRecord.getIsSuccess().equals("y")){
					totalRollOff = alabaoRollOffRecord.getTransferMoney().add(totalRollOff);
				}
			}
			//转入记录表
			List<AlabaoShiftToRecord> alabaoShiftToRecordlist = alabaoShiftToRecordService.findAllByAccount(alabaoAccount.getAccount());
			//转入总金额
			BigDecimal totalShiftTo = new BigDecimal(0);
			for (AlabaoShiftToRecord alabaoShiftToRecord : alabaoShiftToRecordlist) {
				totalShiftTo = alabaoShiftToRecord.getTransferMoney().add(totalShiftTo);
			}
			
			//消费记录表
			List<AlabaoConsumeRecord> alabaoConsumeRecordList = alabaoConsumeRecordService.findAllByAccount(alabaoAccount.getAccount());
			//消费总金额
			BigDecimal totalConsume = new BigDecimal(0);
			for (AlabaoConsumeRecord alabaoConsumeRecord : alabaoConsumeRecordList) {
				totalConsume = alabaoConsumeRecord.getConsumeMoney().add(totalConsume);
			}
			
			
			List<AlabaoShiftToRecord> shiftToRecordList = alabaoShiftToRecordService.findBillRecordByTime(alabaoAccount.getAccount());
			List<AlabaoRollOffRecord> rollOffRecordList = alabaoRollOffRecordService.findBillRecordByTime(alabaoAccount.getAccount());
			List<AlabaoConsumeRecord> consumeRecordList = alabaoConsumeRecordService.findBillRecordByTime(alabaoAccount.getAccount());
			List<AlabaoIncomeRecord> incomeRecordList = alabaoIncomeRecordService.findBillRecordByTime(alabaoAccount.getAccount());
			List<AlabaoBillDto> billList = new ArrayList<AlabaoBillDto>();
			/**转入账单记录*/
			for (AlabaoShiftToRecord alabaoShiftToRecord : shiftToRecordList) {
				AlabaoBillDto alabaoBillDto = new AlabaoBillDto();
				String account = "";
				String memo = "";
				if (StringUtils.equalsIgnoreCase(alabaoShiftToRecord.getShiftToAccountType(), AlabaoShiftTypeEnum.ALABAO.getCode())) {
					AlabaoTurnToAlabao turnToAlabao = alabaoTurnToAlabaoService.findBySerialNum(alabaoShiftToRecord.getSerialNum());
					if (ObjectUtils.notEqual(turnToAlabao, null)) {
						AlabaoAccount otherAccount = alabaoAccountService.findByAccount(turnToAlabao.getAccount());
						account = otherAccount.getTrueName()+"("+turnToAlabao.getAccount()+")";
						memo = turnToAlabao.getMemo();
					}
				}
				alabaoBillDto.setAccount(account);
				alabaoBillDto.setCreateTime(DateFormat.dateTimeToDateString(alabaoShiftToRecord.getCreateTime()));
				alabaoBillDto.setTradeMoney("+"+alabaoShiftToRecord.getTransferMoney());
				alabaoBillDto.setTradeStatus("转入成功");
				alabaoBillDto.setTradeType(AlabaoShiftTypeEnum.parseCode(alabaoShiftToRecord.getShiftToAccountType()));
				alabaoBillDto.setTurnOutType(StringUtils.EMPTY);
				alabaoBillDto.setReason(StringUtils.EMPTY);
				alabaoBillDto.setRemark(memo);
				billList.add(alabaoBillDto);
			}
			/**转出账单记录-银行卡和如意消费卡*/
			for (AlabaoRollOffRecord alabaoRollOffRecord : rollOffRecordList) {
				AlabaoBillDto alabaoBillDto = new AlabaoBillDto();
				String account = "";
				String tradeStatus = "转出成功";
				String turnOutType = "";
				String memo = "";
				if (StringUtils.equalsIgnoreCase(alabaoRollOffRecord.getRollOffAccountType(), AlabaoShiftTypeEnum.ALABAO.getCode())) {
					AlabaoTurnToAlabao turnToAlabao = alabaoTurnToAlabaoService.findBySerialNum(alabaoRollOffRecord.getSerialNum());
					if (ObjectUtils.notEqual(turnToAlabao, null)) {
						AlabaoAccount otherAccount = alabaoAccountService.findByAccount(turnToAlabao.getOtherAccount());
						account = otherAccount.getTrueName()+"("+turnToAlabao.getOtherAccount()+")";
						turnOutType = AlabaoTurnOutEnum.TURN_TO_ALABAO.getCode();
						memo = turnToAlabao.getMemo();
					}
				} else {
					AlabaoTurnOut alabaoTurnOut = alabaoTurnOutService.findBySerialNum(alabaoRollOffRecord.getSerialNum());
					if (ObjectUtils.notEqual(alabaoTurnOut, null)) {
						String cardNo = alabaoTurnOut.getCardNo();
						account = alabaoTurnOut.getCardHolder()+"("+alabaoTurnOut.getBankName()+cardNo.substring(cardNo.length()-4,cardNo.length())+")";
						tradeStatus = AlabaoTurnToBankEnum.parseCode(alabaoTurnOut.getTurnOutStatus());
						turnOutType = AlabaoTurnOutEnum.TURN_TO_BANK.getCode();
						memo = alabaoTurnOut.getMemo();
					}
				}
				alabaoBillDto.setAccount(account);
				alabaoBillDto.setCreateTime(DateFormat.dateTimeToDateString(alabaoRollOffRecord.getCreateTime()));
				alabaoBillDto.setTradeMoney("-"+alabaoRollOffRecord.getTransferMoney());
				alabaoBillDto.setTradeStatus(tradeStatus);
				alabaoBillDto.setTradeType("转出");
				alabaoBillDto.setTurnOutType(AlabaoTurnOutEnum.parseCode(turnOutType));
				
				alabaoBillDto.setReason(StringUtils.EMPTY);
				alabaoBillDto.setRemark(memo);
				billList.add(alabaoBillDto);
			}
			/**消费记录*/
			for (AlabaoConsumeRecord alabaoConsumeRecord : consumeRecordList) {
				AlabaoBillDto alabaoBillDto = new AlabaoBillDto();
				alabaoBillDto.setAccount("");
				alabaoBillDto.setCreateTime(DateFormat.dateTimeToDateString(alabaoConsumeRecord.getCreateTime()));
				alabaoBillDto.setTradeMoney("-"+alabaoConsumeRecord.getConsumeMoney());
				alabaoBillDto.setTradeStatus("消费成功");
				alabaoBillDto.setTradeType("消费");
				alabaoBillDto.setTurnOutType(StringUtils.EMPTY);
				alabaoBillDto.setReason(StringUtils.EMPTY);
				alabaoBillDto.setRemark("订单号("+alabaoConsumeRecord.getOrderCode()+")");
				billList.add(alabaoBillDto);
			}
			/**收益记录*/
			for (AlabaoIncomeRecord alabaoIncomeRecord : incomeRecordList) {
				AlabaoBillDto alabaoBillDto = new AlabaoBillDto();
				alabaoBillDto.setAccount("");
				alabaoBillDto.setCreateTime(DateFormat.dateTimeToDateString(alabaoIncomeRecord.getCreateTime()));
				alabaoBillDto.setTradeMoney("+"+alabaoIncomeRecord.getIncomeMoney());
				alabaoBillDto.setTradeStatus("收券");
				alabaoBillDto.setTradeType("收券");
				alabaoBillDto.setTurnOutType(StringUtils.EMPTY);
				alabaoBillDto.setReason(StringUtils.EMPTY);
				alabaoBillDto.setRemark(StringUtils.EMPTY);
				billList.add(alabaoBillDto);
			}
			Collections.sort(billList,new SortGroupBillUtil());
			
			
			model.addAttribute("billList", billList);
			model.addAttribute("alabaoAccountBankList", alabaoAccountBankList);
			model.addAttribute("totalRollOff", totalRollOff);
			model.addAttribute("totalConsume", totalConsume);
			model.addAttribute("totalShiftTo", totalShiftTo);
			model.addAttribute("alabaoAccount", alabaoAccount);
		} catch (Exception e) {
			logger.error("查询消费列表失败", e);
			return "";
		}
        
		return "models/alabao/account/accountDetail";
		
	}
	
	/**手动更新阿拉宝账户认证状态*/
	@RequestMapping("updateAccountIsValidate/{id}/{validateStatus}/{time}")
	public String updateAccountIsValidate(@PathVariable(value="id")Long id,@PathVariable(value="validateStatus")String validateStatus){
		
		try {
			AlabaoAccount alabaoAccount = alabaoAccountService.findById(id);
			alabaoAccount.setIsValidate(validateStatus);
			alabaoAccountService.updateByAccount(alabaoAccount);
		} catch (Exception e) {
			logger.error("修改阿拉宝账户认证状态失败！", e);
			return "";
		}
		
		return REDIRECT_LIST;
	}
	
	
}
