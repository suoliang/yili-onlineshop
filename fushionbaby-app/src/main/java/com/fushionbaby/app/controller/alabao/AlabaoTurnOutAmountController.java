package com.fushionbaby.app.controller.alabao;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import util.ImageConstantFacade;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoAccountBank;
import com.aladingshop.alabao.model.AlabaoBankConfig;
import com.aladingshop.alabao.model.AlabaoLimit;
import com.aladingshop.alabao.model.AlabaoRollOffRecord;
import com.aladingshop.alabao.model.AlabaoRolloffConfig;
import com.aladingshop.alabao.model.AlabaoTurnOut;
import com.aladingshop.alabao.service.AlabaoAccountBankService;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoBankConfigService;
import com.aladingshop.alabao.service.AlabaoLimitService;
import com.aladingshop.alabao.service.AlabaoRollOffRecordService;
import com.aladingshop.alabao.service.AlabaoRolloffConfigService;
import com.aladingshop.alabao.service.AlabaoTurnOutService;
import com.aladingshop.alabao.vo.AccountBankDto;
import com.aladingshop.alabao.vo.AddAccountBandDto;
import com.aladingshop.alabao.vo.TurnOutAmountDto;
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.VerificationConstant;
import com.fushionbaby.common.constants.alabao.AlabaoTurnOutConstant;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.dto.verification.BankCardVerificationReqDto;
import com.fushionbaby.common.dto.verification.VerificationResDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.GetSerialNumUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrGlobalConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.facade.biz.common.sms.SmsServiceFacade;
import com.fushionbaby.facade.biz.common.verification.BankCardVerificationFacade;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.model.VerificationRecord;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.member.service.VerificationRecordService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @description 转出金额操作类
 * @author 索亮
 * @date 2015年9月23日下午5:18:43
 */
@Controller
@RequestMapping("/alabao")
public class AlabaoTurnOutAmountController {
	
	private static final Log LOGGER = LogFactory.getLog(AlabaoTurnOutAmountController.class);
	
	@Autowired
	private AlabaoRolloffConfigService<AlabaoRolloffConfig> alabaoRolloffConfigService;
	@Autowired
	private AlabaoRollOffRecordService<AlabaoRollOffRecord> alabaoRollOffRecordService;
	@Autowired
	private AlabaoTurnOutService<AlabaoTurnOut> alabaoTurnOutService;
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	/**如意消费卡银行绑定*/
	@Autowired
	private AlabaoAccountBankService<AlabaoAccountBank> alabaoAccountBankService;
	@Autowired
	private AlabaoBankConfigService<AlabaoBankConfig> alabaoBankConfigService;
	@Autowired
	private SmsService<Sms> smsService;
	@Autowired
	private AlabaoLimitService<AlabaoLimit> alabaoLimitService;
	/**认证记录*/
	@Autowired
	private VerificationRecordService<VerificationRecord> verificationRecordService;
	@Autowired
	private SmsServiceFacade smsServiceFacade;
	/**银行卡验证*/
	@Autowired
	private BankCardVerificationFacade bankCardVerificationFacade;
	/**会员地域信息*/
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaService;
	
	/**全局配置*/
	@Autowired
	private SysmgrGlobalConfigService sysmgrGlobalConfigService;
	
