/**
 * 
 */
package com.fushionbaby.common.dto.sysactivity;

/**
 * @author mengshaobo 亲子评论
 */
public class SysactivityFamilyCommentDto {
	/** 昵称 */
	private String nickName;
	/** 评论内容 */
	private String content;
	/** 评论日期 */
	private String commentDate;
	/** 评论时间 */
	private String commentTime;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

}
