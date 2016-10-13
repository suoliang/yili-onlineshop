package com.fushionbaby.member.model;

import java.util.Date;

public class MemberSkuCommentReply {
	private Long id;
	private Long commentId;	    	//评价编号
	private Long memberId;	 		//会员编号
	private String isAnonymous;		//是否匿名
	private String memberName;		//评论回复者昵称
	private String replyContent;	//评论回复内容
	private String ipAddress;		//评论回复者IP地址
	private Integer status;		 	//评论回复状态(1.新建 2.可用(默认) 3.不可用)
	private String sourceCode;     //评价回复来源
	private Date createTime;		//评论回复日期
	private String showTime;		//显示评论回复日期
	public String getShowTime() {
		return showTime;
	}
	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
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
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getIsAnonymous() {
		return isAnonymous;
	}
	public void setIsAnonymous(String isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
