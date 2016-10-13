/**
 * 
 */
package com.fushionbaby.common.dto.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * @author mengshaobo
 *为下订单页面信息准备数据
 */
public class GotoOrderDto implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 优惠券数量*/
	private String usableCouponCount;
	
	/** 支付编号*/
	private String payOffId;
	
	/**默认收货地址*/
	private Long defaultAddressId;
	
	/** 联系人*/
	private String contactor;
	
	/** 省,代码*/
	private String province;
	
	/** 市,代码*/
	private String city;
	
	/** 区、县,代码*/
	private String district;
	
	/** 省名称*/
	private String provinceName;
	
	/** 城市名称*/
	private String cityName;
	
	/** 区县名称*/
	private String districtName;
	
	/** 详细地址*/
	private String address;
	
	/** 移动电话*/
	private String mobile;
	
	/** 商品吊牌价总金额 */
	@Deprecated
	private String retailPriceTotal;
	
	/** 商品总金额,打折前的*/
	private String skuTotal;
	
	/** 订单最终总金额,打折后并且扣除快递费后的*/
	private String totalActual;
	
	/** 运费金额*/
	private String freight;
	
	/** 订单用户总积分数*/
	private BigDecimal epoints;
	
	/** 可以用的积分*/
	private BigDecimal canEpoints;
	
	/** 积分可兑换的金额*/
	private String epointsMoney;
	
	/** 购买总数量*/
	private Integer quantityTotal;
	
	/**为订单准备数据,用户勾选的购物车行列表*/
	private List<GotoOrderLineDto> orderLineItems;
	
	

	
	public String getUsableCouponCount() {
		return usableCouponCount;
	}
	public void setUsableCouponCount(String usableCouponCount) {
		this.usableCouponCount = usableCouponCount;
	}
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
	public String getRetailPriceTotal() {
		return retailPriceTotal;
	}
	public void setRetailPriceTotal(String retailPriceTotal) {
		this.retailPriceTotal = retailPriceTotal;
	}
	public String getSkuTotal() {
		return skuTotal;
	}
	public void setSkuTotal(String skuTotal) {
		this.skuTotal = skuTotal;
	}
	public String getTotalActual() {
		return totalActual;
	}
	public void setTotalActual(String totalActual) {
		this.totalActual = totalActual;
	}

	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public BigDecimal getEpoints() {
		return epoints;
	}
	public void setEpoints(BigDecimal epoints) {
		this.epoints = epoints;
	}
	public String getEpointsMoney() {
		return epointsMoney;
	}
	public void setEpointsMoney(String epointsMoney) {
		this.epointsMoney = epointsMoney;
	}
	public Integer getQuantityTotal() {
		return quantityTotal;
	}
	public void setQuantityTotal(Integer quantityTotal) {
		this.quantityTotal = quantityTotal;
	}
	
	public String getPayOffId() {
		return payOffId;
	}
	public void setPayOffId(String payOffId) {
		this.payOffId = payOffId;
	}
	public List<GotoOrderLineDto> getOrderLineItems() {
		return orderLineItems;
	}
	public void setOrderLineItems(List<GotoOrderLineDto> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public BigDecimal getCanEpoints() {
		return canEpoints;
	}
	public void setCanEpoints(BigDecimal canEpoints) {
		this.canEpoints = canEpoints;
	}
	
	
}
