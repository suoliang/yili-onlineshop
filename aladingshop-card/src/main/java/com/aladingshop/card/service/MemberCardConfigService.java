package com.aladingshop.card.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.aladingshop.card.model.MemberCardConfig;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/**
 * @author xinlangtao
 * 
 */
public interface MemberCardConfigService<T extends MemberCardConfig> extends BaseService<T> {
	List<MemberCardConfig> findAll();

	/**
	 * 分页
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;

	/**
	 * 修改配置状态
	 * 
	 * @param id
	 */
	void updateIsDisabled(MemberCardConfig memberCardConfig);

	/***
	 * 通过type 获得配置的列表
	 * 
	 * @param type
	 * @return
	 */
	List<MemberCardConfig> findByType(String type);
}
