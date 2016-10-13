package com.fushionbaby.core.model.request;

/**
 * 所有APP请求基类
 * 
 * @author sun tao 2015年7月13日
 */
public class RequestBase {
	/** 跳转页 **/
	private Integer pageIndex = 1;
	/** 展示数量 默认10条 **/
	private Integer pageSize = 10;
	/** 分页开始 **/
	private Integer start;
	/** 分页结束 **/
	private Integer limit;
	/** 结果数据总数 **/
	private Integer total;

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
