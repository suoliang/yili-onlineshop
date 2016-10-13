/**
 * 
 */
package com.fushionbaby.cms.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fushionbaby.order.model.OrderLine;

/**
 * @description 类描述...
 * @author 徐鹏飞
 * @date 2015年8月5日下午2:06:03
 */
public class OrderDto {
	
	private OrderLine soSoLine;
	
	private Long id;

	private BigDecimal actualTransferFee;

	private Date cancelTime;

	private String orderCode;

	private Date createTime;

	private String financeStatus;

	private Integer invoiceType;

	private String isInvoice;

	private String invoiceTitle;

	private String isRefused;

	private Date lastReceiveTime;

	private String memo;

	private String sourceCode;

	private BigDecimal orderPointGained;

	private BigDecimal payableTransferFee;

	private Date paymentCompleteTime;

	private String paymentType;

	private String transStatus;

	private String sysCancelReason;

	private BigDecimal totalActual;

	private BigDecimal totalAfDiscount;

	private BigDecimal totalBfDiscount;

	private BigDecimal totalPointUsed;

	private String transCode;

	private String transName;

	private Date version;

	private String orderStatus;
	
	private BigDecimal useWalletMoney;

	private Long memberId;

	private String isDelete;

	private String memberName;

	private BigDecimal skuDiscountTotal;

	private String sendDate;

	private BigDecimal epointsRedeemMoney;

	private String cardno;

	private BigDecimal cardAmount;

	private BigDecimal taxPrice;
	
	private String isHandlePoint;

	
	private Date sysCancelTime;
	
	private String auditFailReason;

	
	private String receiver;
	private String receiverMobile;
	private String address;
	
	private String skuName;
	
	private String province;
	private String city;
	private String district;
	private BigDecimal paymentTotalActual;
	
	
	public BigDecimal getPaymentTotalActual() {
		return paymentTotalActual;
	}

	public void setPaymentTotalActual(BigDecimal paymentTotalActual) {
		this.paymentTotalActual = paymentTotalActual;
	}

	public OrderLine getSoSoLine() {
		return soSoLine;
	}

	public void setSoSoLine(OrderLine soSoLine) {
		this.soSoLine = soSoLine;
	}

	public String getAuditFailReason() {
		return auditFailReason;
	}

	public void setAuditFailReason(String auditFailReason) {
		this.auditFailReason = auditFailReason;
	}

	public Date getSysCancelTime() {
		return sysCancelTime;
	}

	public void setSysCancelTime(Date sysCancelTime) {
		this.sysCancelTime = sysCancelTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getActualTransferFee() {
		return actualTransferFee;
	}

	public void setActualTransferFee(BigDecimal actualTransferFee) {
		this.actualTransferFee = actualTransferFee;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFinanceStatus() {
		return financeStatus;
	}

	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
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

	public String getIsRefused() {
		return isRefused;
	}

	public void setIsRefused(String isRefused) {
		this.isRefused = isRefused;
	}

	public Date getLastReceiveTime() {
		return lastReceiveTime;
	}

	public void setLastReceiveTime(Date lastReceiveTime) {
		this.lastReceiveTime = lastReceiveTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public BigDecimal getOrderPointGained() {
		return orderPointGained;
	}

	public void setOrderPointGained(BigDecimal orderPointGained) {
		this.orderPointGained = orderPointGained;
	}

	public BigDecimal getPayableTransferFee() {
		return payableTransferFee;
	}

	public void setPayableTransferFee(BigDecimal payableTransferFee) {
		this.payableTransferFee = payableTransferFee;
	}

	public Date getPaymentCompleteTime() {
		return paymentCompleteTime;
	}

	public void setPaymentCompleteTime(Date paymentCompleteTime) {
		this.paymentCompleteTime = paymentCompleteTime;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType == null ? null : paymentType.trim();
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getSysCancelReason() {
		return sysCancelReason;
	}

	public void setSysCancelReason(String sysCancelReason) {
		this.sysCancelReason = sysCancelReason == null ? null : sysCancelReason.trim();
	}

	public BigDecimal getTotalActual() {
		return totalActual;
	}

	public void setTotalActual(BigDecimal totalActual) {
		this.totalActual = totalActual;
	}

	public BigDecimal getTotalAfDiscount() {
		return totalAfDiscount;
	}

	public void setTotalAfDiscount(BigDecimal totalAfDiscount) {
		this.totalAfDiscount = totalAfDiscount;
	}

	public BigDecimal getTotalBfDiscount() {
		return totalBfDiscount;
	}

	public void setTotalBfDiscount(BigDecimal totalBfDiscount) {
		this.totalBfDiscount = totalBfDiscount;
	}

	public BigDecimal getTotalPointUsed() {
		return totalPointUsed;
	}

	public void setTotalPointUsed(BigDecimal totalPointUsed) {
		this.totalPointUsed = totalPointUsed;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode == null ? null : transCode.trim();
	}

	public String getTransName() {
		return transName;
	}

	public void setTransName(String transName) {
		this.transName = transName == null ? null : transName.trim();
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete == null ? null : isDelete.trim();
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public BigDecimal getEpointsRedeemMoney() {
		return epointsRedeemMoney;
	}

	public void setEpointsRedeemMoney(BigDecimal epointsRedeemMoney) {
		this.epointsRedeemMoney = epointsRedeemMoney;
	}

	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public BigDecimal getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(BigDecimal cardAmount) {
		this.cardAmount = cardAmount;
	}

	public BigDecimal getTaxPrice() {
		return taxPrice;
	}

	public void setTaxPrice(BigDecimal taxPrice) {
		this.taxPrice = taxPrice;
	}

	public String getIsHandlePoint() {
		return isHandlePoint;
	}

	public void setIsHandlePoint(String isHandlePoint) {
		this.isHandlePoint = isHandlePoint;
	}
	
	public BigDecimal getSkuDiscountTotal() {
		return skuDiscountTotal;
	}

	
	public void setSkuDiscountTotal(BigDecimal skuDiscountTotal) {
		this.skuDiscountTotal = skuDiscountTotal;
	}
	
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	public BigDecimal getUseWalletMoney() {
		return useWalletMoney;
	}

	public void setUseWalletMoney(BigDecimal useWalletMoney) {
		this.useWalletMoney = useWalletMoney;
	}


	
}
