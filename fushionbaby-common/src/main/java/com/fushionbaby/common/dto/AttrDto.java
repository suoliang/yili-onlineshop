package com.fushionbaby.common.dto;

import java.io.Serializable;

/**
 * 商品属性
 * 
 * @author mengshaobo
 * 
 */
public class AttrDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 属性名 */
	private String name;
	/** 属性值 */
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}



}
