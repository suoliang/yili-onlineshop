package com.fushionbaby.cms.dto;

import java.util.Date;

public class OrderFinanceDto {
	private Long id;

    private Long memberId;
    
    private String memberName;

    private String orderCode;

    private String financeStatus;

    private String paymentType;

    private String paymentTotalActual;
    
    private String paymentTotalActualFrom;
    
    private String paymentTotalActualTo;

    private String paymentCompleteTimeFrom;

    private String paymentCompleteTimeTo;
    
    private Date paymentCompleteTime;
    
    private Date createTime;

    private Date updateTime;

    private String isInvoice;

    private Integer invoiceType;

    private String invoiceTitle;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getFinanceStatus() {
		return financeStatus;
	}

	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentTotalActual() {
		return paymentTotalActual;
	}

	public void setPaymentTotalActual(String paymentTotalActual) {
		this.paymentTotalActual = paymentTotalActual;
	}

	public String getPaymentCompleteTimeFrom() {
		return paymentCompleteTimeFrom;
	}

	public void setPaymentCompleteTimeFrom(String paymentCompleteTimeFrom) {
		this.paymentCompleteTimeFrom = paymentCompleteTimeFrom;
	}

	public String getPaymentCompleteTimeTo() {
		return paymentCompleteTimeTo;
	}

	public void setPaymentCompleteTimeTo(String paymentCompleteTimeTo) {
		this.paymentCompleteTimeTo = paymentCompleteTimeTo;
	}

	public Date getPaymentCompleteTime() {
		return paymentCompleteTime;
	}

	public void setPaymentCompleteTime(Date paymentCompleteTime) {
		this.paymentCompleteTime = paymentCompleteTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPaymentTotalActualFrom() {
		return paymentTotalActualFrom;
	}

	public void setPaymentTotalActualFrom(String paymentTotalActualFrom) {
		this.paymentTotalActualFrom = paymentTotalActualFrom;
	}

	public String getPaymentTotalActualTo() {
		return paymentTotalActualTo;
	}

	public void setPaymentTotalActualTo(String paymentTotalActualTo) {
		this.paymentTotalActualTo = paymentTotalActualTo;
	}
    
    
}
