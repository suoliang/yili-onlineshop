package com.fushionbaby.core.model.response;

import java.util.List;

import com.fushionbaby.core.vo.OrderUser;

/**
 * app根据条件查询订单列表返回对象封装
 * 
 * @author sun tao 2015年7月13日
 */
public class OrderUserRes {
	/** 订单数量 */
	private Integer total;
	/** 待付款数量 */
	private Integer toBePaidNo;
	/** 待发货数量 */
	private Integer toBeShippedNo;
	/** 待收货数量 */
	private Integer toBeReceivedNo;
	/** 待评价数量 */
	private Integer toBeEvaluatedNo;
	/** 订单数据集合 */
	private List<OrderUser> orderList;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getToBePaidNo() {
		return toBePaidNo;
	}

	public void setToBePaidNo(Integer toBePaidNo) {
		this.toBePaidNo = toBePaidNo;
	}

	public Integer getToBeShippedNo() {
		return toBeShippedNo;
	}

	public void setToBeShippedNo(Integer toBeShippedNo) {
		this.toBeShippedNo = toBeShippedNo;
	}

	public Integer getToBeReceivedNo() {
		return toBeReceivedNo;
	}

	public void setToBeReceivedNo(Integer toBeReceivedNo) {
		this.toBeReceivedNo = toBeReceivedNo;
	}

	public Integer getToBeEvaluatedNo() {
		return toBeEvaluatedNo;
	}

	public void setToBeEvaluatedNo(Integer toBeEvaluatedNo) {
		this.toBeEvaluatedNo = toBeEvaluatedNo;
	}

	public List<OrderUser> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderUser> orderList) {
		this.orderList = orderList;
	}

}
