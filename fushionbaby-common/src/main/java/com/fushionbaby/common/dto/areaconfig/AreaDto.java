package com.fushionbaby.common.dto.areaconfig;

import java.io.Serializable;

/**
 * 省市县dto
 * @author test
 *
 */
public class AreaDto implements Serializable {
	private static final long serialVersionUID = -7897411512421343365L;

	private String code;//Code
	
	private String name;//名字
	
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
}
