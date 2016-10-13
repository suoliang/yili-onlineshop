package com.fushionbaby.auth.model;

/***
 * 
 * @author xupeijun
 * 
 */
public class AuthRole {
	private Long id;
	private String description;
	private String name;

	/** 页面复选框勾选标记 */

	private boolean select;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

}