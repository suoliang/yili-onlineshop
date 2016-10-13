/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月14日下午5:35:41
 */
package com.fushionbaby.app.dto.store;

import java.util.List;

import com.fushionbaby.common.dto.order.GotoOrderLineDto;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月14日下午5:35:41
 */
public class StoreOrderDto {
	
	private StoreConfigDto storeConfig;
	
	/** 支付编号*/
	private String payOffId;
	
	/** 门店编号*/
	private String storeCode;
	
	/** 省编号*/
	private String provinceCode;
	
	/** 省名称*/
	private String provinceName;
	
	/** 城市编号*/
	private String cityCode;
	
	/** 城市名称*/
	private String cityName;
	
	/** 区县 编号*/
	private String countryCode;
	
	/** 区县名称*/
	private String countryName;
	
	/** 小区编号*/
	private String cellCode;
	
	/** 小区名称*/
	private String cellName;
	
	/** 联系人*/
	private String contactor;
	
	/** 详细地址*/
	private String address;
	
	/** 移动电话*/
	private String mobile;
	
	/** 实际总额*/
	private String totalActual;
	
	/**为订单准备数据,用户勾选的购物车行列表*/
	private List<GotoOrderLineDto> orderLineItems;
	
	
	
	

	

	public StoreConfigDto getStoreConfig() {
		return storeConfig;
	}

	public void setStoreConfig(StoreConfigDto storeConfig) {
		this.storeConfig = storeConfig;
	}

	public String getPayOffId() {
		return payOffId;
	}

	public void setPayOffId(String payOffId) {
		this.payOffId = payOffId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getContactor() {
		return contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
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

	public String getTotalActual() {
		return totalActual;
	}

	public void setTotalActual(String totalActual) {
		this.totalActual = totalActual;
	}

	public List<GotoOrderLineDto> getOrderLineItems() {
		return orderLineItems;
	}

	public void setOrderLineItems(List<GotoOrderLineDto> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getCellCode() {
		return cellCode;
	}

	public void setCellCode(String cellCode) {
		this.cellCode = cellCode;
	}

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
	
	
	
}
