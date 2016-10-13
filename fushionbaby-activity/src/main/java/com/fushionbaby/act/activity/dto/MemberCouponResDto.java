package com.fushionbaby.act.activity.dto;
/**
 * 回传给 调起app 服务接口的 数据类型
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年10月31日下午1:24:52
 */
public class MemberCouponResDto {
	 /**代金券金额*/
     private String couponMoney;
     /** 代金券的状态 1可用，2已使用，3已过期  4还未生效*/
     private String status;
     /**代金券的编码*/
     private String couponCode;
     /**代金券的说明*/
     private String couponDesc;
     /**代金券的有限期描述*/
     private String couponTimeDesc;
     /**密码*/
     private String password;
     
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCouponTimeDesc() {
		return couponTimeDesc;
	}
	public void setCouponTimeDesc(String couponTimeDesc) {
		this.couponTimeDesc = couponTimeDesc;
	}
	public String getCouponMoney() {
		return couponMoney;
	}
	public void setCouponMoney(String couponMoney) {
		this.couponMoney = couponMoney;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getCouponDesc() {
		return couponDesc;
	}
	public void setCouponDesc(String couponDesc) {
		this.couponDesc = couponDesc;
	}
     
     
	
}
