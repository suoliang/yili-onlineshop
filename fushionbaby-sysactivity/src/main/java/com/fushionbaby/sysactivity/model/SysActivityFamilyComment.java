package com.fushionbaby.sysactivity.model;

import java.util.Date;

/**
 * 
 * @author xupeijun 索亮-20141107号更新
 * 
 */
public class SysActivityFamilyComment {
	private Long id;

	private Long articleId;

	private Long memberId;

	private Date commentTime;

	private String commentNickname;

	private String commentContent;

	// 仅用于查询,文章标题
	private String articleTitle;

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

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public String getCommentNickname() {
		return commentNickname;
	}

	public void setCommentNickname(String commentNickname) {
		this.commentNickname = commentNickname == null ? null : commentNickname
				.trim();
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent == null ? null : commentContent
				.trim();
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	@Override
	public String toString() {
		return "SysArticleComment [id=" + id + ", articleId=" + articleId
				+ ", memberId=" + memberId + ", commentTime=" + commentTime
				+ ", commentNickname=" + commentNickname + ", commentContent="
				+ commentContent + "]";
	}
}