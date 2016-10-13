package com.fushionbaby.core.model;

//import java.math.BigDecimal;
import java.util.Date;
/**
 * 订单财务表
 * @author Leon
 *
 */
public class OrderFinanceUser {
	/** 订单财务id */
    private Long id;
    /** 会员id */
    private Long memberId;
    /** 订单编号 */
    private String orderCode;
    /** 财务状态,付款状态n未付款y已付款 */
    private String financeStatus;
    /** 付款方式 */
    private String paymentType;
    /** 最终金额(扣除优惠券或者运费,后的总价) */
    //private BigDecimal paymentTotalActual;
    /** 付款时间 */
    private Date paymentCompleteTime;
    /** 创建时间 */
    private Date createTime;
    /** 最后一次更新时间 */
    private Date updateTime;
    /** 是y否n需要发票 */
    private String isInvoice;
    /** 发票类型(1个人2公司) */
    private Integer invoiceType;
    /** 发票抬头 */
    private String invoiceTitle;
    
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

    

//    public BigDecimal getPaymentTotalActual() {
//		return paymentTotalActual;
//	}
//
//	public void setPaymentTotalActual(BigDecimal paymentTotalActual) {
//		this.paymentTotalActual = paymentTotalActual;
//	}

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

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
    
}