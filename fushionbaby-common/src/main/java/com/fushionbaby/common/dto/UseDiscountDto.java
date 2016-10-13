package com.fushionbaby.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 张明亮
 * 选择优惠折扣dto,未来可能会有其他的优惠折扣,所以建立了这个专门存放折扣数据的总dto
 * 购物票卷封装dto用于为下订单准备数据的gotoOrder页面中使用
 */
public class UseDiscountDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<UseCouponsDto> use_coupon_list;//优惠卷列表
	private UseEpointsDto epoints;//使用积分对象
	private BigDecimal order_total;//订单总金额;订单总计变化
	private Double freight;//运费金额,运费变化
	
	public List<UseCouponsDto> getUse_coupon_list() {
		return use_coupon_list;
	}
	public void setUse_coupon_list(List<UseCouponsDto> use_coupon_list) {
		this.use_coupon_list = use_coupon_list;
	}
	public UseEpointsDto getEpoints() {
		return epoints;
	}
	public void setEpoints(UseEpointsDto epoints) {
		this.epoints = epoints;
	}
	public BigDecimal getOrder_total() {
		return order_total;
	}
	public void setOrder_total(BigDecimal order_total) {
		this.order_total = order_total;
	}
	public Double getFreight() {
		return freight;
	}
	public void setFreight(Double freight) {
		this.freight = freight;
	}
}
