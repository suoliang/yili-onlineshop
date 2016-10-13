package com.fushionbaby.log.model;

import java.io.Serializable;
import java.util.Date;
/***
 * 
 * @author xupeijun
 *
 */
public class LogSoPaymentRecord implements Serializable {

	private static final long serialVersionUID = 5850536875803629220L;
	private Long id;
	private String soCode;
	private Long memberId;
	private Integer paymentType;
	private Date createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSoCode() {
		return soCode;
	}
	public void setSoCode(String soCode) {
		this.soCode = soCode;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
