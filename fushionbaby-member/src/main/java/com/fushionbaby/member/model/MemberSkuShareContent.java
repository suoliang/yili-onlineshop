package com.fushionbaby.member.model;

import java.util.Date;

public class MemberSkuShareContent {
	
	private Long id;
	
	/** 会员号*/
	private Long memberId;
	
	/** 分享地址*/
	private String shareUrl;
	
	/** 分析内容*/
	private String shareContent;
	
	/** 商品编号*/
	private String skuCode;
	
	/** 来源编号*/
	private String sourceCode;
	
	/** 创建时间*/
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getShareUrl() {
		return shareUrl;
	}
	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}
	public String getShareContent() {
		return shareContent;
	}
	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}
	
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
