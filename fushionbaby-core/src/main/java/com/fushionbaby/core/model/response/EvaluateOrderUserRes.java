package com.fushionbaby.core.model.response;

import java.util.List;

import com.fushionbaby.core.vo.SkuByOrderLine;

/**
 * 待评价
 * @description app根据条件查询待评价订单列表返回对象封装
 * @author 索亮
 * @date 2015年7月20日下午1:25:54
 */
public class EvaluateOrderUserRes {
	/** 待评价数量 */
	private Integer total;
	/** 商品列表 **/
	private List<SkuByOrderLine> skuByOrderLines;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<SkuByOrderLine> getSkuByOrderLines() {
		return skuByOrderLines;
	}

	public void setSkuByOrderLines(List<SkuByOrderLine> skuByOrderLines) {
		this.skuByOrderLines = skuByOrderLines;
	}

}
