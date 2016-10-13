/**alading.com 上海一里网络科技有限公司
 */
package com.fushionbaby.app.dto;

import java.math.BigDecimal;

/**
 * @description 红包获取信息
 * @author 孟少博
 * @date 2015年10月22日下午2:22:35
 */
public class RedEnvelopeGainDto {
	
	/**注册的用户*/
	private Long registerMemberId;
	
	/** 内容*/
	private String content; 
	
	/** 获取金额*/
	private BigDecimal gainAmount;
	
	/** 创建时间*/
	private String createTime;

	public Long getRegisterMemberId() {
		return registerMemberId;
	}

	public void setRegisterMemberId(Long registerMemberId) {
		this.registerMemberId = registerMemberId;
	}

	public BigDecimal getGainAmount() {
		return gainAmount;
	}

	public void setGainAmount(BigDecimal gainAmount) {
		this.gainAmount = gainAmount;
	} 

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
