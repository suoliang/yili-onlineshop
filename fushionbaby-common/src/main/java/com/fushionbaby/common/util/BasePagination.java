/**
 * 
 */
package com.fushionbaby.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

/**
 * @author mengshaobo
 * 
 *         分页的基本类
 */
public class BasePagination implements Serializable {
	private static final long serialVersionUID = -4319028035771574084L;

	/**
	 * 默认当前页
	 */
	public static final Integer DEFAULT_PAGE = 1;

	/**
	 * 默认一页数量
	 */
	public static final Integer DEFAULT_LIMIT = 10;
	/**
	 * 默认页面显示索引长度
	 */
	public static final Integer DEFAULT_DISPLAYINDEXLEN = 5;

	/**
	 * 前台页面显示页数索引长度
	 */
	private Integer displayIndexLen = DEFAULT_DISPLAYINDEXLEN;
	/**
	 * 当前页
	 */
	private int currentPage = DEFAULT_PAGE;
	/**
	 * 一页数量
	 */
	private Integer limit = DEFAULT_LIMIT;
	/**
	 * 总数，如果页面传入了total，说明是分页跳转，那么则不需要再查询总数。
	 */
	private int total;
	/**
	 * 排序列名
	 */
	private String sort;
	/**
	 * 顺序or倒序。'ASC' or 'DESC'
	 */
	private String dir;
	/**
	 * search result
	 */
	private Collection<?> result;
	/**
	 * 传入的其他搜素参数
	 */
	private Map<String, String> params;
	/**
	 * execute elapsed time,unit is seconds.
	 */
	private Long elapsedTime;

	/**
	 * 获取总页数
	 * 
	 * @return
	 */
	public Integer getTotalPage() {
		return (total + limit - 1) / limit;
	}

	/**
	 * 获取前一页数。如果没有则返回null
	 * 
	 * @return
	 */
	public Integer getBeforePage() {
		return this.getCurrentPage() >= 1 ? this.getCurrentPage() : 1;
	}

	/**
	 * 获取下一页数。如果没有则返回null
	 * 
	 * @return
	 */
	public int getNextPage() {
		return this.getCurrentPage() <= getTotalPage() ? this.currentPage
				: getTotalPage();
	}

	/**
	 * 获取带查询参数的map对象。start：查询起始位。limit：查询多少条
	 * 
	 * @return
	 */
	public Map<String, Object> getSearchParamsMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (this.params != null) {
			map.putAll(params);
		}
		map.put("start", (this.getCurrentPage() - 1) * this.limit);

		map.put("limit", this.limit);
		return map;
	}

	/**
	 * 前台分页显示集合
	 * 
	 * @return
	 */
	public List<Integer> getDisplayIndexs() {
		// 如果总页数大于显示页数返回显示页数。否则返回总页数数量
		int count = getTotalPage() >= displayIndexLen ? displayIndexLen
				: getTotalPage();
		// 中间索引位
		int count2 = count % 2 == 0 ? count / 2 : count / 2 + 1;
		List<Integer> list = new ArrayList<Integer>();
		if ((this.getCurrentPage() + 1) <= count2) {
			for (int i = 0; i < count; i++) {
				list.add(1 + i);
			}
		} else if ((this.getCurrentPage() + 1) > this.getTotalPage()
				- (count2 - 1)) {
			for (int i = this.getTotalPage() - count + 1; i <= this
					.getTotalPage(); i++) {
				list.add(i);
			}
		} else {
			int count3 = count2 % 2 == 0 ? count2 / 2 : count2 / 2;
			for (int i = this.getCurrentPage() - count3; i <= this
					.getCurrentPage() + count2; i++) {
				list.add(i);
			}
		}
		return list;
	}

	/**
	 * 是否需要设置总数,当页面没有传入total，则为null，那么需要设置总数。
	 * 
	 * @return
	 */
	public boolean isNeedSetTotal() {
		// return total == 0 ? true : false;
		return true;
	}

	public int getCurrentPage() {
		// if (currentPage > getTotalPage()) {
		// currentPage = getTotalPage();
		// }

		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		if (currentPage < 1) {
			currentPage = 1;
		}
		// else if (currentPage > getTotalPage()) {
		// currentPage = getTotalPage();
		// }
		this.currentPage = currentPage;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		if (limit <= 0)
			limit = 1;
		this.limit = limit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setCurrentTotal(int total) {
		if (this.total != total) {
			this.currentPage = 1;
		}
		this.total = total;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public Collection<?> getResult() {
		return result;
	}

	public void setResult(Collection<?> result) {
		this.result = result;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		Map<String, String> paramsTrim = new HashMap<String, String>();
		if (!CollectionUtils.isEmpty(params)) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				if (entry.getValue() != null) {
					paramsTrim.put(entry.getKey(), entry.getValue().trim());
				}
			}
		}
		this.params = paramsTrim;
	}

	public Long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public void setDisplayIndexLen(Integer displayIndexLen) {
		this.displayIndexLen = displayIndexLen;
	}
}
