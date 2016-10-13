package com.fushionbaby.member.model;

import java.math.BigDecimal;
import java.util.Date;

public class MemberSign {
	private Long id;
	private String memberId;
	private Date signTime;
	private BigDecimal getEpoint;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public BigDecimal getGetEpoint() {
		return getEpoint;
	}
	public void setGetEpoint(BigDecimal getEpoint) {
		this.getEpoint = getEpoint;
	}
	
	
}
