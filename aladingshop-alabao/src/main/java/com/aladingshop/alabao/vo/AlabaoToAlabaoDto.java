package com.aladingshop.alabao.vo;
/***
 * @description 如意宝转如意宝
 * @author 索亮
 * @date 2015年10月28日上午11:24:39
 */
public class AlabaoToAlabaoDto {
	/**用户姓名*/
	private String name;
	/**账户名称*/
	private String otherAccount;
	/**可转出的最大金额*/
	private String balance;
	

	
	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOtherAccount() {
		return otherAccount;
	}

	public void setOtherAccount(String otherAccount) {
		this.otherAccount = otherAccount;
	}

}
