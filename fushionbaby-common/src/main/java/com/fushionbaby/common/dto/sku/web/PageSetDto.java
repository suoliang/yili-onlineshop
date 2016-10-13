/**
 * 
 */
package com.fushionbaby.common.dto.sku.web;

import java.util.List;

import com.fushionbaby.common.constants.WebConstant;

/**
 * @author mengshaobo 分页
 */
public class PageSetDto {
	
	/** 当前页数 */
	private Integer curPage = WebConstant.DEFAULT_PAGE_INDEX;
	
	/** 总页数 */
	private Integer totalPage = 0;
	
	/** 总记录数 */
	private Long amount;
	
	/** 返回结果集合*/
	private List<?>  results; 
	
	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	/**
	 * 
	 * @sse商品列表显示使用此方法添加分页数 
	 * 通过总记录获得总页数
	 * 
	 * @param amount
	 *            商品总数
	 */
	public void setTotalPageByAmount(Long amount) {
		Integer totalPage = (int) ((amount - 1) / WebConstant.SKU_LIST_SIZE + 1);
		this.totalPage = totalPage >= 0 ? totalPage : 0;
	}


	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public List<?> getResults() {
		return results;
	}

	public void setResults(List<?> results) {
		this.results = results;
	}


}
