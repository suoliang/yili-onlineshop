/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年11月3日下午4:15:33
 */
package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.sku.cms.model.SkuLabelImage;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年11月3日下午4:15:33
 */
public interface SkuLabelImageDao {
	
	void add(SkuLabelImage skuLabelImage);
	
	void deleteById(Long id);
	
	void update(SkuLabelImage skuLabelImage);
	
	SkuLabelImage findById(Long id);
	
	List<SkuLabelImage> getListPage(Map<String, Object> params);
	
	Integer getTotal(Map<String, Object> params);
	
	
}
