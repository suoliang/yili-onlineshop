package com.fushionbaby.core.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单费用表
 * 
 * @author Leon
 *
 */
public class OrderFeeUser {
	/** 订单费用id */
	private Long id;
	/** 订单编码 */
	private String orderCode;
	/** 会员id */
	private Long memberId;
	/** 代金券号码 */
	private String cardno;
	/** 代金券金额 */
	private BigDecimal cardAmount;
	/** 最终金额(扣除优惠券或者运费,后的总价) */
	private BigDecimal totalActual;
	/** 折扣后总价(当前单价总价) */
	private BigDecimal totalAfDiscount;
	/** 折扣前总价(吊牌价总价) */
	//private BigDecimal totalBfDiscount;
	/** 本次订单使用积分兑换到的优惠金额 */
	private BigDecimal epointsRedeemMoney;
	/** 税费 */
	//private BigDecimal taxPrice;
	/** 红包金额*/
	private BigDecimal redEnvelopeAmount;
	/** 如意宝支付优惠的价格,差价 */
	private BigDecimal alabaoCheapAmount;
	
	/** 使用账户余额 */
	private BigDecimal useWalletMoney;
	/** 应付运费,本次订单未优惠运费金额 */
	private BigDecimal payableTransferFee;
	/** 实付运费,满多少钱优惠后的运费 */
	private BigDecimal actualTransferFee;
	/** 创建时间 */
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
		this.cardno = cardno;
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

//	public BigDecimal getTotalBfDiscount() {
//		return totalBfDiscount;
//	}
//
//	public void setTotalBfDiscount(BigDecimal totalBfDiscount) {
//		this.totalBfDiscount = totalBfDiscount;
//	}

	public BigDecimal getEpointsRedeemMoney() {
		return epointsRedeemMoney;
	}

	public void setEpointsRedeemMoney(BigDecimal epointsRedeemMoney) {
		this.epointsRedeemMoney = epointsRedeemMoney;
	}

//	public BigDecimal getTaxPrice() {
//		return taxPrice;
//	}
//
//	public void setTaxPrice(BigDecimal taxPrice) {
//		this.taxPrice = taxPrice;
//	}

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

	public BigDecimal getRedEnvelopeAmount() {
		return redEnvelopeAmount;
	}

	public void setRedEnvelopeAmount(BigDecimal redEnvelopeAmount) {
		this.redEnvelopeAmount = redEnvelopeAmount;
	}

	public BigDecimal getAlabaoCheapAmount() {
		return alabaoCheapAmount;
	}

	public void setAlabaoCheapAmount(BigDecimal alabaoCheapAmount) {
		this.alabaoCheapAmount = alabaoCheapAmount;
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
}