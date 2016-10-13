/**
 * 
 */
package com.fushionbaby.facade.biz.common.sku;

import java.util.List;

import com.fushionbaby.common.dto.FocusPicDto;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年7月29日下午3:31:04
 */
public interface BannerFacade {
	/***
	 * 获取banner图片
	 * 
	 * @param adCode
	 *            广告编号
	 * @return
	 */
	List<FocusPicDto> getFocusPic(String adCode);
	
	
	/**
	 * 通过标签编号和标签类型获取广告图
	 * @param labelCode 标签编号
	 * @param imageType 图片类型
	 * @return
	 */
	FocusPicDto getLabelBanner(String labelCode,String imageType);
	
	/**
	 * 通过分类编号获取广告图
	 * @param categoryCode
	 * @param imageType 图片类型
	 * @return
	 */
	FocusPicDto getCategoryBanner(String categoryCode,String imageType);
}
