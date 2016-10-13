package com.fushionbaby.common.dto.order;

import java.io.Serializable;

/***
 * 订单结算页dto
 * 
 * @author King 索亮
 * 
 */
public class OrderExtraDto implements Serializable {
	private static final long serialVersionUID = -2931635559294112440L;
	/** 最终订单总计 */
	private String totalActual;
	/** 最终运费 */
	private String freightPrice;
	/** 最终税费 */
	private String taxPrice;
	/** 积分价格 */
	private String integralPrice;
	/** 红包使用金额*/
	private String redEnvelop;
	/** 优惠券价格 */
	@Deprecated
	private String cardnoPrice;
	/** 优惠券价格 */
	private String couponMoney;
	/** 益多宝收益额 */
	private String interestPrice;
	/** 益多宝赠券额 */
	private String rebatePrice;
	/** 礼品卡金额 */
	private String giftCardMoney;
	/** 商品折扣 */
	//private SkuDiscountDto skuDiscountDto;
	
	public String getRedEnvelop() {
		return redEnvelop;
	}

	public void setRedEnvelop(String redEnvelop) {
		this.redEnvelop = redEnvelop;
	}

	public String getTotalActual() {
		return totalActual;
	}

	public void setTotalActual(String totalActual) {
		this.totalActual = totalActual;
	}

	public String getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(String couponMoney) {
		this.couponMoney = couponMoney;
	}

	public String getFreightPrice() {
		return freightPrice;
	}

	public void setFreightPrice(String freightPrice) {
		this.freightPrice = freightPrice;
	}

	public String getTaxPrice() {
		return taxPrice;
	}

	public void setTaxPrice(String taxPrice) {
		this.taxPrice = taxPrice;
	}

	public String getIntegralPrice() {
		return integralPrice;
	}

	public void setIntegralPrice(String integralPrice) {
		this.integralPrice = integralPrice;
	}

	public String getCardnoPrice() {
		return cardnoPrice;
	}

	public void setCardnoPrice(String cardnoPrice) {
		this.cardnoPrice = cardnoPrice;
	}

//	public SkuDiscountDto getSkuDiscountDto() {
//		return skuDiscountDto;
//	}
//
//	public void setSkuDiscountDto(SkuDiscountDto skuDiscountDto) {
//		this.skuDiscountDto = skuDiscountDto;
//	}

	public String getGiftCardMoney() {
		return giftCardMoney;
	}

	public void setGiftCardMoney(String giftCardMoney) {
		this.giftCardMoney = giftCardMoney;
	}

	public String getInterestPrice() {
		return interestPrice;
	}

	public void setInterestPrice(String interestPrice) {
		this.interestPrice = interestPrice;
	}

	public String getRebatePrice() {
		return rebatePrice;
	}

	public void setRebatePrice(String rebatePrice) {
		this.rebatePrice = rebatePrice;
	}

}
