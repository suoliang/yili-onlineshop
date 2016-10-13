package com.fushionbaby.member.dao;

import java.util.List;

import com.fushionbaby.member.model.MemberAreaConfig;
/**
 * 
 * @author King
 *
 */
public interface MemberAreaConfigDao {
	
	/**
	 * 通过areaCode获取名称
	 * @param areaCode
	 * @return
	 */
	public String getNameByAreaCode(String areaCode);
	
	

	MemberAreaConfig findByAreaCode(String areaCode);
	
	
	
	public List<MemberAreaConfig> findByParentAreaCode(String code);
}
