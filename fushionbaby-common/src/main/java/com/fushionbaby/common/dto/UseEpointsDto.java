package com.fushionbaby.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 张明亮
 * 订单页面封装会员使用积分情况dto
 */
public class UseEpointsDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer epoints;//要下订单的用户,可使用积分
	private BigDecimal epoints_money;//积分可以兑换的金额
	public Integer getEpoints() {
		return epoints;
	}
	public void setEpoints(Integer epoints) {
		this.epoints = epoints;
	}
	public BigDecimal getEpoints_money() {
		return epoints_money;
	}
	public void setEpoints_money(BigDecimal epoints_money) {
		this.epoints_money = epoints_money;
	}
}
