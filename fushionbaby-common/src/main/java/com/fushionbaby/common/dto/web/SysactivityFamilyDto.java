/**
 * 
 */
package com.fushionbaby.common.dto.web;


/**
 * @author mengshaobo 亲子活动文章
 */
public class SysactivityFamilyDto {
	/** 文章内容 （或评论的内容）*/
	private String content;
	/** 标题 */
	private String title;
	/** web端的banner图片 */
	private String bannerUrl;
	/** 亲子课程编号 */
	private Long familyId;
	
	/** 亲子课程评论人id*/
	private Long memberId;
	/**亲子课程评论人名称*/
	private String userName;
	
	
	
	
	
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

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
