package com.fushionbaby.member.model;

import java.util.Date;
/***
 * 会员评论
 * @author xupeijun
 *
 */
public class MemberSkuComment {
	
	
	private String productCode;
	
	private Long id;
	/** 评论内容 */
	private String commentContent;
	/**评论时间*/
	private Date createTime;
	/** ip*/
	private String ipAddress;
	/** 审核状态 */
	private String status;
    /** 评论人*/
	private String memberName;
    /**版本*/
	private Date version;
	/** 会员id*/
	private Long memberId;

	/** 订单号*/
	private String orderCode;
	
	/**页面显示*/
	private String showTime;
	
    /**订单行id*/
	private Long orderLineId;
	/** 是否删除 （禁用）*/
	private String disable;
	/** 评分数 */
	private Integer score;
	/** 商品编码 */
	private String skuCode;
	/** 商品颜色 */
	private String skuColor;
	/** 商品尺寸 */
	private String skuSize;

	/** 更新时间 */
	private Date updateTime;
	/** 更新id */
	private Long updateId;
	
	/**品论来源  1  安卓，2 ios ，3 web*/
	private Integer sourceCode;
	/** 点赞数*/
	private Integer praiseCount;
	
	/**是否匿名y匿名，n不匿名*/
	private String isAnonymous;
	/**好评1，中评2，差评3*/
	private String commentLevel;
	
	
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Date getVersion() {
		return version;
	}
	public void setVersion(Date version) {
		this.version = version;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getShowTime() {
		return showTime;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public Long getOrderLineId() {
		return orderLineId;
	}
	public void setOrderLineId(Long orderLineId) {
		this.orderLineId = orderLineId;
	}
	public String getIsAnonymous() {
		return isAnonymous;
	}
	public void setIsAnonymous(String isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
	public String getCommentLevel() {
		return commentLevel;
	}
	public void setCommentLevel(String commentLevel) {
		this.commentLevel = commentLevel;
	}
	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public String getDisable() {
		return disable;
	}
	public void setDisable(String disable) {
		this.disable = disable;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getSkuColor() {
		return skuColor;
	}
	public void setSkuColor(String skuColor) {
		this.skuColor = skuColor;
	}
	public String getSkuSize() {
		return skuSize;
	}
	public void setSkuSize(String skuSize) {
		this.skuSize = skuSize;
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	public Integer getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(Integer sourceCode) {
		this.sourceCode = sourceCode;
	}
	public Integer getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	
}