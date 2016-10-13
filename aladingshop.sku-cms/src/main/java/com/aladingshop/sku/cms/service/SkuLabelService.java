package com.aladingshop.sku.cms.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.aladingshop.sku.cms.model.SkuLabel;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

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
	
	
	List<SkuLabel> findByType(String type);
	
	void deleteByCode(String code);
	
	
	/**
	 * 通过标签编号的前缀获取标签集合
	 * @param prefixCode
	 * @return
	 */
	List<SkuLabel> getLabelListByCode(String prefixCode);

}
