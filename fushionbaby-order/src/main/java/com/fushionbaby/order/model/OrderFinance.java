package com.fushionbaby.order.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderFinance {
    private Long id;

    private Long memberId;

    private String orderCode;

    private String financeStatus;

    private String paymentType;

    private BigDecimal paymentTotalActual;

    private Date paymentCompleteTime;

    private Date createTime;

    private Date updateTime;

    private String isInvoice;

    private Integer invoiceType;

    private String invoiceTitle;
    /** 某一天付款成功的订单数量 */
    private Integer orderTotalNumber;
    /** 某一天付款成功的订单总金额 */
    private BigDecimal salesTotalMoney;
    
    /** 门店系统code，系统生成，不可修改 */
    private String storeCode;
    
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
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getFinanceStatus() {
        return financeStatus;
    }

    public void setFinanceStatus(String financeStatus) {
        this.financeStatus = financeStatus == null ? null : financeStatus.trim();
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType == null ? null : paymentType.trim();
    }

    public BigDecimal getPaymentTotalActual() {
        return paymentTotalActual;
    }

    public void setPaymentTotalActual(BigDecimal paymentTotalActual) {
        this.paymentTotalActual = paymentTotalActual;
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
        this.isInvoice = isInvoice == null ? null : isInvoice.trim();
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
        this.invoiceTitle = invoiceTitle == null ? null : invoiceTitle.trim();
    }

	public Integer getOrderTotalNumber() {
		return orderTotalNumber;
	}

	public void setOrderTotalNumber(Integer orderTotalNumber) {
		this.orderTotalNumber = orderTotalNumber;
	}

	public BigDecimal getSalesTotalMoney() {
		return salesTotalMoney;
	}

	public void setSalesTotalMoney(BigDecimal salesTotalMoney) {
		this.salesTotalMoney = salesTotalMoney;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
    
}