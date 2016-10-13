package com.fushionbaby.common.condition;


public class QueryCondition {
	/**编号*/
	private Long id;
	/** 分页 开始索引 */
	private Integer start;
	/** 分页显示数量 */
	private Integer limit;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
