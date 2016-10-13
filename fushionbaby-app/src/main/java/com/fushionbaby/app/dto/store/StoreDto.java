/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月10日下午5:19:01
 */
package com.fushionbaby.app.dto.store;

/**
 * @description 门店基本信息
 * @author 孟少博
 * @date 2015年12月10日下午5:19:01
 */
public class StoreDto {
	
	/** 省编号*/
	private String provinceCode;
	
	/** 省名称*/
	private String provinceName;
	
	/** 城市编号*/
	private String cityCode;
	
	/** 城市名称*/
	private String cityName;
	
	/** 区县 编号*/
	private String countryCode;
	
	/** 区县名称*/
	private String countryName;

	/** 小区编号*/
	private String cellCode;
	
	/** 小区名称*/
	private String cellName;
	
	/**门店code*/
	private String storeCode;
	
	/** 门店名称*/
	private String storeName;
	
	/** 门店状态*/
	private String storeStatus;
	
	
	
	
	public String getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(String storeStatus) {
		this.storeStatus = storeStatus;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCellCode() {
		return cellCode;
	}

	public void setCellCode(String cellCode) {
		this.cellCode = cellCode;
	}

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}


	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	



	
}
