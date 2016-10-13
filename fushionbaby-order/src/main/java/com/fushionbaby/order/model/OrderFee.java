package com.fushionbaby.order.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderFee {
    private Long id;

    private Long memberId;

    private String orderCode;

    private String cardno;

    private BigDecimal cardAmount;

    private BigDecimal totalActual;

    /** 折扣后的商品总价*/
    private BigDecimal totalAfDiscount;
    	/** 折扣前的商品总价*/
    private BigDecimal totalBfDiscount;

    private BigDecimal epointsRedeemMoney;

    private BigDecimal taxPrice;
    /** 红包金额*/
    private BigDecimal redEnvelopeAmountTotal;
    /** 如意宝支付优惠的价格,差价 */
    private BigDecimal alabaoCheapAmount;

    private BigDecimal useWalletMoney;

    private BigDecimal payableTransferFee;

    private BigDecimal actualTransferFee;

    private Date createTime;
    
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

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno == null ? null : cardno.trim();
    }

    public BigDecimal getCardAmount() {
        return cardAmount;
    }

    public void setCardAmount(BigDecimal cardAmount) {
        this.cardAmount = cardAmount;
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

    public BigDecimal getEpointsRedeemMoney() {
        return epointsRedeemMoney;
    }

    public void setEpointsRedeemMoney(BigDecimal epointsRedeemMoney) {
        this.epointsRedeemMoney = epointsRedeemMoney;
    }

    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    public BigDecimal getUseWalletMoney() {
        return useWalletMoney;
    }

    public void setUseWalletMoney(BigDecimal useWalletMoney) {
        this.useWalletMoney = useWalletMoney;
    }

    public BigDecimal getPayableTransferFee() {
        return payableTransferFee;
    }

    public void setPayableTransferFee(BigDecimal payableTransferFee) {
        this.payableTransferFee = payableTransferFee;
    }

    public BigDecimal getActualTransferFee() {
        return actualTransferFee;
    }

    public void setActualTransferFee(BigDecimal actualTransferFee) {
        this.actualTransferFee = actualTransferFee;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public BigDecimal getRedEnvelopeAmountTotal() {
		return redEnvelopeAmountTotal;
	}

	public void setRedEnvelopeAmountTotal(BigDecimal redEnvelopeAmountTotal) {
		this.redEnvelopeAmountTotal = redEnvelopeAmountTotal;
	}

	public BigDecimal getAlabaoCheapAmount() {
		return alabaoCheapAmount;
	}

	public void setAlabaoCheapAmount(BigDecimal alabaoCheapAmount) {
		this.alabaoCheapAmount = alabaoCheapAmount;
	}
    
    
}