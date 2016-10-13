/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年11月20日下午1:18:56
 */
package com.fushionbaby.cms.controller.alabao;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoRollOffRecord;
import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.aladingshop.alabao.model.AlabaoTurnToAlabao;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoRollOffRecordService;
import com.aladingshop.alabao.service.AlabaoShiftToRecordService;
import com.aladingshop.alabao.service.AlabaoTurnToAlabaoService;
import com.aladingshop.alabao.vo.AlabaoAccountDto;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.util.excel.ImportExcel;
import com.fushionbaby.common.constants.AlabaoPayTypeConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.GetSerialNumUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.model.SmsTypeConfig;
import com.fushionbaby.sms.service.SmsService;
import com.fushionbaby.sms.service.SmsTypeConfigService;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年11月20日下午1:18:56
 */
@Controller
@RequestMapping("alabaoFinace")
public class AlabaoFinaceController extends BaseController {

	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	
	@Autowired
	private MemberService<Member> memberService;
	
	@Autowired
	private AlabaoRollOffRecordService<AlabaoRollOffRecord> alabaoRollOffRecordService;
	@Autowired
	private AlabaoTurnToAlabaoService<AlabaoTurnToAlabao> turnToAlabaoService;
	@Autowired
	private AlabaoShiftToRecordService<AlabaoShiftToRecord> alabaoShiftToRecordService;
	@Autowired
	private SmsService<Sms> smsService;
	/**短信类型*/
	@Autowired
	private SmsTypeConfigService<SmsTypeConfig> smsTypeConfigService;
	
	
	private static final String PRE_="models/alabao/finance/";
	/**
	 * 查询全部
	 * @param account
	 * @param identityCardNo
	 * @param createTimeFrom
	 * @param createTimeTo
	 * @param mobilePhone
	 * @param balanceTo
	 * @param balanceFrom
	 * @param level
	 * @param page
	 * @param model
	 * @return
	 */
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
			BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("level", StringUtils.trimToEmpty(level));
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
			model.addAttribute("level", level);
			model.addAttribute("trueName", trueName);
			model.addAttribute("identityCardNo", identityCardNo);
			model.addAttribute("createTimeFrom", createTimeFrom);
			model.addAttribute("createTimeTo", createTimeTo);
			model.addAttribute("mobilePhone", mobilePhone);
			model.addAttribute("balanceTo", balanceTo);
			model.addAttribute("balanceFrom", balanceFrom);
			return PRE_+"accountFinaceList";
		} catch (Exception e) {
			logger.error("查询权限列表失败", e);
			return "";
		}
	}
	/**
	 * 编辑账户等级
	 * @param account
	 * @param level
	 * @return
	 */
	@ResponseBody
	@RequestMapping("editLevel")
	public Object editLevel(String account,String level){
		
		AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(account);
		if(alabaoAccount==null){
			return false;
		}
		
		alabaoAccount.setLevel(level);
		alabaoAccountService.updateByMemberId(alabaoAccount);
		
		return true;
	}
	
	/**
	 * 转入页面
	 * @param account
	 * @param model
	 * @return
	 */
	@RequestMapping("shiftToPage")
	public Object shiftToPage(String account,Model model){
		
		AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(account);
		
		if(alabaoAccount==null){
			return "redirect:"+Global.getAdminPath()+"/alabaoFinace/accountList";
		}
		final String level = "9";
		List<AlabaoAccount> companyAccountList = alabaoAccountService.findByLevel(level);
		
		model.addAttribute("companyAccountList", companyAccountList);
		model.addAttribute("alabaoAccount", alabaoAccount);
		model.addAttribute("smsTypeList", smsTypeConfigService.findAll());
		
		return "models/alabao/finance/accoutShiftTo";
	}
	
	@RequestMapping("editBalancePage")
	public Object editBalancePage(String account,Model model){
		
		AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(account);
		
		if(alabaoAccount==null){
			return "redirect:"+Global.getAdminPath()+"/alabaoFinace/accountList";
		}
		model.addAttribute("alabaoAccount", alabaoAccount);
		return "models/alabao/finance/editBalance";
	}
	
	@ResponseBody
	@RequestMapping("editBalance")
	public Object editBalance(String account,BigDecimal lockedBalance,BigDecimal balance){
		
		
		AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(account);
		
		if(alabaoAccount==null){
			return false;
		}
		
		alabaoAccount.setLockedBalance(lockedBalance);
		alabaoAccount.setBalance(balance);
		alabaoAccountService.updateByMemberId(alabaoAccount);
		return true;
	}
	
	/**
	 * 转入账户金额
	 * @param account 帐户名
	 * @param amount 转入金额
	 * @param 描述
	 * @return
	 */
	@ResponseBody
	@RequestMapping("shiftToAccout")
	public Jsonp shiftToAccout(@RequestParam(value="companyAccount",defaultValue="") String companyAccount,
			@RequestParam(value="memo",defaultValue="") String memo,String smsTypeId,
			String account,BigDecimal amount){
		
		AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(account);
		if(alabaoAccount==null){
			return Jsonp.error("该账户不存在！");
		}
		
		if(StringUtils.equals(companyAccount,account)  ){
			 if(this.shift(alabaoAccount,alabaoAccount, amount,memo)){
				 return Jsonp.newInstance("0", "公司帐户余额转入成功");
			 }
		}
		AlabaoAccount companyAccountEntry = alabaoAccountService.findByAccount(companyAccount);
		if(companyAccountEntry==null){
			return Jsonp.error("公司帐户不存在");
		}
		BigDecimal companyBalance  =  companyAccountEntry.getBalance();
		if(companyBalance.compareTo(amount)==-1){
			return Jsonp.error("该公司账户金额不足。");
		}
		if(BigDecimal.valueOf(20000).compareTo(amount)==-1){
			return Jsonp.error("每次转入金额不能超过20000。");
		}
		companyAccountEntry.setBalance(companyBalance.subtract(amount));
		if(this.shift(companyAccountEntry,alabaoAccount, amount,memo)){
			alabaoAccountService.updateByMemberId(companyAccountEntry);
		}
	/*	try {
			if(StringUtils.isNotBlank(isInform) && StringUtils.equals(isInform, CommonConstant.YES)){
				smsService.tradeNotifyMessage(companyAccount, account, SourceConstant.CMS_CODE, amount.toString());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}*/
		this.sendSmsMessage(account,companyAccount,amount,Long.valueOf(smsTypeId));
		return Jsonp.success();
	}
	
	/***
	 * 
	 * @param account
	 * @param companyAccount
	 * @param amount
	 */
	private void sendSmsMessage(String account, String companyAccount,BigDecimal amount,Long smsTypeId) {
		try {
			AlabaoAccount account1=this.alabaoAccountService.findByAccount(account);
			AlabaoAccount account2=this.alabaoAccountService.findByAccount(companyAccount);
			SmsTypeConfig smsType=this.smsTypeConfigService.findById(Long.valueOf(smsTypeId));
			String content=smsType.getSmsTemplate();
			String time=DateFormat.dateToString(new Date());
			//替换code
			content=content.replace(SmsConstant.SMS_TEMPLATE_CODE, account.substring(7,11));
			//替换account
			content=content.replace(SmsConstant.SMS_TEMPLATE_ALABAO_ACCOUNT, account2.getTrueName());
			//替换time
			content=content.replace(SmsConstant.SMS_TEMPLATE_TIME, time);
			//替换金额
			content=content.replace(SmsConstant.SMS_TEMLATE_FACEVALUE, amount.toString());
			//替换余额
			content=content.replace(SmsConstant.SMS_TEMPLATE_ACCOUNT_BALANCE, account1.getBalance().toString());
			smsService.sendSmsPromotion(account, content, SourceConstant.CMS_CODE);	
		} catch (Exception e) {
			  logger.error("转入通知短信异常", e);
		}
		
			
			
		
	}
	private boolean shift(AlabaoAccount companyAccount,AlabaoAccount alabaoAccount,BigDecimal amount,String memo){
		String batchNum = System.currentTimeMillis() + StringUtils.EMPTY;
		try {
			BigDecimal newBalance = alabaoAccount.getBalance().add(amount);
			this.record(companyAccount, alabaoAccount, amount,batchNum,memo);
			
			alabaoAccount.setBalance(newBalance);
			alabaoAccountService.updateByMemberId(alabaoAccount);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@RequestMapping("importBalancePage")
	public String importBalancePage(Model model){
		
		final String level = "9";
		List<AlabaoAccount> companyAccountList = alabaoAccountService.findByLevel(level);
		
		model.addAttribute("companyAccountList", companyAccountList);
		model.addAttribute("smsTypeList", smsTypeConfigService.findAll());
		return "models/alabao/finance/importExcelBalance";
	}
	
	
	/***
	 * 批量操作如意消费卡的转入转出
	 * @param companyAccountCode  公司如意消费卡编码
	 * @param memo     备注
	 * @param isInform   短信签名标志（1 乾玺  2平台）
	 * @param excelFile  批量操作的文件
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("importBalanceExcel")
//	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public Jsonp importBalanceExcel(@RequestParam("companyAccount") String companyAccountCode,
			@RequestParam(value="memo",defaultValue="") String memo,@RequestParam("smsTypeId")String smsTypeId,
			MultipartFile excelFile, ModelMap model){
		model.addAttribute("info", "导入成功");
		ImportExcel ei = null;
		
		try {
			ei = new ImportExcel(excelFile, 0, 0); 
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			
			return Jsonp.error();
		} catch (IOException e) {
			e.printStackTrace();
			return Jsonp.error();
		}
		boolean flag = true;
		String info = null;
		List<AlabaoAccountDto> alabaoAccountDtoList = new ArrayList<AlabaoAccountDto>();
		
		BigDecimal allAmount = new BigDecimal(0);
		
		for (int i = ei.getDataRowNum(); i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			
			String account = ObjectUtils.toString(ei.getCellValue(row, 0));
			
			String trueName = ObjectUtils.toString(ei.getCellValue(row, 1));
			
			//String identityCardNo = ObjectUtils.toString(ei.getCellValue(row, 2));
			
			String amount =  ObjectUtils.toString(ei.getCellValue(row, 2));
			if(StringUtils.isBlank(account) ){
				break;
			}
			
			AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(account);
			if(alabaoAccount ==null){
				flag = false;
				info = "第"+i+"行记录可能存在异常，账号"+account+"不存在！";
				break;
			}
			if(!StringUtils.equals(trueName, alabaoAccount.getTrueName())){
				flag = false;
				info = "第"+i+"行记录可能存在异常，账号："+account+"的真实姓名不符！（"+trueName+"）";
				break;
			}
//			if(!StringUtils.equals(identityCardNo, alabaoAccount.getIdentityCardNo())){
//				flag = false;
//				break;
//			}
			if(StringUtils.isNotBlank(amount)  && !NumberUtils.isNumber(amount)){
				flag = false;
				info = "第"+i+"行记录可能存在异常，账号："+account+"的金额有误！";
				break;
			}
			AlabaoAccountDto alabaoAccountDto = new AlabaoAccountDto();
			alabaoAccountDto.setAlabaoAccount(alabaoAccount);
			alabaoAccountDto.setTransferAmount(new BigDecimal(amount));
			alabaoAccountDtoList.add(alabaoAccountDto);
			allAmount = allAmount.add(new BigDecimal(amount));
		}
		
		if(!flag){
			return Jsonp.error("Excel中编辑数据有问题，请检查；"+info);
		}
		
		AlabaoAccount companyAlabaoAccount = alabaoAccountService.findByAccount(companyAccountCode);
		if(companyAlabaoAccount== null){
			return Jsonp.error("该转账方账户不存在");
		}
		if(companyAlabaoAccount.getBalance().compareTo(allAmount)==-1){
			return Jsonp.error("转账方的账号余额小于要转入的Excel的全部金额。");
		}
		
		Jsonp result = this.updateAlabaoBalance(companyAlabaoAccount, allAmount, alabaoAccountDtoList,memo);
		if(StringUtils.equals(result.getResponseCode(),"0") && StringUtils.isNotBlank(smsTypeId)){
			
			this.sendInform(companyAlabaoAccount, alabaoAccountDtoList,smsTypeId);
			
		}
		
		return result;
	}
	/**
	 * 发送短信
	 * @param companyAlabaoAccount
	 * @param alabaoAccountDtoList
	 */
	private void sendInform(AlabaoAccount  companyAlabaoAccount,List<AlabaoAccountDto> alabaoAccountDtoList,String smsTypeId){
		
		try {
	       SmsTypeConfig smsType=this.smsTypeConfigService.findById(Long.valueOf(smsTypeId));
			String content=smsType.getSmsTemplate();
			String time=DateFormat.dateToString(new Date());
			for (AlabaoAccountDto alabaoAccountDto : alabaoAccountDtoList) {
				//替换code
				content=content.replace(SmsConstant.SMS_TEMPLATE_CODE, alabaoAccountDto.getAlabaoAccount().getAccount().substring(7,11));
				//替换account
				content=content.replace(SmsConstant.SMS_TEMPLATE_ALABAO_ACCOUNT, companyAlabaoAccount.getTrueName());
				//替换time
				content=content.replace(SmsConstant.SMS_TEMPLATE_TIME, time);
				//替换金额
				content=content.replace(SmsConstant.SMS_TEMLATE_FACEVALUE, alabaoAccountDto.getTransferAmount().toString());
				//替换余额
				content=content.replace(SmsConstant.SMS_TEMPLATE_ACCOUNT_BALANCE, alabaoAccountDto.getAlabaoAccount().getBalance().toString());
				
				smsService.sendSmsPromotion(alabaoAccountDto.getAlabaoAccount().getAccount(), content, SourceConstant.CMS_CODE);	
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改如意消费卡余额
	 * @param companyAlabaoAccount 公司帐户，全部金额
	 * @param allBalance
	 * @param alabaoAccountDtoList
	 */
//	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	private Jsonp updateAlabaoBalance(AlabaoAccount companyAlabaoAccount,BigDecimal allAmount,
			 List<AlabaoAccountDto> alabaoAccountDtoList,String memo) {
		
		String batchNum = System.currentTimeMillis() + StringUtils.EMPTY;
		
		BigDecimal overAmount = allAmount;
		
		Jsonp flag = null;
		
 		for (AlabaoAccountDto alabaoAccountDto : alabaoAccountDtoList) {
			AlabaoAccount alabaoAccount = alabaoAccountDto.getAlabaoAccount();
			
			AlabaoAccount findCompanyAlabaoAccount = alabaoAccountService.findByAccount(companyAlabaoAccount.getAccount());
			if(findCompanyAlabaoAccount.getBalance().compareTo(overAmount)==-1){
				flag = Jsonp.error("添加到账号为："+alabaoAccount.getAccount()+"时，转账方金额不足");
				break;
			}
			overAmount = overAmount.subtract(alabaoAccountDto.getTransferAmount());
			if(overAmount.compareTo(BigDecimal.ZERO)==-1){
				flag = Jsonp.error("添加到账号为："+alabaoAccount.getAccount()+"时，转账方金额不足");
				break;
			}
			if(findCompanyAlabaoAccount.getBalance().compareTo(alabaoAccountDto.getTransferAmount())==-1){
				flag = Jsonp.error("添加到账号为："+alabaoAccount.getAccount()+"时，转账方金额不足");
				break;
			}
			this.record(companyAlabaoAccount, alabaoAccount, alabaoAccountDto.getTransferAmount(),batchNum,memo);
			
			companyAlabaoAccount.setBalance(companyAlabaoAccount.getBalance().subtract(alabaoAccountDto.getTransferAmount()));
			alabaoAccountService.updateByMemberId(companyAlabaoAccount);
			
			alabaoAccount.setBalance(alabaoAccount.getBalance().add(alabaoAccountDto.getTransferAmount()));
			alabaoAccountService.updateByMemberId(alabaoAccount);
		}
		if(flag!=null && !StringUtils.equals(flag.getResponseCode(),"0")){
			return flag;
		}
		return Jsonp.success();
		
	}
	/**
	 * 交易记录
	 * @param companyAccount  公司账号
	 * @param otherAccount 对方账号
	 * @param turnOutAmount 转入金额
	 * @param batchNum 批处理编号
	 */
	
//	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	private void record(AlabaoAccount companyAccount,AlabaoAccount otherAccount,BigDecimal turnOutAmount,String batchNum,String memo){
		String serialNum = GetSerialNumUtil.generateSerialNum();
		if(!StringUtils.equals(companyAccount.getAccount(),otherAccount.getAccount())){
			/**本方转出记录*/
			AlabaoRollOffRecord alabaoRollOffRecord = new AlabaoRollOffRecord();
			alabaoRollOffRecord.setAccount(companyAccount.getAccount());
			alabaoRollOffRecord.setMemberId(companyAccount.getMemberId());
			alabaoRollOffRecord.setIsSuccess(CommonConstant.YES);
			alabaoRollOffRecord.setTransferMoney(turnOutAmount);
			alabaoRollOffRecord.setRollOffAccountType("1");/**阿拉宝*/
			alabaoRollOffRecord.setSerialNum(serialNum);
			alabaoRollOffRecord.setBatchNum(batchNum);
			alabaoRollOffRecord.setMemo(memo);
			
			alabaoRollOffRecord.setAfterChangeMoney(companyAccount.getBalance().subtract(turnOutAmount));
			alabaoRollOffRecord.setBeforeChangeMoney(companyAccount.getBalance());
			alabaoRollOffRecordService.add(alabaoRollOffRecord);
			/**本方转出方记录*/
			AlabaoTurnToAlabao turnToAlabao = new AlabaoTurnToAlabao();
			turnToAlabao.setMemberId(companyAccount.getMemberId());
			turnToAlabao.setAccount(companyAccount.getAccount());
			turnToAlabao.setOtherAccount(otherAccount.getAccount());
			turnToAlabao.setTransferMoney(turnOutAmount);
			turnToAlabao.setTurnOutStatus(CommonConstant.YES);
			turnToAlabao.setSerialNum(serialNum);
			turnToAlabao.setBatchNum(batchNum);
			turnToAlabao.setMemo(memo);
			turnToAlabaoService.add(turnToAlabao);
		}
		/**对方转入记录*/
		AlabaoShiftToRecord alabaoShiftToRecord = new AlabaoShiftToRecord();
		alabaoShiftToRecord.setAccount(otherAccount.getAccount());
		alabaoShiftToRecord.setMemberId(otherAccount.getMemberId());
		alabaoShiftToRecord.setTransferMoney(turnOutAmount);
		alabaoShiftToRecord.setShiftToAccountType(AlabaoPayTypeConstant.ALABAO);
		alabaoShiftToRecord.setSerialNum(serialNum);
		alabaoShiftToRecord.setBatchNum(batchNum);
		alabaoShiftToRecord.setMemo(memo);
		
		alabaoShiftToRecord.setAfterChangeMoney(otherAccount.getBalance().add(turnOutAmount));
		alabaoShiftToRecord.setBeforeChangeMoney(otherAccount.getBalance());
		alabaoShiftToRecordService.add(alabaoShiftToRecord);
		
	}
	
	@RequestMapping("findRollOffRecord")
	public Object  findRollOffRecord(String account,
			@RequestParam(value="batchNum",defaultValue="") String batchNum,
			@RequestParam(value="serialNum",defaultValue="") String serialNum,
			@RequestParam(value="rollOffAccountType",defaultValue="") String rollOffAccountType,
			@RequestParam(value="isSuccess",defaultValue="") String isSuccess,
			@RequestParam(value="transferMoneyMin",defaultValue="") String transferMoneyMin,
			@RequestParam(value="transferMoneyMax",defaultValue="") String transferMoneyMax,
			BasePagination page,Model model){
		if (page == null) {
			page = new BasePagination();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("account", account);
		params.put("batchNum", batchNum);
		params.put("serialNum", serialNum);
		params.put("rollOffAccountType", rollOffAccountType);
		params.put("isSuccess", isSuccess);
		params.put("transferMoneyMin", transferMoneyMin);
		params.put("transferMoneyMax", transferMoneyMax);
		page.setParams(params);
		
		BasePagination resultPage = alabaoRollOffRecordService.getListPage(page);
		model.addAttribute("account", account);
		model.addAttribute("batchNum", batchNum);
		model.addAttribute("serialNum", serialNum);
		model.addAttribute("rollOffAccountType", rollOffAccountType);
		model.addAttribute("isSuccess", isSuccess);
		model.addAttribute("transferMoneyMin", transferMoneyMin);
		model.addAttribute("transferMoneyMax", transferMoneyMax);
		model.addAttribute("page", resultPage);
		return "models/alabao/finance/alabaoRollOffRecordList";
	}
	
	@RequestMapping("findShiftToRecord")
	public Object findShiftToRecord(String account,
			@RequestParam(value="batchNum",defaultValue="") String batchNum,
			@RequestParam(value="serialNum",defaultValue="") String serialNum,
			@RequestParam(value="shiftToAccountType",defaultValue="") String shiftToAccountType,
			@RequestParam(value="transferMoneyMin",defaultValue="") String transferMoneyMin,
			@RequestParam(value="transferMoneyMax",defaultValue="") String transferMoneyMax,
			BasePagination page,Model model){
		
		if (page == null) {
			page = new BasePagination();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("account", account);
		params.put("batchNum", batchNum);
		params.put("serialNum", serialNum);
		params.put("shiftToAccountType", shiftToAccountType);
		params.put("transferMoneyMin", transferMoneyMin);
		params.put("transferMoneyMax", transferMoneyMax);
		page.setParams(params);
		BasePagination resultPage = alabaoShiftToRecordService.getListPage(page);
		model.addAttribute("account", account);
		model.addAttribute("batchNum", batchNum);
		model.addAttribute("serialNum", serialNum);
		model.addAttribute("shiftToAccountType", shiftToAccountType);
		model.addAttribute("transferMoneyMin", transferMoneyMin);
		model.addAttribute("transferMoneyMax", transferMoneyMax);
		model.addAttribute("page", resultPage);
		return "models/alabao/finance/alabaoShiftToRecordList";
	}
	
	@RequestMapping("findTurnToAlabao")
	public Object findturnToAlabao(String account,
			@RequestParam(value="otherAccount",defaultValue="") String otherAccount,
			@RequestParam(value="turnOutStatus",defaultValue="") String turnOutStatus,
			@RequestParam(value="batchNum",defaultValue="") String batchNum,
			@RequestParam(value="serialNum",defaultValue="") String serialNum,
			@RequestParam(value="transferMoneyMin",defaultValue="") String transferMoneyMin,
			@RequestParam(value="transferMoneyMax",defaultValue="") String transferMoneyMax,
			BasePagination page,Model model){
		
		
		if (page == null) {
			page = new BasePagination();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("account", account);
		params.put("otherAccount", otherAccount);
		params.put("batchNum", batchNum);
		params.put("serialNum", serialNum);
		params.put("turnOutStatus", turnOutStatus);
		params.put("transferMoneyMin", transferMoneyMin);
		params.put("transferMoneyMax", transferMoneyMax);
		page.setParams(params);
		BasePagination resultPage = turnToAlabaoService.getListPage(page);
		model.addAttribute("account", account);
		model.addAttribute("otherAccount", otherAccount);
		model.addAttribute("batchNum", batchNum);
		model.addAttribute("serialNum", serialNum);
		model.addAttribute("turnOutStatus", turnOutStatus);
		model.addAttribute("transferMoneyMin", transferMoneyMin);
		model.addAttribute("transferMoneyMax", transferMoneyMax);
		model.addAttribute("page", resultPage);
		
		return "models/alabao/finance/turnToAlabaoRecordList";
	}
	/**
	 * 批量撤回导入记录
	 * @param selectedAccountSerialNums
	 * @return
	 */
	@ResponseBody
	@RequestMapping("cannelTurnToAccount")
	public Object cannelTurnToAccount(String selectedAccountSerialNums){
		
		
		List<String> selectedAccountSerialNumList = Arrays.asList(StringUtils.split(selectedAccountSerialNums, ","));
		
		List<AlabaoTurnToAlabao> alabaoTurnToAlabaos = new ArrayList<AlabaoTurnToAlabao>();
		
		for (String serialNum : selectedAccountSerialNumList) {
			
			AlabaoTurnToAlabao alabaoTurnToAlabao = turnToAlabaoService.findBySerialNum(serialNum);
			if(alabaoTurnToAlabao==null){
				return false;
			}
			if(StringUtils.equals(alabaoTurnToAlabao.getTurnOutStatus(),CommonConstant.YES)){
				alabaoTurnToAlabaos.add(alabaoTurnToAlabao);
			}
			
		}
		for (AlabaoTurnToAlabao alabaoTurnToAlabao : alabaoTurnToAlabaos) {
			this.resetBalance(alabaoTurnToAlabao);
			this.cancelTurnRecord(alabaoTurnToAlabao);
		}
		
		return true;
	}
	/**
	 * 取消记录信息
	 * @param alabaoTurnToAlabao
	 */
//	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	private void cancelTurnRecord(AlabaoTurnToAlabao alabaoTurnToAlabao){
		
		alabaoTurnToAlabao.setTurnOutStatus(CommonConstant.NO);
		turnToAlabaoService.updateStatus(alabaoTurnToAlabao);
		String batchNum = alabaoTurnToAlabao.getBatchNum();
		String otherAccountCode = alabaoTurnToAlabao.getOtherAccount();
		
		List<AlabaoRollOffRecord> alabaoRollOffRecordList = alabaoRollOffRecordService.findByBatchNum(batchNum);
		
		for (AlabaoRollOffRecord alabaoRollOffRecord : alabaoRollOffRecordList) {
			if(StringUtils.equals(alabaoTurnToAlabao.getAccount(),alabaoRollOffRecord.getAccount())){
				alabaoRollOffRecord.setIsSuccess(CommonConstant.NO);
				alabaoRollOffRecordService.update(alabaoRollOffRecord);
			}
		}
		List<AlabaoShiftToRecord> alabaoShiftToRecordList = alabaoShiftToRecordService.findAllByBatchNum(batchNum);
		for (AlabaoShiftToRecord alabaoShiftToRecord : alabaoShiftToRecordList) {
			if(StringUtils.equals(otherAccountCode, alabaoShiftToRecord.getAccount())){
				alabaoShiftToRecordService.deleteByAccountAndBatchNum(otherAccountCode, batchNum);
			}
		}
		
		
	}
	/**
	 * 重置双方余额
	 * @param alabaoTurnToAlabao
	 */
//	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	private void resetBalance(AlabaoTurnToAlabao alabaoTurnToAlabao){
		
		String otherAccountCode = alabaoTurnToAlabao.getOtherAccount();
		String accountCode = alabaoTurnToAlabao.getAccount();
		BigDecimal transferMoney = alabaoTurnToAlabao.getTransferMoney();
		
		
		AlabaoAccount otherAccount = alabaoAccountService.findByAccount(otherAccountCode);
		if(otherAccount.getBalance().compareTo(transferMoney)==-1){
			
			throw new RuntimeException();
		}
		
		otherAccount.setBalance(otherAccount.getBalance().subtract(transferMoney));
		alabaoAccountService.updateByAccount(otherAccount);
		
		AlabaoAccount companyAccount = alabaoAccountService.findByAccount(accountCode);
		companyAccount.setBalance(companyAccount.getBalance().add(transferMoney));
		alabaoAccountService.updateByAccount(companyAccount);
		
	}
	
	
}
