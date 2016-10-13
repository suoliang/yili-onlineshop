package com.aladingshop.store.model;

import java.util.Date;

public class Store {

	/** 门店id */
	private Long id;
	
	/** 门店商户号 */
	private String number;
	
	/** 门店系统CODE,系统生成 ，不能修改*/
	private String code;
	
	/** 门店名 */
	private String name;
	
	/** 所在省份 */
	private String provinceCode;
	
	/** 归属城市 */
	private String cityCode;
	
	/** 所属县级区域 */
	private String countryCode;
	
	/** 所属小区 */
	private String cellCode;
	
	/** 所在省份 */
	private String provinceName;
	
	/** 归属城市 */
	private String cityName;
	
	/** 所属县级区域 */
	private String countryName;
	
	/** 所属小区 */
	private String cellName;
	
	
	/** 状态0开通;1运营未审核，2运营审核不通过,11过期，21停用 */
	private Integer status;
	/** 纬度 */
	private Float latitude;
	/** 经度 */
	private Float longitude;
	/** 是否删除：n否 y是 */
	private String isDeleted;
	/** 创建时间 */
	private Date createTime;
	/** 创建用户id */
	private Long createId;
	/** 更新id */
	private Long updateId;
	/** 更新时间 */
	private Date updateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
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
	public String getCellCode() {
		return cellCode;
	}
	public void setCellCode(String cellCode) {
		this.cellCode = cellCode;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCellName() {
		return cellName;
	}
	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	
	
}
