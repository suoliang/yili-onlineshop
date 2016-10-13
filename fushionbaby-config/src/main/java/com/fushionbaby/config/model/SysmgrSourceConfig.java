package com.fushionbaby.config.model;

/**
 * 
 * @author King suoliang
 * 
 */
public class SysmgrSourceConfig {
	private Long id;

	private String name;

	private String code;

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
		this.name = name == null ? null : name.trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}