package com.fushionbaby.common.dto;

import java.util.List;

import com.fushionbaby.common.dto.order.GotoOrderLineDto;

/***
 * 用于向前台返回，通过卡号得到的卡的信息
 * 
 * ，卡的类型，以及卡的优惠金额，还有卡所起作用的范围（Codelist）
 * 
 * @author xupeijun
 * 
 */
public class CouponCardDto {
	
	/** 0代表不可用 1代表通用，2代表品牌列表，3代表分类列表，4代表某些商品 5代表商品标签 */
	private String cardType;
	
	/** 编号集合(品牌，分类，商品等)*/
	private String codeList;
	
	/** 优惠金额*/
	private String discountMoney;
	
	/** 满多少之后才可以使用优惠*/
	private String conditionSkuPriceAbove;
	
	/**订单行,得到订单行小计和订单行的商品id，用在判断满赠条件*/
	private List<GotoOrderLineDto> orderLineItems;
	
	/** app 或则 web  标示符*/
	private String flag;
	
	/** 用户提示信息*/
	private String hintMessage;

	public String getHintMessage() {
		return hintMessage;
	}

	public void setHintMessage(String hintMessage) {
		this.hintMessage = hintMessage;
	}
    
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(String discountMoney) {
		this.discountMoney = discountMoney;
	}

	public String getCodeList() {
		return codeList;
	}

	public void setCodeList(String codeList) {
		this.codeList = codeList;
	}

	public String getConditionSkuPriceAbove() {
		return conditionSkuPriceAbove;
	}

	public void setConditionSkuPriceAbove(String conditionSkuPriceAbove) {
		this.conditionSkuPriceAbove = conditionSkuPriceAbove;
	}

	public List<GotoOrderLineDto> getOrderLineItems() {
		return orderLineItems;
	}

	public void setOrderLineItems(List<GotoOrderLineDto> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
