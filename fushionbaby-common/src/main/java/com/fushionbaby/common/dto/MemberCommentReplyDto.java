package com.fushionbaby.common.dto;



/***
 * 评论回复dto
 * @author xupeijun
 *
 */
public class MemberCommentReplyDto {
	
	/** 评论id（回复的评论）*/
	private String commentId;
	
	/**是否匿名*/
	private String isAnonymous;
	
	/** 显示昵称（不匿名就是 用户昵称，匿名就是 匿名用户）*/
	private String showName;
	
	/** 回复的内容*/
	private String replyContent;
	
	/** 会员昵称*/
	private String memberName;
	
	private String memberId;	 	//会员编号
	private String ipAddress;		//评论回复者IP地址
	private String status;		 	//评论回复状态(1.新建 2.可用(默认) 3.不可用)
	private String sourceCode;     //评价回复来源
	private String createTime;		//评论回复日期
	
	public String getCommentId() {
		return commentId;
	}


	public String getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(String isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	
	
}
