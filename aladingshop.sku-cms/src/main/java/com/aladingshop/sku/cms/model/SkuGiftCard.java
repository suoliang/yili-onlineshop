package com.aladingshop.sku.cms.model;

import java.util.Date;

/***
 * 礼品券 
 * @author xupeijun
 *
 */
public class SkuGiftCard {

	
	private Long id;
	/** 礼品卡的编码*/
	private String code;
	/** 礼品卡号*/
	private String cardNo;
	/** 充值密码*/
	private String chargePwd;
	/** 面额*/
	private String faceValue;
	/** 创建时间*/
	private Date createTime;
	/** 过期时间*/
	private Date expiration;
	/** 礼品卡的状态  0 代表生成未售出，1代表售出未使用，2代表已使用*/
	private String status;
	/** 礼品卡的充值会员账号（使用去向）*/
	private Long memberId;
	/** 充值时间*/
	private Date chargeTime;
	
	/** 礼品卡的库存量，暂存*/
	private Integer num;
	
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getChargePwd() {
		return chargePwd;
	}
	public void setChargePwd(String chargePwd) {
		this.chargePwd = chargePwd;
	}
	public String getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getExpiration() {
		return expiration;
	}
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Date getChargeTime() {
		return chargeTime;
	}
	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}
	
	
	
	
	
	
}
