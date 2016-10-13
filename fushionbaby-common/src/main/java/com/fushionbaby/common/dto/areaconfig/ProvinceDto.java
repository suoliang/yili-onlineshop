package com.fushionbaby.common.dto.areaconfig;

import java.util.List;

/**
 * 
 * @description 省
 * @author 孟少博
 * @date 2015年8月17日上午10:54:48
 */
public class ProvinceDto extends AreaDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4950035072469764195L;
	
	private List<CityDto> cityList;

	public List<CityDto> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityDto> cityList) {
		this.cityList = cityList;
	}
	

	
	
}
