package com.fushionbaby.sysactivity.service;

import java.util.List;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.sysactivity.model.SysActivityArticleSku;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SysActivityArticleSkuService<T extends SysActivityArticleSku>
		extends BaseService<T> {
	List<SysActivityArticleSku> findAll();
	/**
	 * 通过文章的id得到 与该文章关联的商品的id集合
	 * 
	 * @param articleId
	 * @return
	 */
	List<Long> listSkuIdByArticleId(Long articleId);

	/**
	 * 通过文章的id，和关联的是商品id 字符串 修改关联商品
	 * 
	 * 先删除原有关联的商品
	 * 
	 * 再添加
	 * 
	 * @param id
	 * @param ids
	 */
	void updateByArticleId(Long id, String ids);

}
