package com.fushionbaby.sms.service;

import java.rmi.RemoteException;
import java.util.Date;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sms.model.Sms;
/**
 * 
 * @author King
 *
 */
public interface SmsService<T extends Sms> extends BaseService<T> {
	/**
	 * @author suoliang
	 * @param page
	 *            分页
	 * @return 结果
	 * @throws DataAccessException
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;
	
	
	/**
	 * 发货提醒  ---- 此方法不从模板里取短信内容了，用户手动输入
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
	 */
	void sendSmsDelivery(String code,String expressCompany,String expressNo,String phone,String source,String name) throws RemoteException;
	/***
	 * 促销信息
	 * @param phone
	 * @param source
	 * @throws RemoteException
	 */
	void sendSmsPromotion(String phone,String content,String source) throws RemoteException;
	/***
	 * 发送定时消息  ---- 此方法不从模板里取短信内容了，用户手动输入
	 * @param phone
	 * @param source
	 * @param time
	 * @throws RemoteException
	 */
	void sendSmsScheduled(String phone,String content,String source,String time) throws RemoteException;
	/***
	 * cms管理员为用户注册时给用户发送的短信
	 * @param phone    用户手机号
	 * @param password 用户的密码
	 * @param source   来源
	 */
	void sendSmsRegisterMessage(String phone,String password,String source) throws RemoteException;
	/***
	 * 用户自定义发送短信信息
	 * @param telephone
	 * @param content
	 * @param source
	 * @throws RemoteException
	 */
	void sendSmsUserDefined(String telephone,String content,String source) throws RemoteException;
	
	public long findByCreateTime(Date createTime) throws DataAccessException;


	/***
	 * web app 注册 找回密码
	 * 
	 * @param registerRandomNum 验证码
	 * @param phone 手机号
	 * @param source 来源
	 * @param smsTypeId 短信类型（ 1注册，2忘记密码）
	 * @throws RemoteException
	 */
	void sendSmsCode(String registerRandomNum, String phone, String sourceCode,Long smsTypeRegisterId) throws RemoteException;

	/***
	 * 给益多宝 购买用户发送短信
	 * @param content   益多宝卡号和密码已经封装好
	 * @param phone     电话
	 * @param source    来源
	 * @param smsTypeId  短信类型id
	 */
	void sendSmsYiDuoBaoCode(String content, String phone, String source,Long smsTypeId) throws RemoteException;

	/***
	 * 提现申请和退卡操作时发送短信确认
	 * @param account
	 * @param sourceCode
	 * @param smsCode 
	 * @throws RemoteException 
	 */
	void sendSmsToConfirm(String account, String sourceCode, String smsCode) throws RemoteException;

	/***
	 * 如意消费卡交易，转入如意消费卡 提醒短信
	 * @param account
	 * @param sourceCode
	 * @param smsCode
	 * @throws RemoteException 
	 */
	void sendAlabaoTradeMessage(String account, String sourceCode,String smsCode) throws RemoteException;

	/***
	 * 如意消费卡被转入方的接收短信   
	 * @param account
	 * @param receiveAccount
	 * @param sourceCode
	 * @param totalActual
	 * @throws RemoteException 
	 */
	void tradeNotifyMessage(String account, String receiveAccount,String sourceCode, String totalActual) throws RemoteException;


	/***
	 * 使用如意消费卡进行订单交易满多少，短信提醒
	 * @param settleAmount
	 * @param account
	 * @param sourceCode 
	 * @throws RemoteException 
	 */
	void orderTradeNotify(String settleAmount, String account, String sourceCode) throws RemoteException;

	/***
	 * 查询用户最近10分钟短信发送量
	 * @param phone
	 * @return
	 */
	Integer findNumByLastTime(String phone);
	
	
	
}
