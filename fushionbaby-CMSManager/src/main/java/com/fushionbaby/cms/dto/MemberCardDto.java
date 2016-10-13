package com.fushionbaby.cms.dto;

import java.math.BigDecimal;
import java.util.Date;

/***
 * 会员储蓄卡
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年9月8日上午11:03:50
 */
public class MemberCardDto extends YiDuoBaoOrderDto{
	/** 储蓄卡的卡号*/
	private String cardNo;
	/** 储蓄卡的卡号*/
	private String code;
	/** 该储蓄卡的面值*/
	private BigDecimal faceValue;
	/** 该储蓄卡产生的总赠券，可使用*/
	private BigDecimal totalRebate;
	/** 该卡类型    年卡，双季卡 ，季卡*/
	private String cardType;
	/** 会员购买时间*/
	private Date createTime;
	/** 使用类型*/
	private String type;
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public BigDecimal getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}
	public BigDecimal getTotalRebate() {
		return totalRebate;
	}
	public void setTotalRebate(BigDecimal totalRebate) {
		this.totalRebate = totalRebate;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
