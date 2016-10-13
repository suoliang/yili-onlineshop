package com.fushionbaby.act.activity.model;

import java.util.Date;

/**
 * 
 * @author cyla
 * 
 */
public class ActCard {
	/** 自增id*/
	private Long id;
	/** 优惠券卡号*/
	private String cardNo;
	/**优惠券使用密码*/
	private String password;
	/** 使用时间*/
	private Date usedTime;
	/** 卡类型id*/
	private Long cardTypeId;
	/** 卡使用类型   一次，多次，无限次*/
	private String useType;
	/** 使用次数*/
	private int useCount;

	
	/** 总的使用次数，从卡的类型里面拿到 */
	private Integer maxCount;

	/** 该卡所属的类型名*/
	private String cardTypeName;
	/** 后台展示页面 使用状态 ，1 没有使用过 2 已经使用过  3 已失效  4 禁用不可再用*/
	private String useStatus;
	
	
	/** 该优惠券的有效时间起点*/
    private Date startTime;
    /** 该优惠券的有效时间截点*/
    private Date stopTime;
    /** 该优惠券所属的会员id*/
    private Long memberId;
	
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public int getUseCount() {
		return useCount;
	}

	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public Long getCardTypeId() {
		return cardTypeId;
	}

	public void setCardTypeId(Long cardTypeId) {
		this.cardTypeId = cardTypeId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
