package com.aladingshop.wap.controller.ruyibao;

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
import com.aladingshop.alabao.model.AlabaoRollOffRecord;
import com.aladingshop.alabao.model.AlabaoRolloffConfig;
import com.aladingshop.alabao.model.AlabaoTurnOut;
import com.aladingshop.alabao.service.AlabaoAccountBankService;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoBankConfigService;
import com.aladingshop.alabao.service.AlabaoRollOffRecordService;
import com.aladingshop.alabao.service.AlabaoRolloffConfigService;
import com.aladingshop.alabao.service.AlabaoTurnOutService;
import com.aladingshop.alabao.vo.AccountBankDto;
import com.aladingshop.alabao.vo.AddAccountBandDto;
import com.aladingshop.alabao.vo.TurnOutAmountDto;
import com.aladingshop.alabao.vo.TurnToBankSmsDto;
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
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
	@Autowired
	private AlabaoAccountBankService<AlabaoAccountBank> alabaoAccountBankService;
	@Autowired
	private AlabaoBankConfigService<AlabaoBankConfig> alabaoBankConfigService;
	@Autowired
	private SmsService<Sms> smsService;
	
	/***
	 * 如意宝-转出到银行卡--请求短信验证码
	 * @param alabaoSid
	 * @param account
	 * @param bankSmsCode -- 请求短信标识码
	 * @param sourceCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getBankSmsCode")
	public Object getBankSmsCode(
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac){
		try {
			LOGGER.info("注册如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			TurnToBankSmsDto turnToBankSmsDto = gson.fromJson(data, TurnToBankSmsDto.class);
			
			if (CheckIsEmpty.isEmpty(turnToBankSmsDto.getAlabaoSid(),turnToBankSmsDto.getAccount(),turnToBankSmsDto.getBankSmsCode(),turnToBankSmsDto.getSourceCode())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(turnToBankSmsDto.getAlabaoSid());
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			
			/**获取纯数字的验证码*/
			String registerRandomNum = RandomNumUtil.getRandom(RandomNumUtil.NUM, RandomNumUtil.REGISTER_USER_LENGTH);
			DataCache.put(turnToBankSmsDto.getBankSmsCode(), registerRandomNum);
			try {
				//注册发送的短信验证码
				smsService.sendSmsCode(registerRandomNum,turnToBankSmsDto.getAccount(),turnToBankSmsDto.getSourceCode(),SmsConstant.SMS_TYPE_TURNBANK_ID);
			} catch (Exception e) {
				e.printStackTrace();
				return Jsonp.error("短信服务器响应超时");
			}
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("请求出错" + e);
			return Jsonp.error();
		}
	}
	
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
			String smsCode = turnOutAmountDto.getSmsCode();
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
			String validSmsCode = (String) DataCache.get(bankSmsCode);
			if (!StringUtils.equals(smsCode,validSmsCode)) {
				return Jsonp.error("验证码输入有误!");
			}
			
			Long memberId = alabaoUserDto.getMemberId();
			String account = alabaoUserDto.getAccount();
			AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(account);
			if (!StringUtils.equals(alabaoAccount.getPayPassword(), MD5Util.MD5(password+MD5Util.getPasswordkey()))) {
				return Jsonp.acountFailureError("密码输入错误");
			}
			if (alabaoAccount.getBalance().compareTo(new BigDecimal(transferMoney))<0) {
				return Jsonp.error("您当前金额不足");
			}
			AlabaoRolloffConfig alabaoRolloffConfig = alabaoRolloffConfigService.findByRollOffCode(GlobalConfigConstant.ROLLOFFCODE);
			List<AlabaoRollOffRecord> list = alabaoRollOffRecordService.findByMemberIdTime(memberId, new Date());
			if (ObjectUtils.notEqual(alabaoRolloffConfig, null)) {
				// 小于0表示前面数小后面数大--超出转出限额，不能转
				if(alabaoRolloffConfig.getMaxMoneyLimit().compareTo(new BigDecimal(transferMoney))<0){
					return Jsonp.error("超出限额"+alabaoRolloffConfig.getMaxMoneyLimit());
				}
				if (!CollectionUtils.isEmpty(list) && list.size() >= alabaoRolloffConfig.getMaxNumberLimit()) {
					return Jsonp.error("超出次数"+alabaoRolloffConfig.getMaxNumberLimit());
				}
			}
			AlabaoRollOffRecord alabaoRollOffRecord = new AlabaoRollOffRecord();
			alabaoRollOffRecord.setAccount(account);
			alabaoRollOffRecord.setMemberId(memberId);
			alabaoRollOffRecord.setIsSuccess(CommonConstant.NO);
			alabaoRollOffRecord.setTransferMoney(new BigDecimal(turnOutAmountDto.getTransferMoney()));
			alabaoRollOffRecord.setRollOffAccountType("2");
			
			alabaoRollOffRecord.setAfterChangeMoney(alabaoAccount.getBalance().subtract(new BigDecimal(turnOutAmountDto.getTransferMoney())));
			alabaoRollOffRecord.setBeforeChangeMoney(alabaoAccount.getBalance());
			alabaoRollOffRecordService.add(alabaoRollOffRecord);
			AlabaoTurnOut alabaoTurnOut = new AlabaoTurnOut();
			alabaoTurnOut.setMemberId(memberId);
			alabaoTurnOut.setAccount(account);
			alabaoTurnOut.setTransferMoney(new BigDecimal(turnOutAmountDto.getTransferMoney()));
			alabaoTurnOut.setBankName(bankName);
			alabaoTurnOut.setBankBranchName(bankBranchName);
			alabaoTurnOut.setCardNo(cardNo);
			alabaoTurnOut.setCardHolder(cardHolder);
			alabaoTurnOut.setTurnOutStatus("1");
			alabaoTurnOutService.add(alabaoTurnOut);
			/**转出时余额做改变*/
			alabaoAccount.setBalance(alabaoAccount.getBalance().subtract(new BigDecimal(turnOutAmountDto.getTransferMoney())));
			alabaoAccountService.updateByAccount(alabaoAccount);
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
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			List<AlabaoAccountBank> list = alabaoAccountBankService.findByAccount(alabaoUserDto.getAccount());
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
				accountBankList.add(accountBankDto);
			}
			return Jsonp_data.success(accountBankList);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取用户银行卡接口出错", e);
			return Jsonp.error("获取卡信息出错");
		}
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
			String bankName = addAccountBandDto.getBankName();
			String cardHolder = addAccountBandDto.getCardHolder();
			String bankBranchName=addAccountBandDto.getBankBranchName();
			LOGGER.info("转码前银行名称:"+bankName+"转码前持卡人姓名:"+cardHolder);
			bankName = URLDecoder.decode(bankName, "UTF-8");
			cardHolder = URLDecoder.decode(cardHolder, "UTF-8");
			bankBranchName = URLDecoder.decode(bankBranchName, "UTF-8");
			LOGGER.info("解码后银行名称:"+bankName+"解码后持卡人姓名:"+cardHolder);
			AlabaoAccountBank alabaoAccountBank = new AlabaoAccountBank();
			alabaoAccountBank.setAccount(alabaoUserDto.getAccount());
			alabaoAccountBank.setMemberId(alabaoUserDto.getMemberId());
			alabaoAccountBank.setBankName(bankName);
			alabaoAccountBank.setBankBranchName(bankBranchName);
			alabaoAccountBank.setCardNo(addAccountBandDto.getCardNo());
			alabaoAccountBank.setCardHolder(cardHolder);
			alabaoAccountBank.setIsDelete(CommonConstant.NO);
			alabaoAccountBankService.add(alabaoAccountBank);
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("添加卡信息接口出错", e);
			return Jsonp.error("添加卡信息出错");
		}
	}
}
