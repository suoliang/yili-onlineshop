/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年11月23日下午5:01:29
 */
package com.aladingshop.alabao.vo;

import java.math.BigDecimal;

import com.aladingshop.alabao.model.AlabaoAccount;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年11月23日下午5:01:29
 */
public class AlabaoAccountDto  {

	private AlabaoAccount alabaoAccount;
	
	private BigDecimal transferAmount;

	public AlabaoAccount getAlabaoAccount() {
		return alabaoAccount;
	}

	public void setAlabaoAccount(AlabaoAccount alabaoAccount) {
		this.alabaoAccount = alabaoAccount;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}
	
	
	
	
}
