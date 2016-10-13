/**
 * app.aladingshop.com 上海一里网络科技有限公司
 */
package com.fushionbaby.app.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description 红包详情DTO
 * @author 孟少博
 * @date 2015年10月22日下午2:05:10
 */
public class RedEnvelopeDetailDto {

	/** 用户ID*/
	private Long memberId;
	
	/** 红包余额*/
	private BigDecimal redBalance;
	
	/** 累计金额*/
	private BigDecimal gainAmountCount;
	
	/** 消费金额*/
	private BigDecimal consumeAmount;
	
	/** 累计获得记录*/
	private List<RedEnvelopeGainDto> gainRecord;
	
	/** 消费记录*/
	private List<RedEnvelopeConsumeDto> consumeRecord;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public BigDecimal getRedBalance() {
		return redBalance;
	}

	public void setRedBalance(BigDecimal redBalance) {
		this.redBalance = redBalance;
	}

	

	public BigDecimal getGainAmountCount() {
		return gainAmountCount;
	}

	public void setGainAmountCount(BigDecimal gainAmountCount) {
		this.gainAmountCount = gainAmountCount;
	}

	public BigDecimal getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(BigDecimal consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	
	public List<RedEnvelopeGainDto> getGainRecord() {
		return gainRecord;
	}

	public void setGainRecord(List<RedEnvelopeGainDto> gainRecord) {
		this.gainRecord = gainRecord;
	}

	public List<RedEnvelopeConsumeDto> getConsumeRecord() {
		return consumeRecord;
	}

	public void setConsumeRecord(List<RedEnvelopeConsumeDto> consumeRecord) {
		this.consumeRecord = consumeRecord;
	}
	
	
	
	
	
	
	
	
}
