package com.fushionbaby.app.controller.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoShiftToRecordService;
import com.fushionbaby.act.activity.dto.EntityCardReqDto;
import com.fushionbaby.act.activity.dto.EntityCardResDto;
import com.fushionbaby.act.activity.model.ActEntityCard;
import com.fushionbaby.act.activity.model.ActEntityCardUseRecord;
import com.fushionbaby.act.activity.service.ActEntityCardService;
import com.fushionbaby.act.activity.service.ActEntityCardUseRecordService;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.AlabaoPayTypeConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;
import com.google.gson.Gson;
/***
 * 实体卡部分转入如意消费卡
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月10日上午9:31:53
 */
@Controller
@RequestMapping("/entityCard")
public class EntityCardController {
	
	private static final Log LOGGER = LogFactory.getLog(EntityCardController.class);

	/**实体卡*/
	@Autowired
	private ActEntityCardService<ActEntityCard> actEntityCardService;
	/**注入短信*/
	@Autowired
	private SmsService<Sms> smsService;
	/** 如意消费卡账户*/
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	/**阿拉宝转入记录*/
	@Autowired
	private AlabaoShiftToRecordService<AlabaoShiftToRecord> alabaoShiftToRecordService;
	/** 使用记录*/
	@Autowired
	private ActEntityCardUseRecordService<ActEntityCardUseRecord>  actEntityCardUseRecordService;
/***
 * 获得 该实体卡的相关信息
 * 
 * @param data   卡号
 * @param mac    
 * @return
 */
	@ResponseBody
	@RequestMapping("getCardInfo")
	public Object getCardInfo(@RequestParam("data") String data, @RequestParam("mac") String mac) {
		try {
			LOGGER.info("获取 实体卡 信息详情 接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			EntityCardReqDto reqDto = gson.fromJson(data, EntityCardReqDto.class);

			if (CheckIsEmpty.isEmpty(reqDto.getAlabaoSid(),reqDto.getEntityCode())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(reqDto.getAlabaoSid());
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("您还未登录如意宝，请登录");
			}
			EntityCardResDto  resDto=this.actEntityCardService.getCardInfoByCode(reqDto.getEntityCode());
			return Jsonp_data.success(resDto);
		} catch (Exception e) {
			LOGGER.error("app  EntityCardController.java  获取该实体卡信息异常  getCardInfo " + e);
			return Jsonp.error("请输入正确卡号");
		}
	}

	/**
	 * 
	 * 
	 * @param data
	 * @param mac
	 * @return
	 */
	@ResponseBody
	@RequestMapping("useEntityCard")
	public Object useEntityCard(@RequestParam("data") String data, @RequestParam("mac") String mac) {
		try {
			LOGGER.info("使用 实体卡 接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			EntityCardReqDto reqDto = gson.fromJson(data, EntityCardReqDto.class);
			if (CheckIsEmpty.isEmpty(reqDto.getAlabaoSid(),reqDto.getEntityCode(),reqDto.getPassword())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(reqDto.getAlabaoSid());
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("您还未登录如意宝，请登录");
			}
			AlabaoAccount alabaoAccount = alabaoAccountService.findById(alabaoUserDto.getAlabaoId());
			if (ObjectUtils.equals(alabaoAccount, null)) {
				return Jsonp.error("该用户如意消费卡账户不存在");
			}
			/**在此判断使用记录中有没有*/
			List<ActEntityCardUseRecord> cardUseRecordList=this.actEntityCardUseRecordService.findByCode(reqDto.getEntityCode());
			if(cardUseRecordList!=null&&cardUseRecordList.size()>0&&"1".equals(cardUseRecordList.get(0).getUseType())){
				return Jsonp.error("该卡已使用过");
			}
			EntityCardResDto resDto=this.actEntityCardService.useEntityCard(reqDto);
			if(resDto.getStatus().equals("2"))
				return Jsonp.error("您输入的密码不正确，请重试");
			/**做使用该实体卡之后的动作*/
			BigDecimal money=resDto.getEntityCardMoney();
			String seriNo=DateFormat.dateToSerialNo(new Date());
			
			AlabaoShiftToRecord alabaoShiftToRecord = new AlabaoShiftToRecord();
			alabaoShiftToRecord.setAccount(alabaoAccount.getAccount());
			/**必须是如意宝的memberId*/
			alabaoShiftToRecord.setMemberId(alabaoAccount.getMemberId());
			alabaoShiftToRecord.setShiftToAccountType(AlabaoPayTypeConstant.ALABAO_APP_ENTITY_CARD);
			alabaoShiftToRecord.setTransferMoney(money);
			alabaoShiftToRecord.setCreateTime(new Date());
			
			alabaoShiftToRecord.setAfterChangeMoney(alabaoAccount.getBalance().add(money));
			alabaoShiftToRecord.setBeforeChangeMoney(alabaoAccount.getBalance());
			alabaoShiftToRecord.setSerialNum(seriNo);
			alabaoShiftToRecordService.add(alabaoShiftToRecord);
			LOGGER.info("如意宝账户现有金额:"+alabaoAccount.getBalance()+"转入金额"+money);
			alabaoAccount.setBalance(alabaoAccount.getBalance().add(money));
			alabaoAccountService.updateByMemberId(alabaoAccount);
			/**使用记录添加*/
			saveUseRecord(reqDto, alabaoAccount, money,seriNo);
			
			return Jsonp.success();
		} catch (Exception e) {
			LOGGER.error("app  EntityCardController.java  获取该实体卡信息异常  useEntityCard " + e);
			return Jsonp.error("如意消费卡充值异常");
		}
	}

	private void saveUseRecord(EntityCardReqDto reqDto,AlabaoAccount alabaoAccount, BigDecimal money,String seriNo) {
		ActEntityCardUseRecord record=new ActEntityCardUseRecord();
		record.setCardNo(reqDto.getEntityCode());
		record.setCreateTime(new Date());
		record.setMoney(money);
		record.setOrderCode(alabaoAccount.getAccount());
		record.setUseSource(reqDto.getSourceCode());
		record.setUseType("1");
		record.setUpdateId(alabaoAccount.getMemberId());
		record.setSerialNo(seriNo);
		record.setMemo("实体卡卡号为:"+reqDto.getEntityCode()+"转入如意消费卡账号为："+alabaoAccount.getAccount()+"。");
		actEntityCardUseRecordService.add(record);
	}
	
}
