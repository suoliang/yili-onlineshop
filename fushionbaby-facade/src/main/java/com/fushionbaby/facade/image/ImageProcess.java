package com.fushionbaby.facade.image;

import java.util.List;

public interface ImageProcess {
	
	/**
	 * 获取缩略图集合
	 * 
	 * @param skuCode
	 *            图片编号
	 * @param imageStandard
	 *            图片尺寸
	 * @return
	 */
	List<String> getThumImageList(String skuCode, String imageStandard);
	 /**
	  * 获取小图
	  * 
	  * @param skuCode
	  *            商品编号
	  * @param imageStandard
	  *            图片尺寸
	  * @return
	  */
	 String getThumImagePath(String skuCode, String imageStandard);
	/**
	 * 获取原始图片集合
	 * 
	 * @param skuCode
	 *            商品编号
	 * @param imageTypeCode
	 *            图片类型编号
	 * @return
	 */
	List<String> getOrigImageList(String skuCode);
	/**
	 * 获得首页产品列表尺寸的图片（单个）
	 * 
	 * @param skuCode
	 *            商品编号
	 * @param imageTypeCode
	 *            图片类型编号
	 * @return
	 */
	 String getOrigImagePath(String skuCode);
	
}
