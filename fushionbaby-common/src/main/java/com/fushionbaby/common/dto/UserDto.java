package com.fushionbaby.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserDto implements Serializable {
	private static final long serialVersionUID = 1L;

	public UserDto() {
	}

	public UserDto(Integer status, String errorMsg) {
		this.loginStatus = status;
		this.errorMsg = errorMsg;
	}

	/** 用户唯一ID */
	private Long memberId;
	/** 类似session ID(判断登录状态) */
	private String sid;
	/** 用户名(昵称) */
	private String loginName;
	/** 用户头像 */
	private String imgPath;
	/** 积分 */
	private BigDecimal epoints;
	/** 钱包总金额 */
	private BigDecimal walletMoney;
	/** 可用金额 */
	private BigDecimal availableMoney;
	/** 会员等级名称 */
	private String gradeName;
	/** 登录状态 */
	private Integer loginStatus;
	/** 登录失败信息 */
	private String errorMsg;
   /**是否绑定如意消费卡  y 代表绑定过如意消费卡，n 代表未开通如意消费卡*/
	private String isBind;
	
	/**如意消费卡 sid*/
	private String alabaoSid;
	
	/**如意消费卡账号*/
	private String account;
	
	/**用户真实姓名*/
	private String trueName;
	
	/** 是否验证过*/
	private  String isValidate;
	
	
	public String getIsValidate() {
		return isValidate;
	}

	public void setIsValidate(String isValidate) {
		this.isValidate = isValidate;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAlabaoSid() {
		return alabaoSid;
	}

	public void setAlabaoSid(String alabaoSid) {
		this.alabaoSid = alabaoSid;
	}

	public String getIsBind() {
		return isBind;
	}
	
	public void setIsBind(String isBind) {
		this.isBind = isBind;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public BigDecimal getEpoints() {
		return epoints;
	}

	public void setEpoints(BigDecimal epoints) {
		this.epoints = epoints;
	}

	public BigDecimal getWalletMoney() {
		return walletMoney;
	}

	public void setWalletMoney(BigDecimal walletMoney) {
		this.walletMoney = walletMoney;
	}

	public BigDecimal getAvailableMoney() {
		return availableMoney;
	}

	public void setAvailableMoney(BigDecimal availableMoney) {
		this.availableMoney = availableMoney;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Integer getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(Integer loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

}
