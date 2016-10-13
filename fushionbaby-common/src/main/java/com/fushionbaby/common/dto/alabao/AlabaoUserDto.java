package com.fushionbaby.common.dto.alabao;
/**
 * @description 如意宝用户DTO
 * @author 索亮
 * @date 2015年9月10日上午9:17:48
 */
public class AlabaoUserDto {
	/** 账户id */
	private Long alabaoId;
	/**如意宝用户标识*/
	private String alabaoSid;
	/** 账户名称 */
	private String account;
	/** 会员用户id */
	private Long memberId;
	/**会员名称-真实姓名*/
	private String memberName;
	/**如意消费卡用户身份证号*/
	private String ID;
	/**验证状态 y代表已验证，n代表未验证或验证失败*/
	private String isValidate;

	public String getIsValidate() {
		return isValidate;
	}
	public void setIsValidate(String isValidate) {
		this.isValidate = isValidate;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public Long getAlabaoId() {
		return alabaoId;
	}
	public void setAlabaoId(Long alabaoId) {
		this.alabaoId = alabaoId;
	}
	public String getAlabaoSid() {
		return alabaoSid;
	}
	public void setAlabaoSid(String alabaoSid) {
		this.alabaoSid = alabaoSid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

}
