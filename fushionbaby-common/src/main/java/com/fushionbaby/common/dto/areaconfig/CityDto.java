/**
 * 
 */
package com.fushionbaby.common.dto.areaconfig;

import java.util.List;

/**
 * @description 城市
 * @author 孟少博
 * @date 2015年8月17日上午10:55:33
 */
public class CityDto extends AreaDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5930026310938786337L;

	private List<AreaDto> areaList;

	public List<AreaDto> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<AreaDto> areaList) {
		this.areaList = areaList;
	}

	

}
