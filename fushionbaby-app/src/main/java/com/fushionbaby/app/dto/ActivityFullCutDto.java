/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月20日下午5:59:50
 */
package com.fushionbaby.app.dto;

import java.util.List;

import com.fushionbaby.common.dto.FocusPicDto;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月20日下午5:59:50
 */
public class ActivityFullCutDto {
	
	/** 活动规则*/
	private FocusPicDto rule;
	
	/** 活动banner*/
	private FocusPicDto banner;
	
	/** 活动满足专区列表*/
	private List<ActivityFullCutAreaDto> areas;


	public List<ActivityFullCutAreaDto> getAreas() {
		return areas;
	}

	public void setAreas(List<ActivityFullCutAreaDto> areas) {
		this.areas = areas;
	}

	public FocusPicDto getRule() {
		return rule;
	}

	public void setRule(FocusPicDto rule) {
		this.rule = rule;
	}

	public FocusPicDto getBanner() {
		return banner;
	}

	public void setBanner(FocusPicDto banner) {
		this.banner = banner;
	}

	
	
}
