package com.fushionbaby.sysmgr.model;

import java.util.Date;

/***
 * @description 门店系统申请信息
 * @author chenyingtao
 *
 */
public class SysmgrStoreApply {
	
	/**自增id */
	private Long id;
	/**申请人姓名 */
	private String name;
	/**申请人电话*/
	private String phone;
	/**申请人城市*/
	private String city;
	/**申请人地址*/
	private String address;
	/**申请时间*/
	private Date applyTime;
	/**来源*/
	private String sourceCode;
	/**是否处理*/
	private String isDeal;
	/**处理时间*/
	private Date dealTime;
	/**处理人*/
	private String dealName;
	/**描述*/
	private String memo;
	
	/**开店申请状态 开店 的意向  1 测试  2有意向（考虑中）  3 已开店 4  废弃 */
	private String intention;
	
	
	
	public String getIntention() {
		return intention;
	}
	public void setIntention(String intention) {
		this.intention = intention;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public String getIsDeal() {
		return isDeal;
	}
	public void setIsDeal(String isDeal) {
		this.isDeal = isDeal;
	}
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	public String getDealName() {
		return dealName;
	}
	public void setDealName(String dealName) {
		this.dealName = dealName;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
	
}
