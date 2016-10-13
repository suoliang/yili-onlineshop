package com.fushionbaby.cms.dto.store;

import java.util.Date;

public class StoreExtraInfoDto {
	
	/** 门店额外信息id */
	private Long id;
	/** 门店系统CODE,系统生成 ，不能修改*/
	private String storeCode;
	/** 联系人 */
	private String linkMan;
	/** 邮编 */
	private String zip;
	/** 联系电话 */
	private String telephone;
	/** 门店电话 */
	private String mobile;
	/** 门店地址 */
	private String address;
	/** 门店照片 */
	private String picture;
	/** 交通路线*/
	private String trafficRoutes;
	/** 门店描述 */
	private String description;
	/** 更新id */
	private Long updateId;
	/** 更新时间 */
	private Date updateTime;
	/**门店编号*/
	private String storeNumber;
	/**门店名称*/
	private String storeName;
	/**身份证号*/
	private String identityCardNo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getTrafficRoutes() {
		return trafficRoutes;
	}
	public void setTrafficRoutes(String trafficRoutes) {
		this.trafficRoutes = trafficRoutes;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getIdentityCardNo() {
		return identityCardNo;
	}
	public void setIdentityCardNo(String identityCardNo) {
		this.identityCardNo = identityCardNo;
	}
	
	
	
	
}
