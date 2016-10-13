package com.aladingshop.alabao.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.alabao.model.AlabaoConfig;

public interface AlabaoConfigDao {
	
	public void add(AlabaoConfig alabaoConfig);
	
	public void deleteById(Long id);
	
	public void updateById(AlabaoConfig alabaoConfig);	
	
	public AlabaoConfig findById(Long id);

	/**
	 * 分页查询
	 * 
	 */
	List<AlabaoConfig> getListPage(Map<String, Object> map);
	
	/**
	 * 分页查询的总记录数
	 * 
	 */
	Integer getTotal(Map<String, Object> map);

	public AlabaoConfig findByRateCode(String rateCode);
}