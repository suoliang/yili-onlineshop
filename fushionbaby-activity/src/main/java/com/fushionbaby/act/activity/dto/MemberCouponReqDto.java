package com.fushionbaby.act.activity.dto;
/**
 * 请求得到用户优惠券参数封装类
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年10月31日下午1:24:52
 */
public class MemberCouponReqDto {

	/** 用户登录标志*/
	private String sid;
	
	/**代金券的编码*/
	private String couponCode;
	
	/**购物车结算的标志*/
	private String payOffId;
	
	/**会员id*/
    private Long memberId;	
    
    /** 是否使用优惠券*/
    private String isUseCouponCard;
    /** 密码*/
    private  String password;

	/**区域地址编码*/
    private String areaCode;
    
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIsUseCouponCard() {
		return isUseCouponCard;
	}

	public void setIsUseCouponCard(String isUseCouponCard) {
		this.isUseCouponCard = isUseCouponCard;
	}

	public String getPayOffId() {
		return payOffId;
	}

	public void setPayOffId(String payOffId) {
		this.payOffId = payOffId;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

}
