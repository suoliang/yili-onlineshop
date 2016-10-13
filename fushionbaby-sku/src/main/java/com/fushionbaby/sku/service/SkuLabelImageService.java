/**
 * @description aladingshop.com 上海一里网络科技有限公司
 * @author 孟少博
 * @date 2015年11月6日上午10:25:42
 */
package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuLabelQueryCondition;
import com.fushionbaby.sku.model.SkuLabelImage;

/**
 * @description 标签图片
 * @author 孟少博
 * @date 2015年11月6日上午10:25:42
 */
public interface SkuLabelImageService<T extends SkuLabelImage> {

	
	
	List<SkuLabelImage>  findByCondition(SkuLabelQueryCondition queryCondition);
	
	
}
