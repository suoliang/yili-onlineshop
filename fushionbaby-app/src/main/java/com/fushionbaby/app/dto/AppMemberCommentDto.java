package com.fushionbaby.app.dto;

import java.util.List;

import com.fushionbaby.common.dto.MemberCommentDto;

/**
 * 
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月20日上午11:53:34
 */
public class AppMemberCommentDto{

	/** 评论全部数量*/
	private Integer allCount;
	
	/** 好评数量*/
	private Integer goodCount;
	
	/** 中评数量*/
	private Integer secondaryCount;
	
	/** 差评数量*/
	private Integer lowestCount;
	
	/** 评论信息列表*/
	private List<MemberCommentDto> commentDtoList;
	

	public Integer getAllCount() {
		return allCount;
	}
	public void setAllCount(Integer allCount) {
		this.allCount = allCount;
	}
	
	public Integer getGoodCount() {
		return goodCount;
	}
	public void setGoodCount(Integer goodCount) {
		this.goodCount = goodCount;
	}
	
	public Integer getSecondaryCount() {
		return secondaryCount;
	}
	public void setSecondaryCount(Integer secondaryCount) {
		this.secondaryCount = secondaryCount;
	}
	public Integer getLowestCount() {
		return lowestCount;
	}
	public void setLowestCount(Integer lowestCount) {
		this.lowestCount = lowestCount;
	}
	public List<MemberCommentDto> getCommentDtoList() {
		return commentDtoList;
	}
	public void setCommentDtoList(List<MemberCommentDto> commentDtoList) {
		this.commentDtoList = commentDtoList;
	}
	
	
	
}
