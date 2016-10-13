package com.aladingshop.card.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fushionbaby.common.util.NumberFormatUtil;

/**
 * @author xinlangtao
 * 
 */
public class MemberCardConfig {
	/** 自增id */
	private Long id;
	/** 卡类型，1代表季卡，2代表半年卡，3代表年卡 */
	private String type;
	/** 该卡的最低起充金额 */
	private BigDecimal lowestMoney;
	/** 时间期限：单位为月，即多少月之内该卡本金不可使用，只可以只用赠券和利息 */
	private Integer timeLine;
	/** 是否禁用（n代表不禁用，y代表禁用） */
	private String isDisabled;
	/** 图片地址 */
	private String imgUrl;
	/** 图文详情地址 */
	private String detailUrl;
	/** 话述 */
	private String remark;
	/** 创建时间 */
	private Date createTime;
	/** 创建者id */
	private Long createId;
	/** 更新时间 */
	private Date updateTime;
	/** 更新者id */
	private Long updateId;
	/** 赠券的利率 */
	private BigDecimal rebate;

	/** 页面显示 */
	private String typeName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public BigDecimal getLowestMoney() {
		return new BigDecimal(NumberFormatUtil.numberFormat(this.lowestMoney));
	}

	public void setLowestMoney(BigDecimal lowestMoney) {
		this.lowestMoney = lowestMoney;
	}

	public Integer getTimeLine() {
		return timeLine;
	}

	public void setTimeLine(Integer timeLine) {
		this.timeLine = timeLine;
	}

	public String getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(String isDisabled) {
		this.isDisabled = isDisabled == null ? null : isDisabled.trim();
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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

	public BigDecimal getRebate() {
		return rebate;
	}

	public void setRebate(BigDecimal rebate) {
		this.rebate = rebate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}