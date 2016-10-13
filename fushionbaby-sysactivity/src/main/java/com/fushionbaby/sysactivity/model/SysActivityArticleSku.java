package com.fushionbaby.sysactivity.model;

/**
 * 
 * @author xupeijun
 * 
 */
public class SysActivityArticleSku {
	private Long id;

	private Long articleId;

	private Long skuId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	@Override
	public String toString() {
		return "SysArticleSku [id=" + id + ", articleId=" + articleId
				+ ", skuId=" + skuId + "]";
	}
}