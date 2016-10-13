package com.aladingshop.alabao.dto;

import java.math.BigDecimal;
import java.util.Date;

public class AlabaoConsumeRecordDto {
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
	/** 创建开始时间*/
	private Date createTime;
	/**创建结束时间*/
	private String memberName;
	
	
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
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	
}
