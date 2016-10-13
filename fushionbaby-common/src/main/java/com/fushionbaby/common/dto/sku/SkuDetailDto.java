package com.fushionbaby.common.dto.sku;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fushionbaby.common.dto.AttrDto;
import com.fushionbaby.common.dto.MemberCommentDto;

/**
 * 
 * @author mengshaobo
 *
 */
public class SkuDetailDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 当前商品 */
	private SelectedSkuDto selectedSku;
	

	/** 商品编号 */
	private String skuNo;

	/** 产品编号 */
	private String productCode;

	/** 商品名称 */
	private String name;

	/** 商品单价(原价) */
	private String priceOld;

	/** 商品单价(现价) */
	private String priceNew;
	
	/** 商品VIP会员价*/
	private String priceVip;
	
	/** 是否是会员商品*/
	private String isMemberSku;

	/** 商品分类编号 */
	private String categoryCode;

	/** 颜色集合 */
	private List<String> colorList;

	/** 尺寸集合 */
	private List<String> sizeList;

	/** 属性集合 */
	private List<AttrDto> attr;

	/** 商品图片多个 */
	private List<String> skuImages;

	/** 图文详情 */
	private List<Map<String, String>> graphicDetails;

	/** 是否已收藏 */
	private String isCollect;

	/** 猜你喜欢的商品 */
	private List<SkuDto> linkSkus;

	/** 视频地址 */
	private String videoUrl;

	/** 同类商品集合 */
	private List<SkuDto> sameCategorySkus;

	/** 关键词集合 */
	private List<String> keyWords;

	/** 商品评价信息 */
	private MemberCommentDto memberComment;
	
	/** 限购数量*/
	private Integer quotaNum;
	
	/** 是否为可用商品*/
	private String usableSku;
	
	/** 积分商城-商品的积分价格 */
	private String priceIntegral;
	
	
	public String getPriceIntegral() {
		return priceIntegral;
	}

	public void setPriceIntegral(String priceIntegral) {
		this.priceIntegral = priceIntegral;
	}

	public String getUsableSku() {
		return usableSku;
	}

	public void setUsableSku(String usableSku) {
		this.usableSku = usableSku;
	}

	public String getPriceVip() {
		return priceVip;
	}

	public void setPriceVip(String priceVip) {
		this.priceVip = priceVip;
	}

	public String getIsMemberSku() {
		return isMemberSku;
	}

	public void setIsMemberSku(String isMemberSku) {
		this.isMemberSku = isMemberSku;
	}

	public Integer getQuotaNum() {
		return quotaNum;
	}

	public void setQuotaNum(Integer quotaNum) {
		this.quotaNum = quotaNum;
	}

	public SelectedSkuDto getSelectedSku() {
		return selectedSku;
	}

	public void setSelectedSku(SelectedSkuDto selectedSku) {
		this.selectedSku = selectedSku;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AttrDto> getAttr() {
		return attr;
	}

	public void setAttr(List<AttrDto> attr) {
		this.attr = attr;
	}

	public List<String> getColorList() {
		return colorList;
	}

	public void setColorList(List<String> colorList) {
		this.colorList = colorList;
	}

	public List<String> getSizeList() {
		return sizeList;
	}

	public void setSizeList(List<String> sizeList) {
		this.sizeList = sizeList;
	}

	public String getPriceOld() {
		return priceOld;
	}

	public void setPriceOld(String priceOld) {
		this.priceOld = priceOld;
	}

	public String getPriceNew() {
		return priceNew;
	}

	public void setPriceNew(String priceNew) {
		this.priceNew = priceNew;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}


	public List<Map<String, String>> getGraphicDetails() {
		return graphicDetails;
	}

	public void setGraphicDetails(List<Map<String, String>> graphicDetails) {
		this.graphicDetails = graphicDetails;
	}

	public List<String> getSkuImages() {
		return skuImages;
	}

	public void setSkuImages(List<String> skuImages) {
		this.skuImages = skuImages;
	}

	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}

	public MemberCommentDto getMemberComment() {
		return memberComment;
	}

	public void setMemberComment(MemberCommentDto memberComment) {
		this.memberComment = memberComment;
	}

	public List<SkuDto> getLinkSkus() {
		return linkSkus;
	}

	public void setLinkSkus(List<SkuDto> linkSkus) {
		this.linkSkus = linkSkus;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public List<SkuDto> getSameCategorySkus() {
		return sameCategorySkus;
	}

	public void setSameCategorySkus(List<SkuDto> sameCategorySkus) {
		this.sameCategorySkus = sameCategorySkus;
	}

	public List<String> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(List<String> keyWords) {
		this.keyWords = keyWords;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

}
