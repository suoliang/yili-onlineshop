package com.fushionbaby.common.dto.verification;

import com.fushionbaby.common.constants.VerificationConstant;
import com.fushionbaby.common.security.MD5Util;


/***
 * 身份校验的请求参数封装（身份证和姓名）
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月23日上午11:14:34
 */
public class IDVerificationReqDto {
	/**商户的id*/
	private String mallId;
	/**需要认证的实名用户名*/
	private String realName;
	/** 身份证卡号*/
	private String idCard;
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
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
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
	/***
	 * 
	 * @param mallId  商户id
	 * @param realName   真实姓名
	 * @param idCard     身份证账号
	 * @param key       加密的key
	 */ 
	public IDVerificationReqDto( String realName, String idCard) {
		super();
		this.mallId =VerificationConstant.MALL_ID;
		this.realName = realName;
		this.idCard = idCard.toLowerCase();
		this.tm=System.currentTimeMillis();
		this.sign=MD5Util.md5Verification(mallId+realName+idCard+tm+VerificationConstant.API_KEY);
	}
	
	public IDVerificationReqDto() {
	}
	
	
}
