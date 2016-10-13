package com.fushionbaby.common.dto.sku.app;

import com.fushionbaby.common.dto.sku.SkuDto;

/**
 * 
 * @description 商品历史浏览记录
 * @author 孟少博
 * @date 2015年11月5日上午9:40:29
 */
public class SkuBrowseHistoriesDto {

	/** 用户ID*/
	private Long memberId;
	
	/** 浏览次数*/
	private Integer browseCounts;
	
	/** 浏览时间*/
	private String browseTime;
	
	/** 商品信息*/
	private SkuDto skuDto;
	
	
	
	public SkuDto getSkuDto() {
		return skuDto;
	}
	public void setSkuDto(SkuDto skuDto) {
		this.skuDto = skuDto;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Integer getBrowseCounts() {
		return browseCounts;
	}
	public void setBrowseCounts(Integer browseCounts) {
		this.browseCounts = browseCounts;
	}
	public String getBrowseTime() {
		return browseTime;
	}
	public void setBrowseTime(String browseTime) {
		this.browseTime = browseTime;
	}
	
	
}
