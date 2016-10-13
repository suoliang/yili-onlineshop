package com.fushionbaby.facade.biz.common.sms.impl;

import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.facade.biz.common.sms.SmsServiceFacade;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.model.SmsTypeConfig;
import com.fushionbaby.sms.service.SmsService;
import com.fushionbaby.sms.service.SmsTypeConfigService;

/***
 * 
 * @description 类描述... 短信服务
 * @author 徐培峻
 * @date 2015年8月13日上午9:22:31
 */
@Service
public class SmsServiceFacadeImpl implements  SmsServiceFacade{

	
	@Autowired
	private SmsService<Sms> smsService;
	
	@Autowired
	private SmsTypeConfigService<SmsTypeConfig> smsTypeConfigService;
	
	private static final Log SmsLog=LogFactory.getLog(SmsServiceFacadeImpl.class );

	public void sendSmsRegisterCode(String code, String phone, String source,Long smsTypeId) {
	try {
			this.smsService.sendSmsCode(code, phone, source,smsTypeId);
		} catch (RemoteException e) {
			SmsLog.error("web:SmsServiceFacadeImpl.java发送短信验证码异常", e);
		}
	}

	public void sendSmsYiDuoBaoCode(String cardNo, String phone, String source, String password,String faceValue) {
		try {
			SmsTypeConfig smsTypeConfig=this.smsTypeConfigService.findById(SmsConstant.SMS_TYPE_YIDUOBAO_ID);
			String content=smsTypeConfig.getSmsTemplate();
			       content=content
			    		   .replace(SmsConstant.SMS_TEMLATE_FACEVALUE, faceValue)
			    		   .replace(SmsConstant.SMS_TEMPLATE_CODE, cardNo)
			    		   .replace(SmsConstant.SMS_TEMLATE_PASSWORD, password);
			this.smsService.sendSmsYiDuoBaoCode(content, phone,source,SmsConstant.SMS_TYPE_YIDUOBAO_ID);
		} catch (Exception e) {
			SmsLog.error("web:SmsServiceFacadeImpl.java益多宝发送短信验证码异常", e);
		}
	}

	public boolean getNumByPhone(String phone) {
		Integer integer = smsService.findNumByLastTime(phone);
		if (integer >=3 ) {
			return true;
		}
		return false;
	}
	
}
