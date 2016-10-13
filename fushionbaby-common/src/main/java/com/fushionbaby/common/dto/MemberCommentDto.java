package com.fushionbaby.common.dto;

/***
 * 会员评论
 * 
 * @author xupeijun
 *
 */
public class MemberCommentDto {
	/** 评论内容 */
	private String commentContent;
	/** 评论颜色 */
	private String skuColor;

	/** 评论尺寸 类型 */
	private String skuSize;
	/** 商品编号 */
	private String skuCode;
	/** 评论人名 */
	private String memberName;
	/** 评论人编号 */
	private Long memberId;
	/** 评论人图片url */
	// private String memberPicUrl;
	/** 评分数 */
	private Integer score;
	/** 评论id */
	// private Long commentId;
	/** 图片总数 */
	// private Integer imageCount;
	/** 来源编号 */
	// private String sourceCode;
	/** 评论的图片url集合 */
	// private List<String> imgUrlList;
	/** 订单行序号 */
	// private Long soLineId;
	/** 评论时间 */
	private String commentTime;
	/** 点赞数 */
	// private Integer praiseCount;
	/** 是否匿名y匿名，n不匿名 */
	private String isAnonymous;
	/** 好评1，中评2，差评3 */
	private String commentLevel;
	/** 评论回复数量 */
	// private Integer replyCount;
	/** 评论的集合 */
	// private List<MemberCommentReplyDto> commentReplyList;
	/** 订单号 */
	// private String soCode;
	/** 商品图片 */
	private String skuImg;

	/** 商品名称 */
	private String skuName;

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getSkuSize() {
		return skuSize;
	}

	public void setSkuSize(String skuSize) {
		this.skuSize = skuSize;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public String getSkuImg() {
		return skuImg;
	}

	public void setSkuImg(String skuImg) {
		this.skuImg = skuImg;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSkuColor() {
		return skuColor;
	}

	public void setSkuColor(String skuColor) {
		this.skuColor = skuColor;
	}

	public String getCommentLevel() {
		return commentLevel;
	}

	public void setCommentLevel(String commentLevel) {
		this.commentLevel = commentLevel;
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

}
