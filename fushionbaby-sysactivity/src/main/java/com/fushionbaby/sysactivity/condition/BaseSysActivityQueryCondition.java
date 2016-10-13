/**
 * 
 */
package com.fushionbaby.sysactivity.condition;

/**
 * @author mengshaobo 活动查询条件
 */
public class BaseSysActivityQueryCondition {
	/** 开始索引 */
	private Integer start;
	/** 显示数量 */
	private Integer limit;

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

}
