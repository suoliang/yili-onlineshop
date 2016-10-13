package com.fushionbaby.act.activity.dto;
/***
 * 
 * @description 类描述...cms列表查询   优惠券类型dto
 * @author 徐培峻
 * @date 2015年7月30日上午10:48:36
 */
public class CardTypeDto {
    /** 优惠券类型的名称*/
	private String cardTypeName;
    /** 优惠券类型，通用，某些商品，某些分类，某些标签*/
	private String cardType;
    /**有效开始时间*/
	private String useTimeFrom;
    /** 有效结束时间*/
	private String useTimeTo;
	/** 时间类型，有效，失效*/
	private String timeType;
	public String getCardTypeName() {
		return cardTypeName;
	}
	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getUseTimeFrom() {
		return useTimeFrom;
	}
	public void setUseTimeFrom(String useTimeFrom) {
		this.useTimeFrom = useTimeFrom;
	}
	public String getUseTimeTo() {
		return useTimeTo;
	}
	public void setUseTimeTo(String useTimeTo) {
		this.useTimeTo = useTimeTo;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	

}
