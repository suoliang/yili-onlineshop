/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月20日下午6:01:12
 */
package com.fushionbaby.app.dto;

import java.util.List;

import com.fushionbaby.common.dto.FocusPicDto;
import com.fushionbaby.common.dto.sku.SkuDto;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月20日下午6:01:12
 */
public class ActivityFullCutAreaDto {
	
	/** 标签banner*/
	private FocusPicDto labelBanner;
	
	/** 商品集合*/
	private List<SkuDto> skuList;
	
	/** 便签标题*/
	private String title;

	public FocusPicDto getLabelBanner() {
		return labelBanner;
	}

	public void setLabelBanner(FocusPicDto labelBanner) {
		this.labelBanner = labelBanner;
	}

	public List<SkuDto> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<SkuDto> skuList) {
		this.skuList = skuList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	 
}
