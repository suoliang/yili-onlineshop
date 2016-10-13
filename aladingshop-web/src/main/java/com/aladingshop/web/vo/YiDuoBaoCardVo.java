/**
 * 
 */
package com.aladingshop.web.vo;

import java.util.List;

import com.aladingshop.card.model.MemberCard;

/**
 * @description 益多宝对应客户列表
 * @author 孙涛
 * @date 2015年9月9日下午2:24:21
 */
public class YiDuoBaoCardVo {
	public YiDuoBaoCardVo(List<MemberCard> inCards, List<MemberCard> reCards) {
		this.interestCards = inCards;
		this.rebateCards = reCards;
	}

	/** 收益可用卡列表 */
	private List<MemberCard> interestCards;

	/** 赠券可用卡列表 */
	private List<MemberCard> rebateCards;

	public List<MemberCard> getInterestCards() {
		return interestCards;
	}

	public void setInterestCards(List<MemberCard> interestCards) {
		this.interestCards = interestCards;
	}

	public List<MemberCard> getRebateCards() {
		return rebateCards;
	}

	public void setRebateCards(List<MemberCard> rebateCards) {
		this.rebateCards = rebateCards;
	}

}
