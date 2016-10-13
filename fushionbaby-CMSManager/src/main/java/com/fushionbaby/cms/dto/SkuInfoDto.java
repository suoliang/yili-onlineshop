package com.fushionbaby.cms.dto;

import java.math.BigDecimal;

public class SkuInfoDto {
	private String code;
	private String name;
	private String barCode;
	private String color;
	private String size;
	private String categoryName;
	private String brandName;
	private BigDecimal costPric;
	private BigDecimal currentPrice;
	private BigDecimal aladingPric;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public BigDecimal getCostPric() {
		return costPric;
	}
	public void setCostPric(BigDecimal costPric) {
		this.costPric = costPric;
	}
	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}
	public BigDecimal getAladingPric() {
		return aladingPric;
	}
	public void setAladingPric(BigDecimal aladingPric) {
		this.aladingPric = aladingPric;
	}
	
}
