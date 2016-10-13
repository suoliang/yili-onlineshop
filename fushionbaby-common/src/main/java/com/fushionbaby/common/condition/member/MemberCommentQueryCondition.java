package com.fushionbaby.common.condition.member;

import com.fushionbaby.common.condition.sku.BaseSkuQueryCondition;

/***
 * 会员评论查询条件
 * 
 * @author xupeijun
 *
 */
public class MemberCommentQueryCondition extends BaseSkuQueryCondition {

	private String productCode;
	
	private String memberName;

	private String disable;

	private String level;
	
	private Long memberId;
	

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
