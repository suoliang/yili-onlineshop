package com.aladingshop.alabao.vo;
/**
 * 
 * @description 如意宝主显信息DTO
 * @author 索亮
 * @date 2015年9月10日上午11:14:00
 */
public class MainAlabaoDto {
	/** 总余额 */
	private String balance;
	/** 昨日收益 */
	private String yesterdayIncome;
	/** 总收益 */
	private String totalIncome;
	/**是否进行过身份验证  y,n*/
	private String isValidate;

	public String getIsValidate() {
		return isValidate;
	}
	public void setIsValidate(String isValidate) {
		this.isValidate = isValidate;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getYesterdayIncome() {
		return yesterdayIncome;
	}
	public void setYesterdayIncome(String yesterdayIncome) {
		this.yesterdayIncome = yesterdayIncome;
	}
	public String getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(String totalIncome) {
		this.totalIncome = totalIncome;
	}

}
