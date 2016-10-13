package com.fushionbaby.order.model;

import java.math.BigDecimal;
import java.util.Date;
/***
 * 订单基本表信息
 * @author Leon
 *
 */
public class OrderBase {
    private Long id;

    private String orderCode;

    private Long memberId;

    private String memberName;

    private String orderStatus;

    private String isRefused;

    private Date lastReceiveTime;

    private Date cancelTime;

    private Date sysCancelTime;

    private String sysCancelReason;

    private String isDelete;

    private String sourceCode;
    
    private BigDecimal totalPointUsed;

    private BigDecimal orderPointGained;

    private String isHandlePoint;

    private String memo;

    private String auditFailReason;

    private Date createTime;

    private Date updateTime;

    private Long updateId;
    
    private String isGroupBuy;
    private Integer orderType;
    /**会员地址表信息*/
    private String receiver;
	private String receiverMobile;
	/**订单财务信息*/
	private String financeStatus;
    private String paymentType;
    private BigDecimal paymentTotalActual;
    private String isInvoice;
    /**订单物流信息*/
    private String transStatus;
    /**订单付款完成时间*/
    private Date paymentCompleteTime;
    
    /**订单费用*/
    private BigDecimal totalActual;
    /**商品总金额 */
    private BigDecimal totalAfDiscount;
	/**红包*/
    private BigDecimal redEnvelopeAmount;
	/**优惠券*/
    private BigDecimal cardAmount;
	/**积分*/
    private BigDecimal epointsRedeemMoney;
	/**运费*/
    private BigDecimal actualTransferFee;
    
    /** 订单的分配时间*/
    private Date distributionTime;
    /** 订单的分配状态 1默认未分配 2 已分配  3分配已撤回*/
    private Integer distribution;
    /** 订单的撤回时间*/
    private Date  withdrawTime;
    /** 门店系统code，系统生成，不可修改 */
    private String storeCode;
    /**是否自提的标志*/
    private String isPickUp;
    
    
    public String getIsPickUp() {
		return isPickUp;
	}

	public void setIsPickUp(String isPickUp) {
		this.isPickUp = isPickUp;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public String getIsRefused() {
        return isRefused;
    }

    public void setIsRefused(String isRefused) {
        this.isRefused = isRefused == null ? null : isRefused.trim();
    }

    public Date getLastReceiveTime() {
        return lastReceiveTime;
    }

    public void setLastReceiveTime(Date lastReceiveTime) {
        this.lastReceiveTime = lastReceiveTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getSysCancelTime() {
        return sysCancelTime;
    }

    public void setSysCancelTime(Date sysCancelTime) {
        this.sysCancelTime = sysCancelTime;
    }

    public String getSysCancelReason() {
        return sysCancelReason;
    }

    public void setSysCancelReason(String sysCancelReason) {
        this.sysCancelReason = sysCancelReason == null ? null : sysCancelReason.trim();
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode == null ? null : sourceCode.trim();
    }

    public BigDecimal getTotalPointUsed() {
		return totalPointUsed;
	}

	public void setTotalPointUsed(BigDecimal totalPointUsed) {
		this.totalPointUsed = totalPointUsed;
	}

	public BigDecimal getOrderPointGained() {
        return orderPointGained;
    }

    public void setOrderPointGained(BigDecimal orderPointGained) {
        this.orderPointGained = orderPointGained;
    }

    public String getIsHandlePoint() {
        return isHandlePoint;
    }

    public void setIsHandlePoint(String isHandlePoint) {
        this.isHandlePoint = isHandlePoint == null ? null : isHandlePoint.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getAuditFailReason() {
        return auditFailReason;
    }

    public void setAuditFailReason(String auditFailReason) {
        this.auditFailReason = auditFailReason == null ? null : auditFailReason.trim();
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

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

	public String getIsGroupBuy() {
		return isGroupBuy;
	}

	public void setIsGroupBuy(String isGroupBuy) {
		this.isGroupBuy = isGroupBuy;
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

	public BigDecimal getPaymentTotalActual() {
		return paymentTotalActual;
	}

	public void setPaymentTotalActual(BigDecimal paymentTotalActual) {
		this.paymentTotalActual = paymentTotalActual;
	}

	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice == null ? null : isInvoice.trim();
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public Date getPaymentCompleteTime() {
		return paymentCompleteTime;
	}

	public void setPaymentCompleteTime(Date paymentCompleteTime) {
		this.paymentCompleteTime = paymentCompleteTime;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
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

	public BigDecimal getRedEnvelopeAmount() {
		return redEnvelopeAmount;
	}

	public void setRedEnvelopeAmount(BigDecimal redEnvelopeAmount) {
		this.redEnvelopeAmount = redEnvelopeAmount;
	}

	public BigDecimal getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(BigDecimal cardAmount) {
		this.cardAmount = cardAmount;
	}

	public BigDecimal getEpointsRedeemMoney() {
		return epointsRedeemMoney;
	}

	public void setEpointsRedeemMoney(BigDecimal epointsRedeemMoney) {
		this.epointsRedeemMoney = epointsRedeemMoney;
	}

	public BigDecimal getActualTransferFee() {
		return actualTransferFee;
	}

	public void setActualTransferFee(BigDecimal actualTransferFee) {
		this.actualTransferFee = actualTransferFee;
	}

	public Date getDistributionTime() {
		return distributionTime;
	}

	public void setDistributionTime(Date distributionTime) {
		this.distributionTime = distributionTime;
	}

	public Integer getDistribution() {
		return distribution;
	}

	public void setDistribution(Integer distribution) {
		this.distribution = distribution;
	}

	public Date getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(Date withdrawTime) {
		this.withdrawTime = withdrawTime;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	
	
	
	
}