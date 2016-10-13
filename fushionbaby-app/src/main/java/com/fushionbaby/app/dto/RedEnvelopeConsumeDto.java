/**
 *alading.com 上海一里网络科技有限公司
 */
package com.fushionbaby.app.dto;

import java.math.BigDecimal;

/**
 * @description 红包消费
 * @author 孟少博
 * @date 2015年10月22日下午2:27:27
 */
public class RedEnvelopeConsumeDto {

	/** 订单编号*/
	private String orderCode;
	
	/** 消费金额*/
	private BigDecimal consumeAmount;
	
	/** 使用状态*/
	private String useStatus;
	
	/** 内容*/
	private String content;
	
	/** 创建时间*/
	private String createTime;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public BigDecimal getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(BigDecimal consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	public String getContent() {
		return content;
	}
	

	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
	
	
}
