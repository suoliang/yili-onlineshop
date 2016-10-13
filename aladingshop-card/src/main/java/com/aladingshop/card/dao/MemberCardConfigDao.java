package com.aladingshop.card.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.card.model.MemberCardConfig;

/**
 * @author xinlangtao
 * 
 */
public interface MemberCardConfigDao {

	void deleteById(Long id);

	void add(MemberCardConfig config);

	MemberCardConfig findById(Long id);

	List<MemberCardConfig> findAll();

	void update(MemberCardConfig config);

	/**
	 * 分页查询
	 * 
	 */
	List<MemberCardConfig> getListPage(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * 
	 */
	Integer getTotal(Map<String, Object> map);

	void updateIsDisabled(MemberCardConfig memberCardConfig);

	/***
	 * 通过type 类型查询
	 * 
	 * @param type
	 * @return
	 */
	List<MemberCardConfig> findByType(String type);
}