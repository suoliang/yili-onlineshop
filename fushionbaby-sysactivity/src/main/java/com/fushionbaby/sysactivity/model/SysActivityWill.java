package com.fushionbaby.sysactivity.model;

/**
 * 
 * @author cyla
 * 
 */
public class SysActivityWill {
	private Long id;
	private String webBannerUrl;
	private String appBannerUrl;
	private String webArticleUrl;
	private String appArticleUrl;
	private int showOrder;//排序字段
	private String disable;//y:启用   n:禁用

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWebBannerUrl() {
		return webBannerUrl;
	}

	public void setWebBannerUrl(String webBannerUrl) {
		this.webBannerUrl = webBannerUrl;
	}

	public String getAppBannerUrl() {
		return appBannerUrl;
	}

	public void setAppBannerUrl(String appBannerUrl) {
		this.appBannerUrl = appBannerUrl;
	}

	public String getWebArticleUrl() {
		return webArticleUrl;
	}

	public void setWebArticleUrl(String webArticleUrl) {
		this.webArticleUrl = webArticleUrl;
	}

	public String getAppArticleUrl() {
		return appArticleUrl;
	}

	public void setAppArticleUrl(String appArticleUrl) {
		this.appArticleUrl = appArticleUrl;
	}

	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}
}
