package com.aladingshop.store.dto;
/**
 * 门店基本信息
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月18日上午9:24:44
 */
public class StoreExtraInfoDto {

	/** 门店编号*/
	private String storeCode;
	
	/** 联系人*/
	private String linkMan ;
	
	/** 邮政*/
	private String zip;
	
	/** 联系电话*/
	private String telephone;
	
	/** 门店电话*/
	private String mobile;
	
	/** 具体地址*/
	private String address;
	
	/** 门店图片*/
	private String picture;
	
	/** 交通路线*/
	private String trafficRoutes;
	
	/** 门店描述*/
	private String description;

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
	
	
}
