package com.fushionbaby.app.controller.alabao;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoContacts;
import com.aladingshop.alabao.model.AlabaoLimit;
import com.aladingshop.alabao.model.AlabaoRollOffRecord;
import com.aladingshop.alabao.model.AlabaoRolloffConfig;
import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.aladingshop.alabao.model.AlabaoTurnToAlabao;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoContactsService;
import com.aladingshop.alabao.service.AlabaoLimitService;
import com.aladingshop.alabao.service.AlabaoRollOffRecordService;
import com.aladingshop.alabao.service.AlabaoRolloffConfigService;
import com.aladingshop.alabao.service.AlabaoShiftToRecordService;
import com.aladingshop.alabao.service.AlabaoTurnToAlabaoService;
import com.aladingshop.alabao.vo.AlabaoToAlabaoDto;
import com.aladingshop.card.dto.BaseReqDto;
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.AlabaoPayTypeConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.alabao.AlabaoTurnOutConstant;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.GetSerialNumUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrImportanceConfig;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.facade.biz.common.sms.SmsServiceFacade;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Controller
@RequestMapping("/alabao")
public class AlabaoTurnToAlabaoController {
	
	private static final Log LOGGER = LogFactory.getLog(AlabaoTurnToAlabaoController.class);
	
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	@Autowired
	private AlabaoRolloffConfigService<AlabaoRolloffConfig> alabaoRolloffConfigService;
	@Autowired
	private AlabaoRollOffRecordService<AlabaoRollOffRecord> alabaoRollOffRecordService;
	@Autowired
	private AlabaoTurnToAlabaoService<AlabaoTurnToAlabao> turnToAlabaoService;
	@Autowired
	private AlabaoShiftToRecordService<AlabaoShiftToRecord> alabaoShiftToRecordService;
	@Autowired
	private AlabaoLimitService<AlabaoLimit> alabaoLimitService;
	/** 重要配置*/
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	@Autowired
	private AlabaoContactsService<AlabaoContacts> alabaoContactsService;
	@Autowired
	private SmsServiceFacade smsServiceFacade;
	/**发送短信通知的金额最小值*/
	private   BigDecimal LOEEST_MONEY=new BigDecimal(100.00);
	/**注入短信*/
	@Autowired
	private SmsService<Sms> smsService;
	/**
	 * @description 验证转出账户有效性
	 * @param alabaoSid
	 * @param otherAccount
	 * @param mac
	 * @return
	 */
	@ResponseBody
	@RequestMapping("inputAccount")
	public Object inputAccount(
			@RequestParam(value="data")String data,
			@RequestParam(value="mac")String mac){
		try {
			LOGGER.info("验证转出有效性如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
			String otherAccount = json.getAsJsonObject().get("otherAccount").getAsString();
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			/**不能自己转给自己*/
			if (StringUtils.equalsIgnoreCase(otherAccount, alabaoUserDto.getAccount())) {
				return Jsonp.error("请不要转给自己");
			}
			/**对方账户信息*/
			AlabaoAccount otherAlaAccount = alabaoAccountService.findByAccount(otherAccount);
			if (ObjectUtils.equals(null, otherAlaAccount)) {
				return Jsonp.error("该账户不存在");
			}
			
			AlabaoToAlabaoDto alabaoToAlabaoDto = new AlabaoToAlabaoDto();
			alabaoToAlabaoDto.setName(otherAlaAccount.getTrueName()==null?"":otherAlaAccount.getTrueName());
			alabaoToAlabaoDto.setOtherAccount(otherAlaAccount.getAccount());
			AlabaoAccount account = alabaoAccountService.findByAccount(alabaoUserDto.getAccount());
			alabaoToAlabaoDto.setBalance(NumberFormatUtil.numberFormat(account.getBalance()));
			return Jsonp_data.success(alabaoToAlabaoDto);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("验证转出账户有效性接口出错", e);
			return Jsonp.error("转出到账户出错");
		}
	}
	
	/**
	 * @description 转出到如意宝账户 -- 主操作
	 * @param alabaoSid		如意宝登录标识
	 * @param otherAccount  对方账户
	 * @param turnOutAmount 转出金额
	 * @param memo          转账备注字段
	 * @param password      支付密码
	 * @param mac
	 * @return
	 */
	@ResponseBody
	@RequestMapping("turnToAlabao")
	public Object turnToAlabao(
			@RequestParam(value="data")String data,
			@RequestParam(value="mac")String mac){
		try {
			LOGGER.info("验证转出有效性如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
			String otherAccount = json.getAsJsonObject().get("otherAccount").getAsString();
			String turnOutAmount = json.getAsJsonObject().get("turnOutAmount").getAsString();
			String memo = json.getAsJsonObject().get("memo").getAsString();
			String password = json.getAsJsonObject().get("password").getAsString();
			/**加入存入缓存中的短信标志 */
			String smsRandomNum = json.getAsJsonObject().get("smsRandomNum").getAsString();
			
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			/**不能自己转给自己*/
			if (StringUtils.equalsIgnoreCase(otherAccount, alabaoUserDto.getAccount())) {
				return Jsonp.error("请不要转给自己");
			}
			/**对方账户信息*/
			AlabaoAccount otherAlaAccount = alabaoAccountService.findByAccount(otherAccount);
			if (ObjectUtils.equals(null, otherAlaAccount)) {
				return Jsonp.error("对方账户不存在");
			}
			
			Long memberId = alabaoUserDto.getMemberId();
			String account = alabaoUserDto.getAccount();
			/**账户信息*/
			AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(account);
			if (!StringUtils.equals(alabaoAccount.getPayPassword(), MD5Util.MD5(password+MD5Util.getPasswordkey()))) {
				return Jsonp.acountFailureError("密码输入错误");
			}
			if (alabaoAccount.getBalance().compareTo(new BigDecimal(turnOutAmount))<0) {
				return Jsonp.error("您当前金额不足");
			}
			AlabaoLimit alabaoLimit = alabaoLimitService.findByAccount(account);
			/**限制对象为空--走正常限制*/
			if (ObjectUtils.equals(alabaoLimit, null)) {
				AlabaoRolloffConfig alabaoRolloffConfig = alabaoRolloffConfigService.findByRollOffCode(GlobalConfigConstant.ROLLOFFCODE);
				/**暂不操作如意宝转如意宝每天的转出限制*/
				//List<AlabaoRollOffRecord> list = alabaoRollOffRecordService.findByMemberIdTime(memberId, new Date());
				if (ObjectUtils.notEqual(alabaoRolloffConfig, null)) {
					// 小于0表示前面数小后面数大--超出转出限额，不能转
					if(alabaoRolloffConfig.getMaxMoneyLimit().compareTo(new BigDecimal(turnOutAmount))<0){
						return Jsonp.error("超出限额"+alabaoRolloffConfig.getMaxMoneyLimit());
					}
					//if (!CollectionUtils.isEmpty(list) && list.size() >= alabaoRolloffConfig.getMaxNumberLimit()) {
					//return Jsonp.error("超出次数"+alabaoRolloffConfig.getMaxNumberLimit());
					//}
				}
			} else {
				// 小于0表示前面数小后面数大--超出转出限额，不能转
				if(alabaoLimit.getMoneyLimit().compareTo(new BigDecimal(turnOutAmount))<0){
					return Jsonp.error("会员超出限额"+alabaoLimit.getMoneyLimit());
				}
				//if (!CollectionUtils.isEmpty(list) && list.size() >= alabaoLimit.getNumberLimit()) {
					//return Jsonp.error("会员超出次数"+alabaoLimit.getNumberLimit());
				//}
			}
			LOGGER.info("转码前转账备注:"+memo);
			memo=URLDecoder.decode(memo, "UTF-8");
			LOGGER.info("解码后转账备注:"+memo);
			String serialNum = GetSerialNumUtil.generateSerialNum();
			BigDecimal transfer_money=new BigDecimal(turnOutAmount);
			BigDecimal old_balance=alabaoAccount.getBalance();
			/**本方转出记录*/
			saveRollOffRecord(memo, memberId, account, serialNum,transfer_money, old_balance);
			/**本方转出方记录*/
			saveTurnTo(otherAccount, memo, memberId, account, serialNum,transfer_money);
			/**对方转入记录*/
			saveShiftToRecord(otherAccount, otherAlaAccount, serialNum,transfer_money);
			/**转出时余额做改变*/
			alabaoAccount.setBalance(alabaoAccount.getBalance().subtract(transfer_money));
			alabaoAccountService.updateByAccount(alabaoAccount);
			/**对方余额做增加改变操作*/
			otherAlaAccount.setBalance(otherAlaAccount.getBalance().add(transfer_money));
			alabaoAccountService.updateByAccount(otherAlaAccount);
			
			
			/**转出完成后，更新阿拉宝账户常用联系人表*/
			AlabaoContacts alabaoContacts = alabaoContactsService.findByAccountAndLinkAccount(alabaoUserDto.getAccount(),otherAccount);
			if(alabaoContacts == null){
				alabaoContacts = new AlabaoContacts();
				alabaoContacts.setAccount(alabaoUserDto.getAccount());
				alabaoContacts.setLinkAccount(otherAccount);
				alabaoContactsService.add(alabaoContacts);
			}else{
				alabaoContactsService.update(alabaoContacts);
			}
			
			/**删除缓存中的验证码*/
			if(StringUtils.isNotBlank(smsRandomNum))
			    DataCache.remove(smsRandomNum);
			LOGGER.info(DateFormat.dateToString(new Date())+"如意消费卡*********"+alabaoUserDto.getAccount()+"*********转出如意消费卡******"+otherAccount+"使用的验证码  *************"+smsRandomNum+"删除");
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("转出到如意宝接口出错", e);
			return Jsonp.error("出错");
		}
	}

	private void saveShiftToRecord(String otherAccount,AlabaoAccount otherAlaAccount, String serialNum,
			BigDecimal transfer_money) {
		AlabaoShiftToRecord alabaoShiftToRecord = new AlabaoShiftToRecord();
		alabaoShiftToRecord.setAccount(otherAccount);
		alabaoShiftToRecord.setMemberId(otherAlaAccount.getMemberId());
		alabaoShiftToRecord.setTransferMoney(transfer_money);
		alabaoShiftToRecord.setShiftToAccountType(AlabaoPayTypeConstant.ALABAO);
		alabaoShiftToRecord.setSerialNum(serialNum);
		
		alabaoShiftToRecord.setAfterChangeMoney(otherAlaAccount.getBalance().add(transfer_money));
		alabaoShiftToRecord.setBeforeChangeMoney(otherAlaAccount.getBalance());
		alabaoShiftToRecordService.add(alabaoShiftToRecord);
	}

	private void saveTurnTo(String otherAccount, String memo, Long memberId,
			String account, String serialNum, BigDecimal transfer_money) {
		AlabaoTurnToAlabao turnToAlabao = new AlabaoTurnToAlabao();
		turnToAlabao.setMemberId(memberId);
		turnToAlabao.setAccount(account);
		turnToAlabao.setOtherAccount(otherAccount);
		turnToAlabao.setTransferMoney(transfer_money);
		turnToAlabao.setTurnOutStatus(CommonConstant.YES);
		turnToAlabao.setSerialNum(serialNum);
		turnToAlabao.setMemo(memo);
		turnToAlabaoService.add(turnToAlabao);
	}

	private void saveRollOffRecord(String memo, Long memberId, String account,
			String serialNum, BigDecimal transfer_money, BigDecimal old_balance) {
		AlabaoRollOffRecord alabaoRollOffRecord = new AlabaoRollOffRecord();
		alabaoRollOffRecord.setAccount(account);
		alabaoRollOffRecord.setMemberId(memberId);
		alabaoRollOffRecord.setIsSuccess(CommonConstant.YES);
		alabaoRollOffRecord.setTransferMoney(transfer_money);
		alabaoRollOffRecord.setRollOffAccountType(AlabaoTurnOutConstant.ROLL_OFF_TYPE_ALABAO);
		alabaoRollOffRecord.setSerialNum(serialNum);
		alabaoRollOffRecord.setMemo(memo);
		
		alabaoRollOffRecord.setAfterChangeMoney(old_balance.subtract(transfer_money));
		alabaoRollOffRecord.setBeforeChangeMoney(old_balance);
		alabaoRollOffRecordService.add(alabaoRollOffRecord);
	}
	
	/**
	 * @description 转出到如意宝账户 -- 校验
	 * @param alabaoSid		如意宝登录标识
	 * @param otherAccount  对方账户
	 * @param turnOutAmount 转出金额
	 * @param mac
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkTurnToAlabao")
	public Object checkTurnToAlabao(
			@RequestParam(value="data")String data,
			@RequestParam(value="mac")String mac){
		try {
			LOGGER.info("验证转出有效性如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
			String otherAccount = json.getAsJsonObject().get("otherAccount").getAsString();
			String turnOutAmount = json.getAsJsonObject().get("turnOutAmount").getAsString();
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			/**不能自己转给自己*/
			if (StringUtils.equalsIgnoreCase(otherAccount, alabaoUserDto.getAccount())) {
				return Jsonp.error("请不要转给自己");
			}
			/**对方账户信息*/
			AlabaoAccount otherAlaAccount = alabaoAccountService.findByAccount(otherAccount);
			if (ObjectUtils.equals(null, otherAlaAccount)) {
				return Jsonp.error("对方账户不存在");
			}
			String account = alabaoUserDto.getAccount();
			/**账户信息*/
			AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(account);
			if (alabaoAccount.getBalance().compareTo(new BigDecimal(turnOutAmount))<0) {
				return Jsonp.error("您当前金额不足");
			}
			AlabaoLimit alabaoLimit = alabaoLimitService.findByAccount(account);
			/**限制对象为空--走正常限制*/
			if (ObjectUtils.equals(alabaoLimit, null)) {
				AlabaoRolloffConfig alabaoRolloffConfig = alabaoRolloffConfigService.findByRollOffCode(GlobalConfigConstant.ROLLOFFCODE);
				/**暂不操作如意宝转如意宝每天的转出限制*/
				//List<AlabaoRollOffRecord> list = alabaoRollOffRecordService.findByMemberIdTime(memberId, new Date());
				if (ObjectUtils.notEqual(alabaoRolloffConfig, null)) {
					// 小于0表示前面数小后面数大--超出转出限额，不能转
					if(alabaoRolloffConfig.getMaxMoneyLimit().compareTo(new BigDecimal(turnOutAmount))<0){
						return Jsonp.error("超出限额"+alabaoRolloffConfig.getMaxMoneyLimit());
					}
					//if (!CollectionUtils.isEmpty(list) && list.size() >= alabaoRolloffConfig.getMaxNumberLimit()) {
					//return Jsonp.error("超出次数"+alabaoRolloffConfig.getMaxNumberLimit());
					//}
				}
			} else {
				// 小于0表示前面数小后面数大--超出转出限额，不能转
				if(alabaoLimit.getMoneyLimit().compareTo(new BigDecimal(turnOutAmount))<0){
					return Jsonp.error("会员超出限额"+alabaoLimit.getMoneyLimit());
				}
				//if (!CollectionUtils.isEmpty(list) && list.size() >= alabaoLimit.getNumberLimit()) {
					//return Jsonp.error("会员超出次数"+alabaoLimit.getNumberLimit());
				//}
			}
			
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("转出到如意宝接口出错", e);
			return Jsonp.error("出错");
		}
	}
	
