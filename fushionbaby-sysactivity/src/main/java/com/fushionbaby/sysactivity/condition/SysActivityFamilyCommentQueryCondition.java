/**
 * 
 */
package com.fushionbaby.sysactivity.condition;

/**
 * @author mengshaobo 亲子活动评论查询条件
 */
public class SysActivityFamilyCommentQueryCondition extends
		BaseSysActivityQueryCondition {

	/** 文章号 */
	private Long articleId;
	/** 评论昵称 */
	private String commentNickname;

	public String getCommentNickname() {
		return commentNickname;
	}

	public void setCommentNickname(String commentNickname) {
		this.commentNickname = commentNickname;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

}
