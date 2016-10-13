package com.fushionbaby.common.dto.areaconfig;

import java.io.Serializable;
import java.util.List;
/***
 * @description 省市区DTO
 * @author 索亮
 * @date 2015年12月25日下午3:45:56
 */
public class AreaAllJsonDto implements Serializable{

	private static final long serialVersionUID = -4430143032349944372L;
	/** 省市区code */
	private String id;
	/** 名称 */
	private String name;
	/** 子集 */
	private List<AreaAllJsonDto> child;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<AreaAllJsonDto> getChild() {
		return child;
	}
	
	public void setChild(List<AreaAllJsonDto> child) {
		this.child = child;
	}
	
}
