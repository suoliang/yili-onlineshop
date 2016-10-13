/**
 * 
 */
package com.fushionbaby.common.dto.sku.web;

/**
 * @author mengshaobo
 * 
 */
public class SortPageSetDto extends PageSetDto {
	
	/** 排序类型 （升序，降序） */
	private String sortType;
	
	/** 排序参数 */
	private String sortParam;
	
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getSortParam() {
		return sortParam;
	}
	public void setSortParam(String sortParam) {
		this.sortParam = sortParam;
	}



}
