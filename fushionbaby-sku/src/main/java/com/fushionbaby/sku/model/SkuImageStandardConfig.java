package com.fushionbaby.sku.model;
/**
 * 商品的图片尺寸规格
 * 
 * @author cyla
 * 
 */
public class SkuImageStandardConfig {
	private Long id;
	private String name;
	private String code;
	private String size;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "id=" + id + ",name=" + name + ",code=" + code + ",size=" + size;
	}
}
