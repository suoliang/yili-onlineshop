package com.fushionbaby.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.dto.areaconfig.AreaDto;
import com.fushionbaby.common.dto.areaconfig.ProvinceDto;
import com.fushionbaby.member.model.MemberAreaConfig;

/**
 * 
 * @author King
 *
 */
public interface MemberAreaConfigService<T extends MemberAreaConfig> {
	public String getNameByAreaCode(String areaCode) throws DataAccessException;	
	
	public MemberAreaConfig getAreaCode(String areaCode) throws DataAccessException;
	
	public List<AreaDto> findByParentAreaCode(String code) throws DataAccessException;
	
	/**
	 * 获取省集合
	 * @return
	 */
	public List<ProvinceDto> findAll();
}
