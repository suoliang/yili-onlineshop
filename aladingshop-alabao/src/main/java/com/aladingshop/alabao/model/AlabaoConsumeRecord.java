package com.aladingshop.alabao.model;

import java.math.BigDecimal;
import java.util.Date;

public class AlabaoConsumeRecord {
	private Long id;
	/** 阿拉宝账户*/
	private String account;
	/** 会员id*/
	private Long memberId;
	/** 订单号，消费在哪个订单上面*/
	private String orderCode;
	/** 消费金额*/
	private BigDecimal consumeMoney;
	/** 消费是否成功,y表示消费成功，n表示消费失败*/
	private String isSuccess;
	/** 创建时间*/
	private Date createTime;
	
	/**本次操作前的金额*/
	private BigDecimal beforeChangeMoney;
	/**本次操作后的金额*/
	private BigDecimal afterChangeMoney;
	
	
	
	
	public BigDecimal getBeforeChangeMoney() {
		return beforeChangeMoney;
	}
	public void setBeforeChangeMoney(BigDecimal beforeChangeMoney) {
		this.beforeChangeMoney = beforeChangeMoney;
	}
	public BigDecimal getAfterChangeMoney() {
		return afterChangeMoney;
	}
	public void setAfterChangeMoney(BigDecimal afterChangeMoney) {
		this.afterChangeMoney = afterChangeMoney;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public BigDecimal getConsumeMoney() {
		return consumeMoney;
	}
	public void setConsumeMoney(BigDecimal consumeMoney) {
		this.consumeMoney = consumeMoney;
	}
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
