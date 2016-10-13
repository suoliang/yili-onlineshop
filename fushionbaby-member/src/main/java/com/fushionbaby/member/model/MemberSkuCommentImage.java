package com.fushionbaby.member.model;

import java.util.Date;

public class MemberSkuCommentImage {
	private Long id;
	private Long commentId;//评价编号
	private String imgUrl;//图片路径
	private Integer index;//图片显示顺序索引
	private Integer sourceCode;//评价来源
	private Date createTime;//图片上传时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Integer getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(Integer sourceCode) {
		this.sourceCode = sourceCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
