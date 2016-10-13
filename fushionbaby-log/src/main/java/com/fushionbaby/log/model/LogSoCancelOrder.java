package com.fushionbaby.log.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 取消订单日志表
 * @author glc
 *
 */
public class LogSoCancelOrder implements Serializable {
	private static final long serialVersionUID = -4972729757502669853L;	
	private Long id;//取消订单日志
	private String cancelReason;//取消原因
	private Date cancelTime;//取消时间
	private String email;//邮箱
	private String memberName;//用户名
	private String paymentType;//付款方式
	private String remark;//备注
	private String reveiver;//接收者，收货人
	private String reveiverMobile;//接收者电话
	private int sendStatus;//发送状态
	private Date sendTime;//发送时间
	private String soCode;//订单编号
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReveiver() {
		return reveiver;
	}
	public void setReveiver(String reveiver) {
		this.reveiver = reveiver;
	}
	public String getReveiverMobile() {
		return reveiverMobile;
	}
	public void setReveiverMobile(String reveiverMobile) {
		this.reveiverMobile = reveiverMobile;
	}
	public int getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getSoCode() {
		return soCode;
	}
	public void setSoCode(String soCode) {
		this.soCode = soCode;
	}
}