	/***
	 * 如意宝-转出到银行卡--请求短信验证码
	 * @param alabaoSid
	 * @param account
	 * @param bankSmsCode -- 请求短信标识码
	 * @param sourceCode
	 * @return
	 */
//	@ResponseBody
//	@RequestMapping("getBankSmsCode")
//	public Object getBankSmsCode(
//			@RequestParam(value="data",defaultValue="")String data,
//			@RequestParam(value="mac",defaultValue="")String mac){
//		try {
//			LOGGER.info("注册如意宝接口action--请求报文：{" + data + "}");
//			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
//				return Jsonp.error_md5();
//			}
//			Gson gson = new Gson();
//			TurnToBankSmsDto turnToBankSmsDto = gson.fromJson(data, TurnToBankSmsDto.class);
//			
//			if (CheckIsEmpty.isEmpty(turnToBankSmsDto.getAlabaoSid(),turnToBankSmsDto.getAccount(),turnToBankSmsDto.getBankSmsCode(),turnToBankSmsDto.getSourceCode())) {
//				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
//			}
//			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(turnToBankSmsDto.getAlabaoSid());
//			if (ObjectUtils.equals(alabaoUserDto, null)) {
//				return Jsonp.noLoginError("未登录或重新登录");
//			}
//			if (smsServiceFacade.getNumByPhone(turnToBankSmsDto.getAccount())) {
//				return Jsonp.smsNumberLimit();
//			}
//			/**获取纯数字的验证码*/
//			String registerRandomNum = RandomNumUtil.getRandom(RandomNumUtil.NUM, RandomNumUtil.REGISTER_USER_LENGTH);
//			DataCache.put(turnToBankSmsDto.getBankSmsCode(), registerRandomNum);
//			try {
//				//注册发送的短信验证码
//				smsService.sendSmsCode(registerRandomNum,turnToBankSmsDto.getAccount(),turnToBankSmsDto.getSourceCode(),SmsConstant.SMS_TYPE_TURNBANK_ID);
//			} catch (Exception e) {
//				e.printStackTrace();
//				return Jsonp.error("短信服务器响应超时");
//			}
//			return Jsonp.success();
//		} catch (Exception e) {
//			e.printStackTrace();
//			LOGGER.error("请求出错" + e);
//			return Jsonp.error();
//		}
//	}
	
