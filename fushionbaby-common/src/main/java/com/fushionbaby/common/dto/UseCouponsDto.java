package com.fushionbaby.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 张明亮
 * 订单优惠券,gotoOrder订单未下单前页面优惠dto
 */
public class UseCouponsDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long discount_id;//优惠券id
	private String sku_brand_name;//商品品牌名称
	private String sku_name;//商品名称
	private BigDecimal discount_money;//商品优惠金额;这里指一件商品优惠的金额,优惠单件商品金额
	private Integer quantity;//优惠数量
	private BigDecimal discount_money_total;//优惠金额总计
	private String is_open;//是否打开了优惠券的使用y打开了优惠券的使用,默认是n用户没有打开使用该优惠券

	public Long getDiscount_id() {
		return discount_id;
	}

	public void setDiscount_id(Long discount_id) {
		this.discount_id = discount_id;
	}

	public String getSku_brand_name() {
		return sku_brand_name;
	}

	public void setSku_brand_name(String sku_brand_name) {
		this.sku_brand_name = sku_brand_name;
	}

	public String getSku_name() {
		return sku_name;
	}

	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}

	public BigDecimal getDiscount_money() {
		return discount_money;
	}

	public void setDiscount_money(BigDecimal discount_money) {
		this.discount_money = discount_money;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getDiscount_money_total() {
		return discount_money_total;
	}

	public void setDiscount_money_total(BigDecimal discount_money_total) {
		this.discount_money_total = discount_money_total;
	}

	public String getIs_open() {
		return is_open;
	}

	public void setIs_open(String is_open) {
		this.is_open = is_open;
	}
}
