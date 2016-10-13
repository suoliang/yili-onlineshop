package com.fushionbaby.common.dto.verification;

import com.fushionbaby.common.constants.VerificationConstant;
import com.fushionbaby.common.security.MD5Util;


/***
 * 身份校验的请求参数封装（银行卡和姓名）
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月23日上午11:14:34
 */
public class BankCardVerificationReqDto {
	/**商户的id*/
	private String mallId;
	/**需要认证的实名用户名*/
	private String realName;
	/** 银行卡卡号*/
	private String cardNum;
	/** 交易时间戳*/
	private long tm;
	/**MD5 加密之后的签名*/
	private String sign;
	
	public String getMallId() {
		return mallId;
	}
	public void setMallId(String mallId) {
		this.mallId = mallId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public long getTm() {
		return tm;
	}
	public void setTm(long tm) {
		this.tm = tm;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	/***
	 * 
	 * @param mallId  商户id
	 * @param realName   真实姓名
	 * @param cardNum   银行卡号     
	 * @param key       加密的key
	 */ 
	public BankCardVerificationReqDto( String realName, String cardNum) {
		super();
		this.mallId = VerificationConstant.MALL_ID;
		this.realName = realName;
		this.cardNum = cardNum;
		this.tm=System.currentTimeMillis();
		this.sign=MD5Util.md5Verification(mallId+realName+cardNum+tm+VerificationConstant.API_KEY);
	}
	
	public BankCardVerificationReqDto() {
	}
	
	
}
