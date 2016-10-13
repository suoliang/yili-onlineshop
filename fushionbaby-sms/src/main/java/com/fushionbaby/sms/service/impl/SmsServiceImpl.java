package com.fushionbaby.sms.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import cn.b2m.eucp.sdkhttp.example.SmsClient;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrDictConfig;
import com.fushionbaby.config.service.SysmgrDictConfigService;
import com.fushionbaby.sms.dao.SmsDao;
import com.fushionbaby.sms.dao.SmsTypeConfigDao;
import com.fushionbaby.sms.dto.Sms5cReqDto;
import com.fushionbaby.sms.dto.Sms5cResDto;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.model.SmsTypeConfig;
import com.fushionbaby.sms.service.Sms5cService;
import com.fushionbaby.sms.service.SmsService;

/**
 * 此类的代码可进行重构
 * @author King
 * 
 */
@Service
public class SmsServiceImpl implements SmsService<Sms> {

	@Autowired
	private SmsDao smsDao;
	
	@Autowired
	private SmsTypeConfigDao smsTypeDao;
	
	/**重要配置*/
//	@Autowired
//	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
//	
	/**字典配置*/
	@Autowired
	private SysmgrDictConfigService<SysmgrDictConfig> sysmgrDictConfigService;
	
	
	/**美联软通 短信服务*/
	@Autowired
	private Sms5cService sms5cService;
	

	public void add(Sms sms) throws DataAccessException {
		smsDao.add(sms);
	}



	public void deleteById(Long id) throws DataAccessException {
		smsDao.deleteById(id);
	}

	public void update(Sms sms) throws DataAccessException {
		smsDao.update(sms);
	}

