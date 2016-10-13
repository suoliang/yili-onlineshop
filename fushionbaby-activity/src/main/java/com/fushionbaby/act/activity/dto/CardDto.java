package com.fushionbaby.act.activity.dto;

/***
 * 优惠券的查询条件
 * 
 * @author xupeijun
 * 
 */
public class CardDto {

	/**优惠券卡号*/
	private String cardNo;
    /**优惠券类型*/
	private String useType;
    /**开始时间*/
	private String useTimeFrom;
    /**截止时间*/
	private String useTimeTo;
	/**优惠券密码*/
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public String getUseTimeFrom() {
		return useTimeFrom;
	}

	public void setUseTimeFrom(String useTimeFrom) {
		this.useTimeFrom = useTimeFrom;
	}

	public String getUseTimeTo() {
		return useTimeTo;
	}

	public void setUseTimeTo(String useTimeTo) {
		this.useTimeTo = useTimeTo;
	}

}
