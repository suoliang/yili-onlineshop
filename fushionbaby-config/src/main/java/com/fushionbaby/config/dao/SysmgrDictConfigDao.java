package com.fushionbaby.config.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.config.model.SysmgrDictConfig;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SysmgrDictConfigDao {

	public void add(SysmgrDictConfig sysDictConfig);

	public void deleteById(Long id);
	
	public void deleteByType(String type);

	public void update(SysmgrDictConfig sysDictConfig);
	
	public SysmgrDictConfig findById(Long id);

	public List<SysmgrDictConfig> findByLabelValueType(@Param("label")String label,@Param("value")String value,@Param("type")String type);

	public Integer getTotal(Map<String, Object> map);

	public List<SysmgrDictConfig> getListPage(Map<String, Object> map);

}
