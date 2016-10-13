/**
 * 
 */
package com.fushionbaby.common.dto.sku.web;

import java.util.List;

import com.fushionbaby.common.dto.sku.ProductDetailImageDto;
import com.fushionbaby.common.dto.sku.SkuDetailDto;

/**
 * @author mengshaobo
 *
 */
public class WebSkuDetailDto  extends SkuDetailDto{
	private static final long serialVersionUID = 1L;
	/** 商品详情图片大图URL连接 */
	private List<String> skuImagesBig;
	/** 商品详情图片小图URL连接 */
	private List<String> skuImagesSmall;	
	/** 产品的图文详情web端显示 */
	private List<ProductDetailImageDto> productDetailImgs;
	
	public List<String> getSkuImagesBig() {
		return skuImagesBig;
	}
	public void setSkuImagesBig(List<String> skuImagesBig) {
		this.skuImagesBig = skuImagesBig;
	}
	public List<String> getSkuImagesSmall() {
		return skuImagesSmall;
	}
	public void setSkuImagesSmall(List<String> skuImagesSmall) {
		this.skuImagesSmall = skuImagesSmall;
	}
	public List<ProductDetailImageDto> getProductDetailImgs() {
		return productDetailImgs;
	}
	public void setProductDetailImgs(List<ProductDetailImageDto> productDetailImgs) {
		this.productDetailImgs = productDetailImgs;
	}
	
	
}
