package com.aladingshop.sku.cms.service;

import java.util.List;

import com.aladingshop.sku.cms.model.SkuImage;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author xupeijun
 * 
 * @param <T>
 */
public interface SkuImageService<T extends SkuImage> extends BaseService<T> {
	/**
	 * 通过每个商品的商品id得到 该商品的所有图片
	 * 
	 * @param skuId
	 * @return
	 */
	List<SkuImage> findBySkuCode(String skuCode);
	
	SkuImage findImageBySkuCode(String skuCode, String imageType);

	/**
	 * 判断商品下是否有图片
	 * 
	 * @param id
	 * @return
	 */
	boolean IsHaveImage(String skuCode);

	/**
	 * 通过商品编号和图片类型编号查询图片
	 * 
	 * @param skuCode
	 *            商品编码
	 * @param imageTypeCode
	 *            图片类型
	 * @return
	 */
	List<SkuImage> findBySkuCodeByImageTypeCode(String skuCode,
			String imageTypeCode);

	BasePagination getListPage(BasePagination page);

}
