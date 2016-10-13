/**
 * 
 */
package com.fushionbaby.common.dto.sysmgr;

import java.util.Date;

/**
 * @author mengshaobo
 *搜索关键字
 */
public class SearchKeywordsDto {
	private String ip;

	private Date date;

	private String keyword;

	private Integer count;

	private String sourceCode;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	
	
}
