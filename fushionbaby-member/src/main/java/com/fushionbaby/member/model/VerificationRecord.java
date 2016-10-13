package com.fushionbaby.member.model;

import java.util.Date;
/***
 * 认证记录（身份证 真实姓名，银行卡 真实姓名）
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年12月7日下午3:33:54
 */
public class VerificationRecord {
	
    private Long id;
    /**真实姓名*/
    private String trueName;
    /**身份证号码*/
    private String identityCardNo;
    /**银行卡号*/
    private String bankCardNo;
    /**验证类型  1、身份证号码真实姓名 2、银行卡号真实姓名*/
    private String verificationType;
    /**验证状态  y验证成功    n验证失败*/
    private String verificationStatus;
    /**服务商返回的认证状态  (1000=一致   1001=不一致    1002=库中无此号   1003=请稍后再试    1110=核验失败，请稍候再试  1101=商家 ID 不合法    1102=身份证姓名不合法 1103=身份证号码不合法）*/
    private String responseStatus;
    /**来源  1安卓   2ios*/
    private String sourceCode;
    /**创建时间*/
    private Date createTime;
    /**更新时间*/
    private Date updateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getIdentityCardNo() {
		return identityCardNo;
	}
	public void setIdentityCardNo(String identityCardNo) {
		this.identityCardNo = identityCardNo;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}
	public String getVerificationStatus() {
		return verificationStatus;
	}
	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}
	public String getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getVerificationType() {
		return verificationType;
	}
	
    
   
	

}