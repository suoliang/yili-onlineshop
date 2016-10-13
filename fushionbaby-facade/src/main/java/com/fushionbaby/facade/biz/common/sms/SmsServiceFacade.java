package com.fushionbaby.facade.biz.common.sms;

/***
 * 
 * @description 类描述...短信服务
 * @author 徐培峻
 * @date 2015年8月13日上午9:17:07
 */
public interface SmsServiceFacade {

	/***
	 * 发送短信  添加到数据库中
	 * @param code  验证码
	 * @param phone   接收手机
	 * @param source    来源（web，app）
	 * @param smsTypeId   短信类型（注册，忘记密码）
	 */
	public void sendSmsRegisterCode(String code, String phone,String source,Long smsTypeId);

	/***
	 * 用户购买益多宝之后 发短信通知用户
	 * @param cardNo 益多宝卡号
	 * @param phone  手机号码
	 * @param source  来源
	 * @param password  密码
	 * @param faceValue 面值
	 */
	public void sendSmsYiDuoBaoCode(String cardNo, String phone,String source,String password,String faceValue);
	
	/***
	 * 查询用户十分钟内发送的短信数量
	 * 短信发送量超过3条返回true
	 * @param phone
	 * @return
	 */
	boolean getNumByPhone(String phone);
	
}
