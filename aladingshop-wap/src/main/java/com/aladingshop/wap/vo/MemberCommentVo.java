/**
 * 
 */
package com.aladingshop.wap.vo;

import java.util.List;

import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.MemberCommentDto;

/**
 * @description 商品评论封装类
 * @author 孙涛
 * @date 2015年8月11日下午5:23:21
 */
public class MemberCommentVo {
	/** 所有评论总量 */
	private Integer allCommentCount = 0;
	/** 所有评论总页数 */
	private Integer allPage;
	/** 所有评论当前页 */
	private Integer allCurrentPage = 1;
	/** 好评总量 */
	private Integer goodCommentCount = 0;
	/** 好评总页数 */
	private Integer goodPage;
	/** 好评当前页 */
	private Integer goodCurrentPage = WebConstant.DEFAULT_PAGE_INDEX;
	/** 中评总量 */
	private Integer midCommentCount = 0;
	/** 中评总页数 */
	private Integer midPage;
	/** 中评当前页 */
	private Integer midCurrentPage = WebConstant.DEFAULT_PAGE_INDEX;
	/** 差评总量 */
	private Integer badCommentCount = 0;
	/** 差评总页数 */
	private Integer badPage;
	/** 差评当前页 */
	private Integer badCurrentPage = WebConstant.DEFAULT_PAGE_INDEX;
	/** 所有评论列表 */
	private List<MemberCommentDto> allComment;
	/** 好评列表 */
	private List<MemberCommentDto> goodComment;
	/** 中评列表 */
	private List<MemberCommentDto> midComment;
	/** 差评列表 */
	private List<MemberCommentDto> badComment;

	public Integer getAllCommentCount() {
		return allCommentCount;
	}

	public void setAllCommentCount(Integer allCommentCount) {
		this.allCommentCount = allCommentCount;
	}

	public Integer getAllPage() {
		return allPage;
	}

	public void setAllPage(Integer allPage) {
		this.allPage = allPage;
	}

	public Integer getAllCurrentPage() {
		return allCurrentPage;
	}

	public void setAllCurrentPage(Integer allCurrentPage) {
		this.allCurrentPage = allCurrentPage;
	}

	public Integer getGoodCommentCount() {
		return goodCommentCount;
	}

	public void setGoodCommentCount(Integer goodCommentCount) {
		this.goodCommentCount = goodCommentCount;
	}

	public Integer getGoodPage() {
		return goodPage;
	}

	public void setGoodPage(Integer goodPage) {
		this.goodPage = goodPage;
	}

	public Integer getGoodCurrentPage() {
		return goodCurrentPage;
	}

	public void setGoodCurrentPage(Integer goodCurrentPage) {
		this.goodCurrentPage = goodCurrentPage;
	}

	public Integer getMidCommentCount() {
		return midCommentCount;
	}

	public void setMidCommentCount(Integer midCommentCount) {
		this.midCommentCount = midCommentCount;
	}

	public Integer getMidPage() {
		return midPage;
	}

	public void setMidPage(Integer midPage) {
		this.midPage = midPage;
	}

	public Integer getMidCurrentPage() {
		return midCurrentPage;
	}

	public void setMidCurrentPage(Integer midCurrentPage) {
		this.midCurrentPage = midCurrentPage;
	}

	public Integer getBadCommentCount() {
		return badCommentCount;
	}

	public void setBadCommentCount(Integer badCommentCount) {
		this.badCommentCount = badCommentCount;
	}

	public Integer getBadPage() {
		return badPage;
	}

	public void setBadPage(Integer badPage) {
		this.badPage = badPage;
	}

	public Integer getBadCurrentPage() {
		return badCurrentPage;
	}

	public void setBadCurrentPage(Integer badCurrentPage) {
		this.badCurrentPage = badCurrentPage;
	}

	public List<MemberCommentDto> getAllComment() {
		return allComment;
	}

	public void setAllComment(List<MemberCommentDto> allComment) {
		this.allComment = allComment;
	}

	public List<MemberCommentDto> getGoodComment() {
		return goodComment;
	}

	public void setGoodComment(List<MemberCommentDto> goodComment) {
		this.goodComment = goodComment;
	}

	public List<MemberCommentDto> getMidComment() {
		return midComment;
	}

	public void setMidComment(List<MemberCommentDto> midComment) {
		this.midComment = midComment;
	}

	public List<MemberCommentDto> getBadComment() {
		return badComment;
	}

	public void setBadComment(List<MemberCommentDto> badComment) {
		this.badComment = badComment;
	}
}
