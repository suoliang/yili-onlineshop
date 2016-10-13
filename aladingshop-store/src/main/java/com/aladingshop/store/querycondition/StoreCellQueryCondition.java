/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月11日下午2:05:19
 */
package com.aladingshop.store.querycondition;

import com.alading.common.condition.QueryCondition;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月11日下午2:05:19
 */
public class StoreCellQueryCondition  extends QueryCondition{
		
	/** 是否使用*/
	private String isDisable;
	
	/** 小区编号*/
	private String code;
	
	/** 地区编号*/
	private String countryCode;
	
	/** 小区名称*/
	private String name;

	public String getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(String isDisable) {
		this.isDisable = isDisable;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
