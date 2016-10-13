package com.fushionbaby.sysactivity.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.sysactivity.dao.SysActivityArticleSkuDao;
import com.fushionbaby.sysactivity.model.SysActivityArticleSku;
import com.fushionbaby.sysactivity.service.SysActivityArticleSkuService;

/**
 * 
 * @author xupeijun
 * 
 */
@Service
public class SysActivityArticleSkuServiceImpl implements
		SysActivityArticleSkuService<SysActivityArticleSku> {

	@Autowired
	private SysActivityArticleSkuDao sysArticleSkuDao;

	/*
	 * @Autowired private SysArticleSkuService<SysArticleSku> articleSkuService;
	 */
	public void add(SysActivityArticleSku sysArticleSku)
			throws DataAccessException {
		sysArticleSkuDao.add(sysArticleSku);
	}

	public void deleteById(Long id) throws DataAccessException {
		sysArticleSkuDao.deleteById(id);
	}

	public void update(SysActivityArticleSku sysArticleSku)
			throws DataAccessException {
		sysArticleSkuDao.update(sysArticleSku);
	}

	public SysActivityArticleSku findById(Long id) throws DataAccessException {
		return sysArticleSkuDao.findById(id);
	}

	public List<SysActivityArticleSku> findAll() throws DataAccessException {
		return sysArticleSkuDao.findAll();
	}

	/*
	 * 通过文章id得到对应的商品id集合 (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sysactivity.service.SysArticleSkuService#listSkuIdByArticleId
	 * (java.lang.Long)
	 */

	public List<Long> listSkuIdByArticleId(Long articleId) {
		// 通过文章的id 查询关联表 得到 文章关联商品 的集合对象
		List<SysActivityArticleSku> listAllByid = this.sysArticleSkuDao
				.listSkusByArticleId(articleId);
		// 再通过 文章关联商品 对象得到 关联商品的id集合
		List<Long> listskuIdByarticleId = new ArrayList<Long>();
		if (listAllByid.size() > 0) {
			for (SysActivityArticleSku sysArticleSku : listAllByid) {
				listskuIdByarticleId.add(sysArticleSku.getSkuId());
			}
		}
		return listskuIdByarticleId;
	}

	public void updateByArticleId(Long id, String ids) {
		// 通过文章的id清除 与该文章关联的商品
		this.deleteByArticleId(id);
		String idd[] = ids.split(",");
		if (idd.length > 0) {
			// 添加风尚宝贝的时候还要将关联商品添加到关联的表中，通过文章的id和关联商品的id
			SysActivityArticleSku sysArticleSku = new SysActivityArticleSku();
			sysArticleSku.setArticleId(id);
			// 得到关联商品的id数组
			for (String string : idd) {
				sysArticleSku.setSkuId(Long.valueOf(string));

				this.add(sysArticleSku);
			}
		}
	}

	/**
	 * 通过文章id删除与其相关的 关联商品
	 * 
	 * @param id
	 */
	private void deleteByArticleId(Long id) {
		this.sysArticleSkuDao.deleteByArticleId(id);
	}

}
