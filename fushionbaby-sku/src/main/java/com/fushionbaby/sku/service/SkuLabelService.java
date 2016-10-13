package com.fushionbaby.sku.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sku.model.SkuLabel;

/**
 * 
 * @author King
 * 
 */
public interface SkuLabelService<T extends SkuLabel> extends BaseService<T> {

	/**
	 * @author suoliang
	 * @param page
	 *            分页
	 * @return 结果
	 * @throws DataAccessException
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;
	
	
	/**
	 * 通过标签编号获取标签信息
	 * @param code 编号
	 * @return
	 */
	SkuLabel getByCode(String code);
	
	/**
	 * 通过标签编号的前缀获取标签集合
	 * @param prefixCode
	 * @return
	 */
	List<SkuLabel> getLabelListByCode(String prefixCode);
	
	/**
	 * 根据标签编号删除
	 * @param code
	 */
	void deleteByCode(String code);
	
	
	List<SkuLabel> getByType(String type);

}
