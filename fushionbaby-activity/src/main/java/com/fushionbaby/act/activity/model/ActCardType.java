package com.fushionbaby.act.activity.model;

import java.util.Date;

/**
 * 
 * @author cyla
 * 
 */
public class ActCardType {
    /** 自增id*/
	private Long id;
	/** 优惠券的有效时间起点*/
	private Date beginTime;
	/** 优惠券的有效时间截点*/
	private Date endTime;
	/** 代金券的面额，优惠金额 */
	private String discountMoney;
	/** 该优惠券的作用范围，在分类下还是特定商品等 */
	private String cardType;
	/** 该优惠券的作用商品id集合，用','分割开 */
	private String idList;
	/** 卡类型的名称*/
	private String name;
	/** 是否可用*/
	private String disable;
	/** 使用类型*/
	private String useType;
	/** 使用次数限制*/
	private int useCountLimit;
	/** 满多少之后才可以使用优惠*/
	private String conditionSkuPriceAbove;
	/** 该优惠券类型的查询编码*/
	private String code;
	/** 优惠券类型的描述*/
	private String description;
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public int getUseCountLimit() {
		return useCountLimit;
	}

	public void setUseCountLimit(int useCountLimit) {
		this.useCountLimit = useCountLimit;
	}

	public String getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(String discountMoney) {
		this.discountMoney = discountMoney;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public String getConditionSkuPriceAbove() {
		return conditionSkuPriceAbove;
	}

	public void setConditionSkuPriceAbove(String conditionSkuPriceAbove) {
		this.conditionSkuPriceAbove = conditionSkuPriceAbove;
	}

}
