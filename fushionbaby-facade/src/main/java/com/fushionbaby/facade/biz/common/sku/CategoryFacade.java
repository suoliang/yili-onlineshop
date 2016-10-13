/**
 * 
 */
package com.fushionbaby.facade.biz.common.sku;

import java.util.List;

import com.fushionbaby.common.dto.sku.CategoryDto;
import com.fushionbaby.common.dto.sku.LabelCategoryRelationDto;
import com.fushionbaby.sku.model.SkuCategory;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年7月29日上午10:28:36
 */
public interface CategoryFacade {

	
	/**
	 * 通过分类编号获取商品分类信息
	 * 
	 * @param categoryId
	 *            分类编号
	 * @return
	 */
	CategoryDto getCategoryDto(String storeCode,String categoryCode);

	/**
	 * 查询出所有分类 isNext如果为true，则会查询出当前几点下所有相关联的节点，否是：只查一级分类
	 * @param isNext 是否查一级分类
	 * @param showType（Y，N，null） 
	 * 
	 * @return
	 */
	List<CategoryDto> findAllCategory(String storeCode,boolean isNext, String showType);

	/**
	 * 一级分类关联二级分类 isNext如果为true，则会查询出当前节点下所有相关联的节点，否只查下一级
	 * 
	 * @param code
	 * @return
	 */
	List<CategoryDto> findChildCategory(String storeCode,String code, boolean isNext);
	
	/**
	 * 商品中分类关联出上级所有分类
	 * @param code
	 * @return
	 */
	List<CategoryDto> findParentCategory(String storeCode,String code);
	
	/**
	 * 根据标签关联分类
	 * 
	 * @param labelCode
	 * @return
	 */
	List<LabelCategoryRelationDto> getLabelCategory(String labelCode);
	
	/**
	 * 根据分类code查询出分类对象
	 * @param categoryCode
	 * @return
	 */
	SkuCategory findByCategoryCode(String storeCode,String categoryCode);
	/**
	 * 获取一级分类
	 * @param code 分类编号
	 * @return
	 */
	CategoryDto getParentCategoryDto(String storeCode,String code);
	
	
}
