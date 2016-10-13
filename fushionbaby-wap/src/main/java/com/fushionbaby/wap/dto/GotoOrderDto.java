package com.fushionbaby.wap.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 张明亮
 * 为下订单页面信息准备数据
 */
public class GotoOrderDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//默认收货地址
	private Long defaultAddressId;//默认地址id
	private String contactor;//联系人
	private String province;//省,代码
	private String city;//市,代码
	private String district;//区、县,代码
	private String address;//详细地址
	private String mobile;//移动电话
	
	private BigDecimal skuTotal;//商品总金额,打折前的
	private BigDecimal totalActual;//订单最终总金额,打折后并且扣除快递费后的
	private Double freight;//运费金额
	private Integer epoints;//要下订单的用户,可使用积分
	private BigDecimal epointsMoney;//积分可兑换的金额
	private Integer quantityTotal;//购买总数量
	private String payOffId;
	
	//为订单准备数据,用户勾选的购物车行列表
	List<GotoOrderLineDto> orderLineItems = new ArrayList<GotoOrderLineDto>();
	
	
	public Long getDefaultAddressId() {
		return defaultAddressId;
	}

	public void setDefaultAddressId(Long defaultAddressId) {
		this.defaultAddressId = defaultAddressId;
	}

	public String getContactor() {
		return contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public BigDecimal getSkuTotal() {
		return skuTotal;
	}

	public void setSkuTotal(BigDecimal skuTotal) {
		this.skuTotal = skuTotal;
	}

	public BigDecimal getTotalActual() {
		return totalActual;
	}

	public void setTotalActual(BigDecimal totalActual) {
		this.totalActual = totalActual;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public Integer getEpoints() {
		return epoints;
	}

	public void setEpoints(Integer epoints) {
		this.epoints = epoints;
	}

	public BigDecimal getEpointsMoney() {
		return epointsMoney;
	}

	public void setEpointsMoney(BigDecimal epointsMoney) {
		this.epointsMoney = epointsMoney;
	}

	public Integer getQuantityTotal() {
		return quantityTotal;
	}

	public void setQuantityTotal(Integer quantityTotal) {
		this.quantityTotal = quantityTotal;
	}

	public List<GotoOrderLineDto> getOrderLineItems() {
		return orderLineItems;
	}

	public void setOrderLineItems(List<GotoOrderLineDto> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}

	public String getPayOffId() {
		return payOffId;
	}

	public void setPayOffId(String payOffId) {
		this.payOffId = payOffId;
	}

	

	
}