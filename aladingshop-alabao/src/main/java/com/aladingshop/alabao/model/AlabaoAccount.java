package com.aladingshop.alabao.model;

import java.math.BigDecimal;
import java.util.Date;

public class AlabaoAccount {
	private Long id;
	/** 余额宝账号,转入转出，消费收益*/
	private String account;
	/** 支付密码，进行转账或则消费密码*/
	private String payPassword;
	/** 登陆密码*/
	private String loginPassword;
	/** 会员id*/
	private Long memberId;
	/** 手机号码*/
	private String mobilePhone;
	/** 用户的真实姓名 */
	private String trueName;
	/** 身份证号码*/   
	private String identityCardNo;
	/**来源*/
	private String sourceCode;
	/** 锁定余额，订单支付未完成要释放*/
	private BigDecimal lockedBalance;
	/** 阿拉宝余额*/
	private BigDecimal balance;
	/** 昨日收益*/
	private BigDecimal yesterdayIncome;
	/** 总的收益*/
	private BigDecimal totalIncome;
	/** 用户状态 1待审核，2审核通过，3审核不通过 4.注销*/
	private String status;
	/** 创建时间*/
	private Date createTime;
	/** 更新时间*/
	private Date updateTime;
	/** 会员名  */
	private String memberName;
	/** 会员等级*/
	private String level;
	/**是否验证过  1 未验证 2 已验证 未通过 3 已验证 已通过*/
	private String isValidate;
	/**最新使用的用户和银行卡的关联id*/
    private Long lastedBankId;
	
	public String getIsValidate() {
		return isValidate;
	}
	public void setIsValidate(String isValidate) {
		this.isValidate = isValidate;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
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
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public BigDecimal getLockedBalance() {
		return lockedBalance;
	}
	public void setLockedBalance(BigDecimal lockedBalance) {
		this.lockedBalance = lockedBalance;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getYesterdayIncome() {
		return yesterdayIncome;
	}
	public void setYesterdayIncome(BigDecimal yesterdayIncome) {
		this.yesterdayIncome = yesterdayIncome;
	}
	public BigDecimal getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Long getLastedBankId() {
		return lastedBankId;
	}
	public void setLastedBankId(Long lastedBankId) {
		this.lastedBankId = lastedBankId;
	}
	 
}
