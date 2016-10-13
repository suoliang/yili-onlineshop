package com.aladingshop.sku.cms.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.aladingshop.sku.cms.model.SkuBrand;
import com.fushionbaby.common.condition.sku.SkuBrandQueryCondition;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

public interface SkuBrandService<T extends SkuBrand> extends BaseService<T> {

	/**
	 * 分页查询商品品牌列表
	 * 
	 * @param pageParams
	 * @return
	 * @throws DataAccessException
	 */
	BasePagination getListPage(BasePagination pageParams)
			throws DataAccessException;

	List<T> findAll();
	/**
	 * 分页查询为显示的品牌列表
	 * 
	 * @param offset
	 *            开始
	 * @param size
	 *            数量
	 * @return
	 * @throws DataAccessException
	 */
	List<SkuBrand> findAllApp(Integer offset, Integer size)
			throws DataAccessException;

	/***
	 * 查询为显示的品牌的全部数量
	 * 
	 * @param offset
	 *            开始
	 * @param size
	 *            数量
	 * @return
	 * @throws DataAccessException
	 */
	List<SkuBrand> findAllWeb(Integer offset, Integer size)
			throws DataAccessException;
	
	/**
	 * 通过查询条件查询商品品牌
	 * @param queryCondition 查询条件
	 * @return
	 */
	List<SkuBrand> findByCondition(SkuBrandQueryCondition queryCondition) throws DataAccessException;

	/**
	 * 通过商品编号查询品牌
	 * 
	 * @param skuId
	 * @return
	 */
	SkuBrand findBySkuUnCode(String skuCode);
	
	SkuBrand findByBrandCode(String brandCode);
}