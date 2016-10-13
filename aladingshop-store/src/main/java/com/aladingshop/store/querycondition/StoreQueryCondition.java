/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月10日下午5:36:07
 */
package com.aladingshop.store.querycondition;

import com.alading.common.condition.QueryCondition;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月10日下午5:36:07
 */
public class StoreQueryCondition extends QueryCondition {

	
	/** 门店编号*/
	private String number;
	
	/**门店code*/
	private String code;
	
	/** 门店名称*/
	private String name;
	
	/** 所属省份Id*/
	private String provinceCode;
	
	/** 城市Id*/
	private String cityCode;
	
	/** 区县Id*/
	private String countryCode;
	
	/** 小区Id*/
	private String cellCode;
	
	/** 小区名称*/
	private String cellName;
	
	/** 是否删除*/
	private String isDeleted;
	
	/** 状态*/
	private Integer status;
	
	
	

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
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

	public String getCellCode() {
		return cellCode;
	}

	public void setCellCode(String cellCode) {
		this.cellCode = cellCode;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
}
