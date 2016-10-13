package com.aladingshop.sku.cms.model;

import java.util.Date;

public class SkuExtraInfo {
	private Long id;
	/** 商品编码*/
	private String skuCode;
	/** 上架时间*/
	private Date onshelvestime;
	/** 下架时间*/
	private Date offshelvestime;
	/** 商品是否有赠品*/
	private String hasGift;
	/** 商品是否有优惠券*/
	private String hasDiscount;
	/** 商品是是赠品*/
	private String isGift;
	/** 是否有视频*/
	private String isVideo;
	/** 视频路径*/
	private String videoUrl;
	/** 商品销量 */
	private Integer salesVolume;
	/** 实际商品销量 */
	private Integer actualSalesVolume;
	/** 是否是会员商品*/
	private String isMemberSku;
	
	/** 商品评论数*/
	private Integer commentCount;
	private Date version;
	/** 创建用户id*/
	private Long createId;
	/** 更新id*/
	private Long updateId;
	/** 创建时间*/
	private Date createTime;
	/** 更新时间*/
	private Date updateTime;
	
	/** 门店编码*/
	private String storeCode;
	
	

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	
	public String getIsMemberSku() {
		return isMemberSku;
	}
	public void setIsMemberSku(String isMemberSku) {
		this.isMemberSku = isMemberSku;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public Date getOnshelvestime() {
		return onshelvestime;
	}
	public void setOnshelvestime(Date onshelvestime) {
		this.onshelvestime = onshelvestime;
	}
	public Date getOffshelvestime() {
		return offshelvestime;
	}
	public void setOffshelvestime(Date offshelvestime) {
		this.offshelvestime = offshelvestime;
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
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public Date getVersion() {
		return version;
	}
	public void setVersion(Date version) {
		this.version = version;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	public Long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
