package com.fushionbaby.app.dto.store;


/***
 * 
 * @description 门店配置
 * @author 孟少博
 * @date 2016年1月12日下午1:19:21
 */
public class StoreConfigDto {
	
	
	/** 低于免运费的标准收取的运费*/
	private Integer freightAmount;
	
	/** 免运费的标准*/
	private Integer freeFreightAmount;
	
	/** 营业时间*/
	private String shopHours;

	public Integer getFreightAmount() {
		return freightAmount;
	}

	public void setFreightAmount(Integer freightAmount) {
		this.freightAmount = freightAmount;
	}

	public Integer getFreeFreightAmount() {
		return freeFreightAmount;
	}

	public void setFreeFreightAmount(Integer freeFreightAmount) {
		this.freeFreightAmount = freeFreightAmount;
	}

	public String getShopHours() {
		return shopHours;
	}

	public void setShopHours(String shopHours) {
		this.shopHours = shopHours;
	}
	
	
	
}
