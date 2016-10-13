package com.fushionbaby.common.constants;

public class EmailConstant {
	/** 邮件发送模板常量 -- start */
	/** 发送验证码的邮箱 */
	public static final String SENDER_EMAIL = "yili@aldmt.com";
	/** 邮件类型1代表忘记密码操作 */
	public static final int EMAIL_TYPE_FORGET = 1;
	/** 注册时的邮件类型 3 */
	public static final int EMAIL_TYPE_REGISTER = 3;
	/** 邮件类型2代表邮件推送 */
	public static final int EMAIL_PUSH_TYPE = 2;
	/** 邮件类型8代表购买益多宝 */
	public static final int EMAIL_YIDUOBAO_TYPE = 8;
	/** 邮件类型9代表益多宝收益 */
	public static final int EMAIL_YIDUOBAO2_TYPE = 9;
	/** 邮件类型10代表益多宝消费 */
	public static final int EMAIL_YIDUOBAO3_TYPE = 10;
	/** 邮件主题 */
	public static final String EMAIL_SUBJECT = "验证码提示(www.aldingshop.com)";
	/** 邮件主题 购买益多宝 */
	public static final String EMAIL_YIDUOBAO_SUBJECT = "益多宝 提示(www.aldingshop.com)";
	/** 邮件的模板[code] 益多宝账号 */
	public static final String EMAIL_CODE = "[code]";
	/** 邮件的模板[faceValue] 益多宝面值 */
	public static final String EMAIL_YIDUOBAO_FACEVALUE = "[faceValue]";
	/** 益多宝卡号密码[password] */
	public static final String EMAIL_YIDUOBAO_PASSWORD = "[password]";
	/** 益多宝收益额 */
	//public static final String EMAIL_YIDUOBAO_INEREST = "[inerest]";
	/** 益多宝收益余额 */
	//public static final String EMAIL_YIDUOBAO_INBLANCE = "[inblance]";
	/** 益多宝赠券额 */
	public static final String EMAIL_YIDUOBAO_REBATE = "[rebate]";
	/** 益多宝赠券余额 */
	public static final String EMAIL_YIDUOBAO_REBLANCE = "[reblance]";

	/** 发送的邮件内容 */
	public static final String EMAIL_CONTENT = "<strong>尊敬的用户，您好:</strong><br />"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;感谢您关注‘阿拉丁玛特’(www.aldingshop.com)，系统自动为您发送了这封邮件。"
			+ "您本次操作的验证码为：[code]。此验证码有效期为三十分钟，请在有效期内使用验证码进行修改。";
	/** 益多宝购卡成功发送到邮箱 */
	public static final String EMAIL_YIDUOBAO_CONTENT = "<strong>尊敬的用户，您好:</strong><br />"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;感谢您在阿拉丁购买益多宝，益多宝面值为:[faceValue]元，益多宝卡号为:[code]，已存入你的账户，支付密码为[password]，请注意查收，如有疑问，请拨打阿拉丁客服电话4000-021-060咨询，祝您生活愉快。";
	/** 益多宝返还收益额、赠券额提示 发送到邮箱 */
	public static final String EMAIL_YIDUOBAO2_CONTENT = "<strong>尊敬的用户，您好:</strong><br />"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您当前在阿拉丁商城购买的益多宝卡号为:[code]已到返还收益和赠券日期，返还赠券额：[rebate]，当前总赠券额：[reblance]，请注意查收。如有疑问，请拨打阿拉丁客服电话4000-021-060咨询，感谢您的使用。";
	/** 益多宝消费提示 发送到邮箱 */
	public static final String EMAIL_YIDUOBAO3_CONTENT = "<strong>尊敬的用户，您好:</strong><br />"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您当前在阿拉丁商城使用益多宝卡号为:[code]消费成功，扣除赠券额：[rebate]，当前余下赠券额：[reblance]。如不是本人操作或有其他疑问，请拨打阿拉丁客服电话4000-021-060咨询，感谢您的使用。";
	/** 邮件发送模板常量 -- end */

}
