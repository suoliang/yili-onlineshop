/**
 * 
 */
package com.fushionbaby.common.enums;

/**
 * @author mengshaobo 商品评论状态
 */
public enum MemberCommentStatusEnum {

	/** 审核通过 */
	PASS("y"),
	/** 不通过 */
	NO_PASS("n");

	private String vlue;

	private MemberCommentStatusEnum(String vlue) {
		this.vlue = vlue;
	}

	public String getVlue() {
		return vlue;
	}

}
