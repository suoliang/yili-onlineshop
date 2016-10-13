package com.fushionbaby.config.model;

import java.math.BigDecimal;

/**
 * 
 * @author cyla
 * 
 */
public class SysmgrSfFreightConfig {
	private Long id;
	private String city;
	private String areaCode;
	private BigDecimal amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	

	@Override
	public String toString() {
		return "SysmgrSfFreight [id=" + id + ", city=" + city + ", areaCode="
				+ areaCode + ", amount=" + amount + "]";
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
