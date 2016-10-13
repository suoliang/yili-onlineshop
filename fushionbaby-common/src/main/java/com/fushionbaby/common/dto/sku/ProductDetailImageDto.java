/**
 * 
 */
package com.fushionbaby.common.dto.sku;

/**
 * @author mengshaobo 产品的商品图片详情
 */
public class ProductDetailImageDto {

	/** 图片路径 */
	private String imgPath;
	/** 图片访问地址 */
	private String imgUrl;
	/** 显示顺序*/
	private Integer sortOrder;

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

}