	/***
	 * 发送确认短信
	 * @return  
	 */
	@ResponseBody
	@RequestMapping("sendSmsCode")
	public Object sendSmsCode(@RequestParam("data") String data, @RequestParam("mac") String mac){
		LOGGER.info("如意消费卡交易  确认发送短信  接口action--请求报文：{" + data + "}");
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}
		Gson gson = new Gson();
		BaseReqDto reDto = gson.fromJson(data, BaseReqDto.class);
		if (CheckIsEmpty.isEmpty(reDto.getSid(), reDto.getAlabaoSid(),reDto.getSourceCode(),reDto.getSmsRandomNum())) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		String alabaoSid = reDto.getAlabaoSid();
		AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
		if (ObjectUtils.equals(alabaoUserDto, null)) {
			return Jsonp.noLoginError("您还未登录如意宝，请登录");
		}
		if (smsServiceFacade.getNumByPhone(reDto.getPhone())) {
			return Jsonp.smsNumberLimit();
		}
		String smsCode = RandomNumUtil.getRandom(RandomNumUtil.NUM,SmsConstant.SMS_CODE_LENGTH);
		DataCache.put(reDto.getSmsRandomNum(), smsCode);
		//发送验证短信
		try {
			smsService.sendAlabaoTradeMessage(alabaoUserDto.getAccount(),reDto.getSourceCode(),smsCode);
		} catch (RemoteException e) {
			LOGGER.error("AlabaoTurnToAlabaoController.java 发送验证短信异常", e);
			return Jsonp.error("发送验证短信异常");
		}
		return Jsonp.success();
	}
	
	
	/***
	 * 验证验证码是否正确
	 * @param data
	 * @param mac
	 * @return
	 */
	@RequestMapping("checkSmsCode")
	@ResponseBody
	public Object checkSmsCode(@RequestParam("data") String data, @RequestParam("mac") String mac){
		LOGGER.info("如意消费卡交易验证短信   接口action--请求报文：{" + data + "}");
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}
		Gson gson = new Gson();
		BaseReqDto reDto = gson.fromJson(data, BaseReqDto.class);

		if (CheckIsEmpty.isEmpty(reDto.getSid(), reDto.getAlabaoSid(),reDto.getSourceCode(),reDto.getSmsRandomNum())) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		String alabaoSid = reDto.getAlabaoSid();
		AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
		if (ObjectUtils.equals(alabaoUserDto, null)) {
			return Jsonp.noLoginError("您还未登录如意宝，请登录");
		}
		String code=reDto.getSmsCode();
		String oldCode = (String) DataCache.get(reDto.getSmsRandomNum());
		if (!StringUtils.equals(code,oldCode)) {
			return Jsonp.error("验证码输入有误!");
		}
		/***删除缓存验证码*/
		//DataCache.remove(reDto.getType());
		return Jsonp.success();
	}
	
	
	
	/****
	 * 转出成功之后，发送短信告知被转入用户
	 * @param data
	 * @param mac
	 * @return
	 */
	@ResponseBody
	@RequestMapping("notifyMessage")
	public Object sendTradeMessage(@RequestParam("data") String data, @RequestParam("mac") String mac){
		LOGGER.info("如意消费卡交易通知短信  接口action--请求报文：{" + data + "}");
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}
		JsonParser jsonParser = new JsonParser();
		JsonElement json = jsonParser.parse(data);
		String receiveAccount = json.getAsJsonObject().get("receiveAccount").getAsString();
		String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
		String totalActual = json.getAsJsonObject().get("totalActual").getAsString();
		String sourceCode = json.getAsJsonObject().get("sourceCode").getAsString();
		if (CheckIsEmpty.isEmpty(receiveAccount,alabaoSid,totalActual)) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
		if (ObjectUtils.equals(alabaoUserDto, null)) {
			return Jsonp.noLoginError("您还未登录如意宝，请登录");
		}
		try {
			/**满一百之后发短信通知*/
			SysmgrImportanceConfig config=this.sysmgrImportanceConfigService.findByCode("LOWEST_MONEY_TO_SEND_SMS");
			if (config!=null) {
				LOEEST_MONEY=new BigDecimal(config.getValue());
			}
			AlabaoAccount account=this.alabaoAccountService.findByAccount(alabaoUserDto.getAccount());
			if(LOEEST_MONEY.compareTo(new BigDecimal(totalActual))<=0)
			smsService.tradeNotifyMessage(account==null?alabaoUserDto.getAccount():account.getTrueName(),receiveAccount,sourceCode,totalActual);
		} catch (RemoteException e) {
			LOGGER.error("AlabaoTurnToAlabaoController.java 发送验证短信异常", e);
			return Jsonp.error("发送验证短信异常");
		}
		return Jsonp.success();
	}
	
	
}
