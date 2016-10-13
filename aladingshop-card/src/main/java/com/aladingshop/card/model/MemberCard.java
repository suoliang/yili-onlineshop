package com.aladingshop.card.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xinlangtao
 * 
 */
public class MemberCard {
	/* 储值卡的id */
	private Long id;
	/* 会员的id */
	private Long memberId;
	/* 绑定的如意宝账号 */
	private String acount;
	/* 储值卡的编码 */
	private String code;
	/* 储值卡的卡号 */
	private String cardNo;
	/* 支付确认密码 */
	private String password;
	/* 卡配置 */
	private Long cardConfigId;
	/* 本金 */
	private BigDecimal totalCorpus;
	/**阿拉宝卡的总赠券 */
	private BigDecimal totalRebate;
	/** 益多宝卡（阿拉丁卡）装态*/
	private String status;
	/* 储蓄卡的创建时间 */
	private Date createTime;
	/* 更新时间 */
	private Date updateTime;
	/** 页面显示时间 */
	private String showTime;
	/** 益多宝卡类型名 页面显示 */
	private String cardName;
	/** 1、正常卡2测试卡3问题卡*/
	private String type;

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getAcount() {
		return acount;
	}

	public void setAcount(String acount) {
		this.acount = acount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo == null ? null : cardNo.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getCardConfigId() {
		return cardConfigId;
	}

	public void setCardConfigId(Long cardConfigId) {
		this.cardConfigId = cardConfigId;
	}

	public BigDecimal getTotalCorpus() {
		return totalCorpus;
	}

	public void setTotalCorpus(BigDecimal totalCorpus) {
		this.totalCorpus = totalCorpus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTotalRebate() {
		return totalRebate;
	}

	public void setTotalRebate(BigDecimal totalRebate) {
		this.totalRebate = totalRebate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}