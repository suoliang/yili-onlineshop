package com.fushionbaby.sysactivity.dao;

import java.util.List;

import com.fushionbaby.sysactivity.model.SysActivityArticleSku;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SysActivityArticleSkuDao {

	void add(SysActivityArticleSku sysArticleSku);

	void deleteById(Long id);

	void update(SysActivityArticleSku sysArticleSku);

	SysActivityArticleSku findById(Long id);

	List<SysActivityArticleSku> findAll();

	/**
	 * 通过文章的id得到 与该文章关联的商品的id集合
	 * 
	 * @param articleId
	 * @return
	 */
	List<SysActivityArticleSku> listSkusByArticleId(Long articleId);

	/**
	 * 通过文章的id删除
	 * 
	 * @param id
	 */
	void deleteByArticleId(Long id);

}