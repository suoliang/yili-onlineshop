package com.fushionbaby.common.constants;

/**
 * 短信发送模板常量
 * 
 * @author King 索亮
 *
 */
public class SmsConstant {
	/** 短信发送状态 -- start */

	/** 短信状态 --0发送成功 */
	public static final int SMS_SUCCESS_STATUS = 0;
	/** 短信状态 --1发送失败 */
	public static final int SMS_ERROR_STATUS = 1;
	/** 短信发送级别，5为默认，表示即时发送 */
	public static final int SMS_SEND_LEVEL = 5;
	/**短信长度  4位 */
	public static final int SMS_CODE_LENGTH = 4;

	/** 短信发送状态 -- end */

	/** 短信发送模板常量 -- 开始 */

	/** 1用户注册 */
	public static final Long SMS_TYPE_REGISTER_ID = 1L;
	/** 2忘记密码 */
	public static final Long SMS_TYPE_FORGET_ID = 2L;
	/** 3发货提醒 */
	public static final Long SMS_TYPE_DELIVERY_ID = 3L;
	/** 4促销信息 */
	public static final Long SMS_TYPE_PROMOTION_ID = 4L;
	/** 5定时短信 */
	public static final Long SMS_TYPE_SCHEDULED_ID = 5L;
	/** 6注册信息 */
	public static final Long SMS_TYPE_REGISTERMESSAGE_ID = 6L;
	/** 7自定义短信 */
	public static final Long SMS_TYPE_USERDEFINED_ID = 7L;
	/** 8 益多宝购卡发送短信 */
	public static final Long SMS_TYPE_YIDUOBAO_ID = 8L;
	/** 9 益多宝收益发送短信 */
	public static final Long SMS_TYPE_YIDUOBAO2_ID = 9L;
	/** 10 益多宝消费发送短信 */
	public static final Long SMS_TYPE_YIDUOBAO3_ID = 10L;
	/** 11 阿拉丁卡到期转入如意宝通知*/
	public static final Long SMS_TYPE_YIDUOBAO4_ID = 11L;
	/**双十一短信模板 12*/	
	public static final Long SMS_TYPE_DOUBLE_11_ID = 12L;
	/** 提现申请和退卡时的确认短信  13*/
	public static final Long SMS_TYPE_CONFIRM_ID = 13L;
	/** 转出到银行卡获取验证码14*/
	public static final Long SMS_TYPE_TURNBANK_ID = 14L;
	/** 如意消费卡 转出到如意消费卡  交易提醒短信  15*/
	public static final Long SMS_TYPE_ALABAO_TRADE_ID = 15L;
	/** 如意消费卡 转出到如意消费卡   接收方的收入提醒短信   16*/
	public static final Long SMS_TYPE_TRADE_NOTIFY_ID = 16L;
	/** 如意消费卡 转出到如意消费卡   接收方的收入提醒短信   17*/
	public static final Long SMS_TYPE_RUYIBAO_TRADE_ID = 17L;
	/** 短信发送模板里的[code] 益多宝卡号 */
	public static final String SMS_TEMPLATE_CODE = "[code]";
	/** 注册信息模板里的[password] 益多宝密码 */
	public static final String SMS_TEMLATE_PASSWORD = "[password]";
	/** 注册信息模板里的[faceValue] 益多宝面值 */
	public static final String SMS_TEMLATE_FACEVALUE = "[faceValue]";
	/** 发货提醒里的[expressCompany]快递公司 */
	public static final String SMS_EXPRESS_COMPANY = "[expressCompany]";
	/** 发货提醒里的[expressNo]快递单号 */
	public static final String SMS_EXPRESS_NO = "[expressNo]";
	/** 短信发送模板里的[inerest] 益多宝收益额 */
	//public static final String SMS_TEMPLATE_INEREST = "[inerest]";
	/** 短信发送模板里的[rebate] 益多宝赠券额 */
	public static final String SMS_TEMPLATE_REBATE = "[rebate]";
	/** 益多宝收益余额 */
	//public static final String SMS_TEMPLATE_INBLANCE = "[inblance]";
	/** 益多宝赠券余额 */
	public static final String SMS_TEMPLATE_REBLANCE = "[reblance]";
	/**如意宝账号*/
	public static final String SMS_TEMPLATE_ALABAO_ACCOUNT = "[account]";
    /** 时间模板*/
	public static final String SMS_TEMPLATE_TIME = "[time]";
	/** 账户余额*/
	public static final String SMS_TEMPLATE_ACCOUNT_BALANCE = "[balance]";
	
	
	/** 短信发送模板常量 -- 结束 */

}