	public Sms findById(Long id) throws DataAccessException {
		return smsDao.findById(id);
	}

	

	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {
		Integer total = smsDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<Sms> list = smsDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<Sms>());
		}
		return page;
	}

	/***
	 * web app 注册 找回密码
	 * @param registerRandomNum 验证码
	 * @param phone 手机号
	 * @param source 来源
	 * @param smsTypeId 短信类型（ 1注册，2忘记密码）
	 * @throws RemoteException
	 */
	public void sendSmsCode(String registerRandomNum, String phone,String source,Long smsTypeId) throws RemoteException {
		/**
		 * id为1代表用户注册
		 * 拼接替换要发送的短信模板 
		 */
		SmsTypeConfig smsType = smsTypeDao.findById(smsTypeId);
		String smsTemplate = smsType.getSmsTemplate();
		String sendSmsTemplate = smsTemplate.replace(SmsConstant.SMS_TEMPLATE_CODE,registerRandomNum);
		Sms sms = new Sms();
		sms.setMemberName(phone);
		sms.setMobile(phone);
		sms.setSendTime(new Date());
		sms.setSourceCode(source);
		sms.setSmsTypeId(smsTypeId);//注册
		sms.setSmsContent(sendSmsTemplate);
		sms.setSendLevel(SmsConstant.SMS_SEND_LEVEL);
		/**亿美*/
		sendMessageByYIiMei(phone, sendSmsTemplate, sms);
		  /** 美联软通*/
		sendMessageByMeiLian(phone, sendSmsTemplate, sms);
		smsDao.add(sms);
	}

	/***
	 * 美联软通的发送短信
	 * @param phone
	 * @param sendSmsTemplate
	 * @param sms
	 */
	private void sendMessageByMeiLian(String phone, String sendSmsTemplate,Sms sms) {
		//String SMS_COMPANY_FLAG=sysmgrImportanceConfigService.findByCode("SMS_COMPANY_FLAG").getValue();
		String SMS_COMPANY_FLAG="1";
		List<SysmgrDictConfig> dictFonfig = this.sysmgrDictConfigService.findByLabelValueType(null, null, "SMS_COMPANY_FLAG");
		if(dictFonfig!=null&&dictFonfig.size()>0)
			SMS_COMPANY_FLAG=dictFonfig.get(0).getValue();
		if("2".equals(SMS_COMPANY_FLAG)){
			  Sms5cReqDto req=new Sms5cReqDto(phone, sendSmsTemplate);
			  Sms5cResDto res =sms5cService.sendSmsMessage(req);
			  if("success".equals(res.getStatus())){
				  sms.setStatus(SmsConstant.SMS_SUCCESS_STATUS);
			  }else{
				  sms.setStatus(SmsConstant.SMS_ERROR_STATUS);
			  }
			  sms.setSmsSendFlag(Integer.valueOf(SMS_COMPANY_FLAG));
		   }
	}

	/***
	 * 亿美的发送短信 (默认都是亿美)
	 * @param phone
	 * @param sendSmsTemplate
	 * @param sms
	 * @throws RemoteException
	 */
	private void sendMessageByYIiMei(String phone, String sendSmsTemplate,Sms sms) throws RemoteException {
		//String SMS_COMPANY_FLAG=sysmgrImportanceConfigService.findByCode("SMS_COMPANY_FLAG").getValue();
		String SMS_COMPANY_FLAG="1";
		List<SysmgrDictConfig> dictFonfig = this.sysmgrDictConfigService.findByLabelValueType(null, null, "SMS_COMPANY_FLAG");
		if(dictFonfig!=null&&dictFonfig.size()>0)
			SMS_COMPANY_FLAG=dictFonfig.get(0).getValue();
		if("1".equals(SMS_COMPANY_FLAG))
		{
			int i = SmsClient.sendSMS(phone, sendSmsTemplate);
			if (i == 0) {
				sms.setStatus(SmsConstant.SMS_SUCCESS_STATUS);
			} else {
				sms.setStatus(SmsConstant.SMS_ERROR_STATUS);
			}
			sms.setSmsSendFlag(Integer.valueOf(SMS_COMPANY_FLAG));
	   }
	}
	
	
	/**
	 * 发货提醒
	 * @param code
	 * 			订单号
	 * @param expressCompany
	 * 			快递公司
	 * @param expressNo
	 * 			快递单号
	 * @param phone
	 * 			手机号
	 * @param source
	 * 			来源 -- 运营后台
	 * @param name
	 * 			会员名称
	 * @throws RemoteException 
	 */
	public void sendSmsDelivery(String code,String expressCompany,String expressNo, String phone, String source,String name) throws RemoteException {
		/**
		 * id为3代表发货提醒
		 */
		SmsTypeConfig smsType = smsTypeDao.findById(SmsConstant.SMS_TYPE_DELIVERY_ID);
		String smsTemplate = smsType.getSmsTemplate();
		String codeTemplate = smsTemplate.replace(SmsConstant.SMS_TEMPLATE_CODE,code);//订单号
		codeTemplate= codeTemplate.replace(SmsConstant.SMS_EXPRESS_COMPANY, expressCompany);//快递公司
		String sendSmsTemplate = codeTemplate.replace(SmsConstant.SMS_EXPRESS_NO, expressNo);//快递单号
		/***
		 * 发送的短信内容添加到短信表
		 */
		Sms sms = new Sms();
		sms.setMemberName(name);
		sms.setMobile(phone);
		sms.setSendTime(new Date());
		sms.setSourceCode(source);// 来源
		sms.setSmsTypeId(SmsConstant.SMS_TYPE_DELIVERY_ID);
		sms.setSmsContent(sendSmsTemplate);
		sms.setSendLevel(SmsConstant.SMS_SEND_LEVEL);
		
		/**亿美*/
		sendMessageByYIiMei(phone, sendSmsTemplate, sms);
		  /** 美联软通*/
		sendMessageByMeiLian(phone, sendSmsTemplate, sms);
		
		/** 您的宝贝已发货，请牢记订单编号：+code */
//		int i = SmsClient.sendSMS(phone,sendSmsTemplate);
//		if (i == 0) {
//			sms.setStatus(SmsConstant.SMS_SUCCESS_STATUS);
//		} else {
//			sms.setStatus(SmsConstant.SMS_ERROR_STATUS);
//		}
		
		smsDao.add(sms);
	}
	
	/***
	 * 促销信息   ---此方法不从模板里取短信内容了，用户手动输入
	 * @param phone
	 * @param content 短信内容
	 * @param source
	 * @throws RemoteException
	 */
	public void sendSmsPromotion(String phone,String content,String source) throws RemoteException{
		/***
		 * id为4代表促销信息
		 */
		/***
		 * 发送的短信内容添加到短信表
		 */
		Sms sms = new Sms();
		sms.setMemberName(phone);
		sms.setMobile(phone);
		sms.setSendTime(new Date());
		sms.setSourceCode(source);// 来源
		sms.setSmsTypeId(SmsConstant.SMS_TYPE_PROMOTION_ID);//促销信息
		sms.setSmsContent(content);
		sms.setSendLevel(SmsConstant.SMS_SEND_LEVEL);
		
		/**亿美*/
		sendMessageByYIiMei(phone, content, sms);
		  /** 美联软通*/
		sendMessageByMeiLian(phone, content, sms);
		
//		if (i == 0) {
//			sms.setStatus(SmsConstant.SMS_SUCCESS_STATUS);
//		} else {
//			sms.setStatus(SmsConstant.SMS_ERROR_STATUS);
//		}
		smsDao.add(sms);
	}
	
	/***
	 * 发送定时消息   ---- 此方法不从模板里取短信内容了，用户手动输入
	 * @param phone
	 * @param source
	 * @throws RemoteException
	 */
	public void sendSmsScheduled(String phone,String content, String source,String time) throws RemoteException {
	
		/***
		 * 发送的短信内容添加到短信表
		 */
		Sms sms = new Sms();
		sms.setMemberName(phone);
		sms.setMobile(phone);
		sms.setSendTime(new Date());
		sms.setSourceCode(source);// 来源
		sms.setSmsTypeId(SmsConstant.SMS_TYPE_SCHEDULED_ID);//定时消息
		sms.setSmsContent(content);
		sms.setSendLevel(SmsConstant.SMS_SEND_LEVEL);
//		/***
//		 * id为5代表定时信息
//		 */
//		int i = SmsClient.sendScheduledSMS(phone,content,time);
//		if (i == 0) {
//			sms.setStatus(SmsConstant.SMS_SUCCESS_STATUS);
//		} else {
//			sms.setStatus(SmsConstant.SMS_ERROR_STATUS);
//		}
		/**亿美*/
		sendMessageByYIiMei(phone, content, sms);
		  /** 美联软通*/
		sendMessageByMeiLian(phone, content, sms);
		smsDao.add(sms);
	}
	/***
	 * cms管理员注册后给用户发送的短信
	 */
	public void sendSmsRegisterMessage(String phone,String password,String source) throws RemoteException {
		/**
		 * id为6代表cms管理员为用户注册时给用户发送的短信
		 * 拼接替换要发送的短信模板 
		 */
		SmsTypeConfig smsType = smsTypeDao.findById(SmsConstant.SMS_TYPE_REGISTERMESSAGE_ID);
		String smsTemplate = smsType.getSmsTemplate();
		String sendSmsTemplate = smsTemplate.replace(SmsConstant.SMS_TEMPLATE_CODE,phone);//替换手机号
		sendSmsTemplate = sendSmsTemplate.replace(SmsConstant.SMS_TEMLATE_PASSWORD, password);//替换密码 
	
		/***
		 * 发送的短信内容添加到短信表
		 */
		Sms sms = new Sms();
		sms.setMemberName(phone);
		sms.setMobile(phone);
		sms.setSendTime(new Date());
		sms.setSourceCode(source);
		sms.setSmsTypeId(SmsConstant.SMS_TYPE_REGISTERMESSAGE_ID);//注册信息
		sms.setSmsContent(sendSmsTemplate);
		sms.setSendLevel(SmsConstant.SMS_SEND_LEVEL);
		
//		int i = SmsClient.sendSMS(phone, sendSmsTemplate);
//		if (i == 0) {
//			sms.setStatus(SmsConstant.SMS_SUCCESS_STATUS);
//		} else {
//			sms.setStatus(SmsConstant.SMS_ERROR_STATUS);
//		}
		
		/**亿美*/
		sendMessageByYIiMei(phone, sendSmsTemplate, sms);
		  /** 美联软通*/
		sendMessageByMeiLian(phone, sendSmsTemplate, sms);
		smsDao.add(sms);
	}
	/***
	 * 管理员自定义发送短信信息
	 */
	public void sendSmsUserDefined(String telephone, String content, String source)throws RemoteException {

		Sms sms = new Sms();
		sms.setMemberName(telephone);
		sms.setMobile(telephone);
		sms.setSendTime(new Date());
		sms.setSourceCode(source);
		sms.setSmsTypeId(SmsConstant.SMS_TYPE_USERDEFINED_ID);//自定义信息
		sms.setSmsContent(content);
		sms.setSendLevel(SmsConstant.SMS_SEND_LEVEL);
	
		/**
		 * id为7代表用户自定义的短信
		 * 
		 */
//		int i = SmsClient.sendSMS(telephone, content);
//		if (i == 0) {
//			sms.setStatus(SmsConstant.SMS_SUCCESS_STATUS);
//		} else {
//			sms.setStatus(SmsConstant.SMS_ERROR_STATUS);
//		}
		/**亿美*/
		sendMessageByYIiMei(telephone, content, sms);
		  /** 美联软通*/
		sendMessageByMeiLian(telephone, content, sms);
		smsDao.add(sms);
	}

	public long findByCreateTime(Date createTime) throws DataAccessException {
		long count = 0L;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("createTime", createTime);
		count = smsDao.count(map);
		return count;
	}

	/***
	 *购买益多宝卡，发送短信  内容，电话，来源   类型id
	 */
	public void sendSmsYiDuoBaoCode(String content, String phone, String source,Long smsTypeId) throws RemoteException {
		
		Sms sms = new Sms();
		sms.setMemberName(phone);
		sms.setMobile(phone);
		sms.setSendTime(new Date());
		sms.setSourceCode(source);
		sms.setSmsTypeId(smsTypeId);//自定义信息
		sms.setSmsContent(content);
		sms.setSendLevel(SmsConstant.SMS_SEND_LEVEL);
		
//		int i = SmsClient.sendSMS(phone, content);
//		if (i == 0) {
//			sms.setStatus(SmsConstant.SMS_SUCCESS_STATUS);
//		} else {
//			sms.setStatus(SmsConstant.SMS_ERROR_STATUS);
//		}
		/**亿美*/
		sendMessageByYIiMei(phone, content, sms);
		  /** 美联软通*/
		sendMessageByMeiLian(phone, content, sms);
		smsDao.add(sms);
	}

	public void sendSmsToConfirm(String account, String sourceCode,String smsCode) throws RemoteException {
		SmsTypeConfig smsType = smsTypeDao.findById(SmsConstant.SMS_TYPE_CONFIRM_ID);
		String smsTemplate = smsType.getSmsTemplate();
		String content=smsTemplate.replace(SmsConstant.SMS_TEMPLATE_CODE, smsCode);
		
		Sms sms = new Sms();
		sms.setMemberName(account);
		sms.setMobile(account);
		sms.setSendTime(new Date());
		sms.setSourceCode(sourceCode);
		sms.setSmsTypeId(SmsConstant.SMS_TYPE_CONFIRM_ID);//自定义信息
		sms.setSmsContent(content);
		sms.setSendLevel(SmsConstant.SMS_SEND_LEVEL);
		
//		int i = SmsClient.sendSMS(account, content);
//		if (i == 0) {
//			sms.setStatus(SmsConstant.SMS_SUCCESS_STATUS);
//		} else {
//			sms.setStatus(SmsConstant.SMS_ERROR_STATUS);
//		}
		/**亿美*/
		sendMessageByYIiMei(account, content, sms);
		  /** 美联软通*/
		sendMessageByMeiLian(account, content, sms);
		smsDao.add(sms);
		
	}

	public void sendAlabaoTradeMessage(String account, String sourceCode,String smsCode) throws RemoteException {
		SmsTypeConfig smsType = smsTypeDao.findById(SmsConstant.SMS_TYPE_ALABAO_TRADE_ID);
		String smsTemplate = smsType.getSmsTemplate();
		String content=smsTemplate.replace(SmsConstant.SMS_TEMPLATE_CODE, smsCode);
		
		Sms sms = new Sms();
		sms.setMemberName(account);
		sms.setMobile(account);
		sms.setSendTime(new Date());
		sms.setSourceCode(sourceCode);
		sms.setSmsTypeId(SmsConstant.SMS_TYPE_ALABAO_TRADE_ID);//转出验证
		sms.setSmsContent(content);
		sms.setSendLevel(SmsConstant.SMS_SEND_LEVEL);
		
		
//		int i = SmsClient.sendSMS(account, content);
//		if (i == 0) {
//			sms.setStatus(SmsConstant.SMS_SUCCESS_STATUS);
//		} else {
//			sms.setStatus(SmsConstant.SMS_ERROR_STATUS);
//		}
		/**亿美*/
		sendMessageByYIiMei(account, content, sms);
		  /** 美联软通*/
		sendMessageByMeiLian(account, content, sms);
		smsDao.add(sms);
	}

	public void tradeNotifyMessage(String account, String receiveAccount,String sourceCode, String totalActual) throws RemoteException {
		SmsTypeConfig smsType = smsTypeDao.findById(SmsConstant.SMS_TYPE_TRADE_NOTIFY_ID);
		String smsTemplate = smsType.getSmsTemplate();
		String content=smsTemplate.replace(SmsConstant.SMS_TEMPLATE_ALABAO_ACCOUNT, account).replace(SmsConstant.SMS_TEMLATE_FACEVALUE,totalActual );
	
		Sms sms = new Sms();
		sms.setMemberName(receiveAccount);
		sms.setMobile(receiveAccount);
		sms.setSendTime(new Date());
		sms.setSourceCode(sourceCode);
		sms.setSmsTypeId(SmsConstant.SMS_TYPE_TRADE_NOTIFY_ID);//转入通知
		sms.setSmsContent(content);
		sms.setSendLevel(SmsConstant.SMS_SEND_LEVEL);
		
//		int i = SmsClient.sendSMS(receiveAccount, content);
//		if (i == 0) {
//			sms.setStatus(SmsConstant.SMS_SUCCESS_STATUS);
//		} else {
//			sms.setStatus(SmsConstant.SMS_ERROR_STATUS);
//		}
		
		/**亿美*/
		sendMessageByYIiMei(receiveAccount, content, sms);
		  /** 美联软通*/
		sendMessageByMeiLian(receiveAccount, content, sms);
		smsDao.add(sms);
	}

	public void orderTradeNotify(String settleAmount, String account,String sourceCode) throws RemoteException {
		SmsTypeConfig smsType = smsTypeDao.findById(SmsConstant.SMS_TYPE_RUYIBAO_TRADE_ID);
		String smsTemplate = smsType.getSmsTemplate();
		String content=smsTemplate.replace(SmsConstant.SMS_TEMLATE_FACEVALUE,settleAmount);
		
		Sms sms = new Sms();
		sms.setMemberName(account);
		sms.setMobile(account);
		sms.setSendTime(new Date());
		sms.setSourceCode(sourceCode);
		sms.setSmsTypeId(SmsConstant.SMS_TYPE_RUYIBAO_TRADE_ID);//转入通知
		sms.setSmsContent(content);
		sms.setSendLevel(SmsConstant.SMS_SEND_LEVEL);
		
//		int i = SmsClient.sendSMS(account, content);
//		if (i == 0) {
//			sms.setStatus(SmsConstant.SMS_SUCCESS_STATUS);
//		} else {
//			sms.setStatus(SmsConstant.SMS_ERROR_STATUS);
//		}
//		
		/**亿美*/
		sendMessageByYIiMei(account, content, sms);
		  /** 美联软通*/
		sendMessageByMeiLian(account, content, sms);
		smsDao.add(sms);
	}



	public Integer findNumByLastTime(String phone) {
		if (CollectionUtils.isEmpty(smsDao.findNumByLastTime(phone))) {
			return 0;
		}
		return smsDao.findNumByLastTime(phone).size();
	}

}
