package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.sku.cms.model.SkuLabel;

/**
 * 
 * @author King
 * 
 */
public interface SkuLabelDao {

	void add(SkuLabel maLabel);

	void deleteById(Long id);

	void update(SkuLabel maLabel);

	SkuLabel findById(Long id);

	List<SkuLabel> findAll();
	
	/**
	 * 分页查询
	 * @author suoliang
	 */
	List<SkuLabel> getListPage(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * 
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);
	/**
	 * 通过标签编号获取标签信息
	 * @param code
	 * @return
	 */
	SkuLabel getByCode(String code) ;
	
	void deleteByCode(String code);
	
	List<SkuLabel> getLabelListByCode(String prefixCode);

}