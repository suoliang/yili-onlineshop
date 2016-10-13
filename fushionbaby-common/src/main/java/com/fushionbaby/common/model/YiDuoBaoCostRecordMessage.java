/**
 * 
 */
package com.fushionbaby.common.model;

import java.math.BigDecimal;

/**
 * @description 益多宝消费记录短信model
 * @author 孙涛
 * @date 2015年9月17日上午10:56:23
 */
public class YiDuoBaoCostRecordMessage {
	public YiDuoBaoCostRecordMessage(Long mid, String cacode, BigDecimal in, BigDecimal re) {
		this.memberId = mid;
		this.cardCode = cacode;
		this.costInerest = in == null ? new BigDecimal(0) : in;
		this.costRebate = re == null ? new BigDecimal(0) : re;
	}

	/** 消费会员ID */
	private Long memberId;
	/** 消费卡号 */
	private String cardCode;
	/** 消费收益额 */
	private BigDecimal costInerest;
	/** 消费赠券额 */
	private BigDecimal costRebate;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public BigDecimal getCostInerest() {
		return costInerest;
	}

	public void setCostInerest(BigDecimal costInerest) {
		this.costInerest = costInerest;
	}

	public BigDecimal getCostRebate() {
		return costRebate;
	}

	public void setCostRebate(BigDecimal costRebate) {
		this.costRebate = costRebate;
	}
}
