package com.fushionbaby.common.dto.store;

import java.math.BigDecimal;

/**
 * @description 商户门店费用
 * @author 索亮
 * @date 2015年12月23日上午10:01:34
 */
public class StoreFeeDto {
	/** 交易金额 */
	private BigDecimal settleAmount;
	/** 支付平台费用,服务费 */
	private BigDecimal platformFee;
	/** 商户实收金额 */
	private BigDecimal realIncomeAmount;
	/** 交易流水号 */
	private String tradeSerialNumber;

	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}

	public BigDecimal getPlatformFee() {
		return platformFee;
	}

	public void setPlatformFee(BigDecimal platformFee) {
		this.platformFee = platformFee;
	}

	public BigDecimal getRealIncomeAmount() {
		return realIncomeAmount;
	}

	public void setRealIncomeAmount(BigDecimal realIncomeAmount) {
		this.realIncomeAmount = realIncomeAmount;
	}

	public String getTradeSerialNumber() {
		return tradeSerialNumber;
	}

	public void setTradeSerialNumber(String tradeSerialNumber) {
		this.tradeSerialNumber = tradeSerialNumber;
	}
	
}