	/**
	 * 转出金额--主操作
	 * @param alabaoSid
	 * @param sourceCode
	 * @param transferMoney 
	 * @param bankName
	 * @param cardNo
	 * @param cardHolder
	 * @param payPassword
	 * @param bankBranchName -- 银行支行名称
	 * @param smsCode -- 用户输入的验证码
	 * @param bankSmsCode -- 请求短信标识码
	 * @param accountBankId -- 用户银行关联id
	 * @param memo -- 备注
	 * @return
	 */
	@ResponseBody
	@RequestMapping("turnOutAmount")
	public Object turnOutAmount(
			@RequestParam(value="data")String data,
			@RequestParam(value="mac")String mac){
		try {
			LOGGER.info("转出金额如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			TurnOutAmountDto turnOutAmountDto = gson.fromJson(data, TurnOutAmountDto.class);
			String alabaoSid = turnOutAmountDto.getAlabaoSid();
			String transferMoney = turnOutAmountDto.getTransferMoney();
			String sourceCode = turnOutAmountDto.getSourceCode();
			String bankName = turnOutAmountDto.getBankName();
			String cardNo = turnOutAmountDto.getCardNo();
			String cardHolder = turnOutAmountDto.getCardHolder();
			String password = turnOutAmountDto.getPayPassword();
			String bankBranchName=turnOutAmountDto.getBankBranchName();
			String bankSmsCode = turnOutAmountDto.getBankSmsCode();
			//String smsCode = turnOutAmountDto.getSmsCode();
			Long accountBankId = turnOutAmountDto.getAccountBankId();
			String memo = turnOutAmountDto.getMemo();
			LOGGER.info("转码前转账备注:"+memo);
			memo=URLDecoder.decode(memo, "UTF-8");
			LOGGER.info("解码后转账备注:"+memo);
			LOGGER.info("转码前银行名称:"+bankName+"转码前持卡人姓名:"+cardHolder);
			bankName = URLDecoder.decode(bankName, "UTF-8");
			cardHolder = URLDecoder.decode(cardHolder, "UTF-8");
			bankBranchName=URLDecoder.decode(bankBranchName, "UTF-8");
			LOGGER.info("解码后银行名称:"+bankName+"解码后持卡人姓名:"+cardHolder);
			if (CheckIsEmpty.isEmpty(alabaoSid,transferMoney,sourceCode,bankName,cardNo,cardHolder)) {
				return Jsonp.error("参数无值");
			}
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(turnOutAmountDto.getAlabaoSid());
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			/** 合法的验证码 */
//			String validSmsCode = (String) DataCache.get(bankSmsCode);
//			if (!StringUtils.equals(smsCode,validSmsCode)) {
//				return Jsonp.error("验证码输入有误!");
//			}
			
			Long memberId = alabaoUserDto.getMemberId();
			String account = alabaoUserDto.getAccount();
			AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(account);
			if (!StringUtils.equals(alabaoAccount.getPayPassword(), MD5Util.MD5(password+MD5Util.getPasswordkey()))) {
				return Jsonp.acountFailureError("密码输入错误");
			}
			if (alabaoAccount.getBalance().compareTo(new BigDecimal(transferMoney))<0) {
				return Jsonp.error("您当前金额不足");
			}
			AlabaoLimit alabaoLimit = alabaoLimitService.findByAccount(account);
			/**当天转出次数集合*/
			List<AlabaoRollOffRecord> list = alabaoRollOffRecordService.findByMemberIdTime(memberId, new Date());
			/**限制对象为空--走正常限制*/
			if (ObjectUtils.equals(alabaoLimit, null)) {
				AlabaoRolloffConfig alabaoRolloffConfig = alabaoRolloffConfigService.findByRollOffCode(GlobalConfigConstant.ROLLOFFCODE);
				/**当天转出到银行卡总金额*/
				BigDecimal totalTransferMoney = BigDecimal.ZERO;
				for (AlabaoRollOffRecord alabaoRollOffRecord : list) {
					totalTransferMoney = totalTransferMoney.add(alabaoRollOffRecord.getTransferMoney());
				}
				/**当天转出银行卡现在总金额*/
				BigDecimal allMoney = totalTransferMoney.add(new BigDecimal(transferMoney));
				
				if (ObjectUtils.notEqual(alabaoRolloffConfig, null)) {
					// 小于0表示前面数小后面数大--超出转出限额，不能转
					if(alabaoRolloffConfig.getMaxMoneyLimit().compareTo(allMoney)<0){
						return Jsonp.error("超出总额"+alabaoRolloffConfig.getMaxMoneyLimit()+"可转"+alabaoRolloffConfig.getMaxMoneyLimit().subtract(totalTransferMoney));
					}
					if (!CollectionUtils.isEmpty(list) && list.size() >= alabaoRolloffConfig.getMaxNumberLimit()) {
						return Jsonp.error("超出次数"+alabaoRolloffConfig.getMaxNumberLimit());
					}
				}
			} else {
				// 小于0表示前面数小后面数大--超出转出限额，不能转
				if(alabaoLimit.getMoneyLimit().compareTo(new BigDecimal(transferMoney))<0){
					return Jsonp.error("会员超出限额"+alabaoLimit.getMoneyLimit());
				}
				if (!CollectionUtils.isEmpty(list) && list.size() >= alabaoLimit.getNumberLimit()) {
					return Jsonp.error("会员超出次数"+alabaoLimit.getNumberLimit());
				}
			}
			
			String flag=CommonConstant.NO;
			SysmgrGlobalConfig globalConfig = sysmgrGlobalConfigService.findByCode(VerificationConstant.OPEN_OR_CLOSE_VERIFICATION_CARD);
			if(globalConfig!=null){
				flag=globalConfig.getValue();
			}
//			List<SysmgrDictConfig> configList=this.sysmgrDictConfigService.findByLabelValueType(null, null, VerificationConstant.OPEN_OR_CLOSE_VERIFICATION_CARD);
//			if(configList!=null&&configList.size()>0)
//				flag=configList.get(0).getValue();
			if(CommonConstant.YES.equals(flag)){
				/**银行卡认证begin*/
				AlabaoAccountBank accountBank=this.alabaoAccountBankService.findByBankCardNo(cardNo, CommonConstant.NO);
				if(accountBank==null){
					return Jsonp.error("您的银行卡不存在");
				}
				if(!VerificationConstant.IS_VALIDATE_STATUS_SUCCESS.equals(accountBank.getIsValidate()))
				{
					List<VerificationRecord> recordList=this.verificationRecordService.findByBankCardNoAndID(cardNo, "", cardHolder);
					if(recordList!=null&&recordList.size()-VerificationConstant.VERIFICATION_MAX_TIMES_ONEDAY>=0){
						return Jsonp.error("认证已超三次，请联系客服");
					}
					BankCardVerificationReqDto reqDto=new BankCardVerificationReqDto(cardHolder, cardNo);
				    VerificationResDto verificationResDto = this.bankCardVerificationFacade.getBankCardVerificationInfo(reqDto);
					if(!VerificationConstant.VALIDATE_SUCCESS_RESPONSECODE.equals(verificationResDto.getCode()))
					{
						this.alabaoAccountBankService.updateIsValidate(cardHolder, cardNo, account, false);
						return Jsonp.error("校验不通过");
					}else{
						this.alabaoAccountBankService.updateIsValidate(cardHolder, cardNo, account, true);
					}
				}
				/** 银行卡认证end*/
			}
			String serialNum =GetSerialNumUtil.generateSerialNum();
			BigDecimal transfer_money=new BigDecimal(turnOutAmountDto.getTransferMoney());
			BigDecimal old_balance=alabaoAccount.getBalance();
			/**保存转出记录*/
			saveRollOffRecord(transfer_money, memo,memberId, account,old_balance,serialNum);
			/**保存提现记录（转出到银行卡）*/
			saveTurnOut(transfer_money, bankName, cardNo, cardHolder,bankBranchName, memo, memberId, account, serialNum);
			/**转出时余额做改变*/
			alabaoAccount.setBalance(old_balance.subtract(transfer_money));
			alabaoAccount.setLastedBankId(accountBankId);
			alabaoAccountService.updateByAccount(alabaoAccount);
			
			/**删除短信验证码*/
			if(StringUtils.isNotBlank(bankSmsCode))
			    DataCache.remove(bankSmsCode);
			
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("转出金额接口出错", e);
			return Jsonp.error("转出金额出错");
		}
	}

	private void saveTurnOut(BigDecimal transfer_money,String bankName, String cardNo, String cardHolder,
			String bankBranchName, String memo, Long memberId, String account,
			String serialNum) {
		AlabaoTurnOut alabaoTurnOut = new AlabaoTurnOut();
		alabaoTurnOut.setMemberId(memberId);
		alabaoTurnOut.setAccount(account);
		alabaoTurnOut.setTransferMoney(transfer_money);
		alabaoTurnOut.setBankName(bankName);
		alabaoTurnOut.setBankBranchName(bankBranchName);
		alabaoTurnOut.setCardNo(cardNo);
		alabaoTurnOut.setCardHolder(cardHolder);
		alabaoTurnOut.setTurnOutStatus(AlabaoTurnOutConstant.TURN_STATUS_WAIT);
		alabaoTurnOut.setSerialNum(serialNum);
		alabaoTurnOut.setMemo(memo);
		alabaoTurnOutService.add(alabaoTurnOut);
	}

	private void saveRollOffRecord(BigDecimal transfer_money,String memo, Long memberId, String account,
			BigDecimal old_balance,String serialNum) {
		AlabaoRollOffRecord alabaoRollOffRecord = new AlabaoRollOffRecord();
		alabaoRollOffRecord.setAccount(account);
		alabaoRollOffRecord.setMemberId(memberId);
		alabaoRollOffRecord.setIsSuccess(CommonConstant.NO);
		alabaoRollOffRecord.setTransferMoney(transfer_money);
		alabaoRollOffRecord.setRollOffAccountType(AlabaoTurnOutConstant.ROLL_OFF_TYPE_YINLIAN);
		alabaoRollOffRecord.setSerialNum(serialNum);
		alabaoRollOffRecord.setMemo(memo);
		alabaoRollOffRecord.setAfterChangeMoney(old_balance.subtract(transfer_money));
		alabaoRollOffRecord.setBeforeChangeMoney(old_balance);
		alabaoRollOffRecordService.add(alabaoRollOffRecord);
	}
	
	/**
	 * 转出金额--确认转出时校验
	 * @param alabaoSid
	 * @param sourceCode
	 * @param transferMoney 
	 * @param bankName
	 * @param cardNo
	 * @param cardHolder
	 * @param bankBranchName -- 银行支行名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkTurnOutBank")
	public Object checkTurnOutBank(
			@RequestParam(value="data")String data,
			@RequestParam(value="mac")String mac){
		try {
			LOGGER.info("转出到银行卡校验如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			TurnOutAmountDto turnOutAmountDto = gson.fromJson(data, TurnOutAmountDto.class);
			String alabaoSid = turnOutAmountDto.getAlabaoSid();
			String transferMoney = turnOutAmountDto.getTransferMoney();
			String sourceCode = turnOutAmountDto.getSourceCode();
			String bankName = turnOutAmountDto.getBankName();
			String cardNo = turnOutAmountDto.getCardNo();
			String cardHolder = turnOutAmountDto.getCardHolder();
			String bankBranchName=turnOutAmountDto.getBankBranchName();
			LOGGER.info("转码前银行名称:"+bankName+"转码前持卡人姓名:"+cardHolder);
			bankName = URLDecoder.decode(bankName, "UTF-8");
			cardHolder = URLDecoder.decode(cardHolder, "UTF-8");
			bankBranchName=URLDecoder.decode(bankBranchName, "UTF-8");
			LOGGER.info("解码后银行名称:"+bankName+"解码后持卡人姓名:"+cardHolder);
			if (CheckIsEmpty.isEmpty(alabaoSid,transferMoney,sourceCode,bankName,cardNo,cardHolder)) {
				return Jsonp.error("参数无值");
			}
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(turnOutAmountDto.getAlabaoSid());
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			Long memberId = alabaoUserDto.getMemberId();
			String account = alabaoUserDto.getAccount();
			AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(account);
			if (alabaoAccount.getBalance().compareTo(new BigDecimal(transferMoney))<0) {
				return Jsonp.error("您当前金额不足");
			}
			AlabaoLimit alabaoLimit = alabaoLimitService.findByAccount(account);
			/**当天转出次数集合*/
			List<AlabaoRollOffRecord> list = alabaoRollOffRecordService.findByMemberIdTime(memberId, new Date());
			/**限制对象为空--走正常限制*/
			if (ObjectUtils.equals(alabaoLimit, null)) {
				AlabaoRolloffConfig alabaoRolloffConfig = alabaoRolloffConfigService.findByRollOffCode(GlobalConfigConstant.ROLLOFFCODE);
				/**当天转出到银行卡总金额*/
				BigDecimal totalTransferMoney = BigDecimal.ZERO;
				for (AlabaoRollOffRecord alabaoRollOffRecord : list) {
					totalTransferMoney = totalTransferMoney.add(alabaoRollOffRecord.getTransferMoney());
				}
				/**当天转出银行卡现在总金额*/
				BigDecimal allMoney = totalTransferMoney.add(new BigDecimal(transferMoney));
				
				if (ObjectUtils.notEqual(alabaoRolloffConfig, null)) {
					// 小于0表示前面数小后面数大--超出转出限额，不能转
					if(alabaoRolloffConfig.getMaxMoneyLimit().compareTo(allMoney)<0){
						return Jsonp.error("超出总额"+alabaoRolloffConfig.getMaxMoneyLimit()+"可转"+alabaoRolloffConfig.getMaxMoneyLimit().subtract(totalTransferMoney));
					}
					if (!CollectionUtils.isEmpty(list) && list.size() >= alabaoRolloffConfig.getMaxNumberLimit()) {
						return Jsonp.error("超出次数"+alabaoRolloffConfig.getMaxNumberLimit());
					}
				}
			} else {
				// 小于0表示前面数小后面数大--超出转出限额，不能转
				if(alabaoLimit.getMoneyLimit().compareTo(new BigDecimal(transferMoney))<0){
					return Jsonp.error("会员超出限额"+alabaoLimit.getMoneyLimit());
				}
				if (!CollectionUtils.isEmpty(list) && list.size() >= alabaoLimit.getNumberLimit()) {
					return Jsonp.error("会员超出次数"+alabaoLimit.getNumberLimit());
				}
			}
			
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("转出金额接口出错", e);
			return Jsonp.error("转出金额出错");
		}
	}
	
	/**
	 * 获取用户的银行卡列表
	 * @param alabaoSid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getUserBank")
	public Object getUserBank(
			@RequestParam(value="data")String data,
			@RequestParam(value="mac")String mac){
		try {
			LOGGER.info("获取用户银行卡列表如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			AlabaoAccount account=this.alabaoAccountService.findByAccount(alabaoUserDto.getAccount());
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			
			BigDecimal balance = getCanTransMaxMoney(account);
			
			List<AlabaoAccountBank> list = alabaoAccountBankService.findByAccount(alabaoUserDto.getAccount());
			Long lastedBankId = alabaoAccountService.findByAccount(alabaoUserDto.getAccount()).getLastedBankId();
			List<AccountBankDto> accountBankList = new ArrayList<AccountBankDto>();
			for (AlabaoAccountBank alabaoAccountBank : list) {
				AccountBankDto accountBankDto = new AccountBankDto();
				accountBankDto.setBankName(alabaoAccountBank.getBankName());
				accountBankDto.setCardHolder(alabaoAccountBank.getCardHolder());
				String cardNo = alabaoAccountBank.getCardNo();
				accountBankDto.setCardNo(cardNo);
				accountBankDto.setBankTailNo(cardNo.substring(cardNo.length()-4,cardNo.length()));
				AlabaoBankConfig bankConfig = alabaoBankConfigService.findByBankName(alabaoAccountBank.getBankName());
				accountBankDto.setBankIconUrl(ImageConstantFacade.IMAGE_SERVER_ROOT_PATH+bankConfig.getBankIconUrl());
				accountBankDto.setBankDesc(bankConfig.getBankDesc()==null?"":bankConfig.getBankDesc());
				accountBankDto.setBankBranchName(alabaoAccountBank.getBankBranchName()==null?"":alabaoAccountBank.getBankBranchName());
				accountBankDto.setAccountBankId(alabaoAccountBank.getId());
				if (ObjectUtils.equals(lastedBankId, alabaoAccountBank.getId())) {
					accountBankDto.setIsLasted(CommonConstant.YES);
				} else {
					accountBankDto.setIsLasted(CommonConstant.NO);
				}
				String address = setAddressMemo(alabaoAccountBank);
				accountBankDto.setAddress(address);
				accountBankDto.setBalance(NumberFormatUtil.numberFormat(balance));
				accountBankList.add(accountBankDto);
			}
			return Jsonp_data.success(accountBankList);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取用户银行卡接口出错", e);
			return Jsonp.error("获取卡信息出错");
		}
	}

	/***
	 * 设置 地址的对应汉语名称
	 * @param alabaoAccountBank
	 * @return
	 */
	private String setAddressMemo(AlabaoAccountBank alabaoAccountBank) {
		String address="";
		String province_code=alabaoAccountBank.getProvince();
		String city_code=alabaoAccountBank.getCity();
		MemberAreaConfig provinceConfig = memberAreaService.getAreaCode(province_code);
		MemberAreaConfig cityConfig = memberAreaService.getAreaCode(city_code);
		if(provinceConfig!=null&&cityConfig!=null){
			address=provinceConfig.getCity()+cityConfig.getCity();
		}
		return address;
	}

	/***
	 * 得到 用户可以转出的最大金额
	 * 
	 * @param account
	 * @return
	 */
	private BigDecimal getCanTransMaxMoney(AlabaoAccount account) {
		BigDecimal balance=BigDecimal.ZERO;
		
		AlabaoLimit alabaoLimit = alabaoLimitService.findByAccount(account.getAccount());
		/**当天转出次数集合*/
		List<AlabaoRollOffRecord> roll_list = alabaoRollOffRecordService.findByMemberIdTime(account.getMemberId(), new Date());
		/**当天转出到银行卡总金额*/
		BigDecimal totalTransferMoney = BigDecimal.ZERO;
		for (AlabaoRollOffRecord alabaoRollOffRecord : roll_list) {
			totalTransferMoney = totalTransferMoney.add(alabaoRollOffRecord.getTransferMoney());
		}
		/**在转出限制控制中*/
		if(ObjectUtils.notEqual(alabaoLimit, null)){
			/**转出次数和金额控制*/
			if(alabaoLimit.getNumberLimit()>roll_list.size() && alabaoLimit.getMoneyLimit().compareTo(totalTransferMoney)>0)
				{
					balance=alabaoLimit.getMoneyLimit().compareTo(account.getBalance())>0?account.getBalance():alabaoLimit.getMoneyLimit();
				}
		}else{/**限制对象为空--走正常限制*/
			AlabaoRolloffConfig alabaoRolloffConfig = alabaoRolloffConfigService.findByRollOffCode(GlobalConfigConstant.ROLLOFFCODE);
			/**每天可转默认最大金额5000*/
			BigDecimal  max_money_oneday=new BigDecimal(5000);
			if(ObjectUtils.notEqual(alabaoRolloffConfig, null)){
				max_money_oneday=alabaoRolloffConfig.getMaxMoneyLimit();
			}
			/** 当天最多还可以转出多少金额   设置每天可转量-已经转出量*/
			BigDecimal can_trans_money=max_money_oneday.subtract(totalTransferMoney);
			
			if(alabaoRolloffConfig.getMaxNumberLimit()>=roll_list.size())
				{
					balance=can_trans_money.compareTo(account.getBalance())>=0?account.getBalance():can_trans_money;
				}
		}
		return balance;
	}
	
	/**
	 * 获取支持的银行配置列表,供用户选择
	 * @param alabaoSid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getBankConfigList")
	public Object getBankConfigList(
			@RequestParam(value="data")String data,
			@RequestParam(value="mac")String mac){
		try {
			LOGGER.info("获取用户银行卡列表如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			List<AlabaoBankConfig> list = alabaoBankConfigService.findAll();
			List<AccountBankDto> accountBankList = new ArrayList<AccountBankDto>();
			for (AlabaoBankConfig alabaoBankConfig : list) {
				AccountBankDto accountBankDto = new AccountBankDto();
				accountBankDto.setBankName(alabaoBankConfig.getBankName());
				accountBankDto.setBankIconUrl(ImageConstantFacade.IMAGE_SERVER_ROOT_PATH+alabaoBankConfig.getBankIconUrl());
				accountBankList.add(accountBankDto);
			}
			return Jsonp_data.success(accountBankList);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取银行卡配置列表接口出错", e);
			return Jsonp.error("获取卡列表出错");
		}
	}
	
	/**
	 * 添加用户的银行卡信息
	 * @param alabaoSid
	 * @param bankName
	 * @param cardNo
	 * @param cardHolder
	 * @param bankBranchName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addUserBank")
	public Object addUserBank(
			@RequestParam(value="data")String data,
			@RequestParam(value="mac")String mac){
		try {
			LOGGER.info("添加卡信息如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			AddAccountBandDto addAccountBandDto = gson.fromJson(data, AddAccountBandDto.class);
			
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(addAccountBandDto.getAlabaoSid());
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			if (ObjectUtils.notEqual(alabaoAccountBankService.findByBankCardNo(addAccountBandDto.getCardNo(),CommonConstant.NO), null)) {
				return Jsonp.error("此银行卡已绑定");
			}
			
			String bankName = addAccountBandDto.getBankName();
			String cardHolder = addAccountBandDto.getCardHolder();
			String bankBranchName=addAccountBandDto.getBankBranchName();
			String province=addAccountBandDto.getProvince();
			String city=addAccountBandDto.getCity();
			
			LOGGER.info("转码前银行名称:"+bankName+"转码前持卡人姓名:"+cardHolder);
			bankName = URLDecoder.decode(bankName, "UTF-8");
			cardHolder = URLDecoder.decode(cardHolder, "UTF-8");
			bankBranchName = URLDecoder.decode(bankBranchName, "UTF-8");
			LOGGER.info("解码后银行名称:"+bankName+"解码后持卡人姓名:"+cardHolder);
			
			if (!StringUtils.equals(cardHolder, alabaoUserDto.getMemberName())) {
				return Jsonp.error("请绑定本人银行卡");
			}
			
			List<AlabaoAccountBank> list = alabaoAccountBankService.findByAccount(alabaoUserDto.getAccount());
			if (list.size() >= 5) {
				return Jsonp.error("卡个数限制5个");
			}
			
			AlabaoAccountBank alabaoAccountBank = new AlabaoAccountBank();
			alabaoAccountBank.setAccount(alabaoUserDto.getAccount());
			alabaoAccountBank.setMemberId(alabaoUserDto.getMemberId());
			alabaoAccountBank.setBankName(bankName);
			alabaoAccountBank.setBankBranchName(bankBranchName);
			alabaoAccountBank.setCardNo(addAccountBandDto.getCardNo());
			alabaoAccountBank.setCardHolder(cardHolder);
			alabaoAccountBank.setIsDelete(CommonConstant.NO);
			alabaoAccountBank.setProvince(province);
			alabaoAccountBank.setCity(city);
			alabaoAccountBank.setIsValidate(VerificationConstant.IS_VALIDATE_STATUS_NULL);
			
			alabaoAccountBankService.add(alabaoAccountBank);
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("添加卡信息接口出错", e);
			return Jsonp.error("添加卡信息出错");
		}
	}
	
}
