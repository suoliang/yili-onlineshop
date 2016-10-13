package com.aladingshop.store.dto;
/**
 * 
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月3日上午11:28:45
 */
public class SkuExtraInfoDto {

	private String skuCode;
	
	/** 是否有赠品*/
	private String hasGift;
	
	/** 是否有优惠券*/
	private String hasDiscount;
	
	/** 是否是赠品*/
	private String isGift;
	
	/** 是否有视频*/
	private String isVideo;
	
	/** 视频路径*/
	private String videoUrl;
	
	/** 销量*/
	private Integer salesVolume;
	
	/** 实际销量*/
	private Integer actualSalesVolume;
	
	/** 是否是会员商品*/
	private String isMemberSku;

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getHasGift() {
		return hasGift;
	}

	public void setHasGift(String hasGift) {
		this.hasGift = hasGift;
	}

	public String getHasDiscount() {
		return hasDiscount;
	}

	public void setHasDiscount(String hasDiscount) {
		this.hasDiscount = hasDiscount;
	}

	public String getIsGift() {
		return isGift;
	}

	public void setIsGift(String isGift) {
		this.isGift = isGift;
	}

	public String getIsVideo() {
		return isVideo;
	}

	public void setIsVideo(String isVideo) {
		this.isVideo = isVideo;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Integer getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

	public Integer getActualSalesVolume() {
		return actualSalesVolume;
	}

	public void setActualSalesVolume(Integer actualSalesVolume) {
		this.actualSalesVolume = actualSalesVolume;
	}

	public String getIsMemberSku() {
		return isMemberSku;
	}

	public void setIsMemberSku(String isMemberSku) {
		this.isMemberSku = isMemberSku;
	}


	
	
}
