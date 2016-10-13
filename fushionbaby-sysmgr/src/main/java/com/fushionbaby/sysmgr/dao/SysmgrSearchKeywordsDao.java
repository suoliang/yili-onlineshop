package com.fushionbaby.sysmgr.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.sysmgr.model.SysmgrSearchKeywords;

/**
 * 
 * @author King
 * 
 */
public interface SysmgrSearchKeywordsDao {

	void add(SysmgrSearchKeywords sysArticleSku);

	void deleteById(Long id);

	void update(SysmgrSearchKeywords sysArticleSku);

	SysmgrSearchKeywords findById(Long id);

	List<SysmgrSearchKeywords> findAll();

	/**
	 * 通过关键字查询商品关键字
	 * 
	 * @param searchKey
	 *            关键字
	 * @return
	 */
	SysmgrSearchKeywords queryBySearchKey(@Param("searchKey") String searchKey,
			@Param("sourceCode") String sourceCode);

}
