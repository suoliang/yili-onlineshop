/**
 * 
 */
package com.fushionbaby.common.dto.sysactivity;

/**
 * @author mengshaobo 亲子活动文章
 */
public class SysactivityFamilyDto {
	/** 文章内容 */
	private String content;
	/** 标题 */
	private String title;
	/** web端的banner图片 */
	private String bannerUrl;
	/** 亲自课程编号 */
	private Long familyId;

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getFamilyId() {
		return familyId;
	}

	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}

}
