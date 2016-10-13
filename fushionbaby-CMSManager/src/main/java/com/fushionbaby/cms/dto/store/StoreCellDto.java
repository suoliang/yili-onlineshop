package com.fushionbaby.cms.dto.store;

import java.util.Date;

/**
 * 
 * @description 门店小区Dto
 * @author 孟少博
 * @date 2015年12月19日下午2:14:12
 */
public class StoreCellDto {
	
	/** id*/
	private Long id;
	
	/** 所在省份 */
	private String provinceCode;
	
	/** 归属城市 */
	private String cityCode;
	
	/** 所属县级区域 */
	private String countryCode;
	
	/** 省名称*/
	private String provinceName;
	
	/** 城市名*/
	private String cityName;
	
	/** 区县名称*/
	private String countryName;

	/** 小区名称*/
	private String cellName;
	
	/** 小区编号*/
	private String cellCode;
	
	/** 是否禁用*/
	private String isDisable;
	
	/** 显示顺序*/
	private Integer showOrder;
	
	private Date createTime;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCellCode() {
		return cellCode;
	}

	public void setCellCode(String cellCode) {
		this.cellCode = cellCode;
	}

	public String getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(String isDisable) {
		this.isDisable = isDisable;
	}

	public Integer getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(Integer showOrder) {
		this.showOrder = showOrder;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
