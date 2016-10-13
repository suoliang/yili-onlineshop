package com.fushionbaby.core.model;

import java.math.BigDecimal;
import java.util.Date;
/***
 * 
 * @author 余浩
 *
 *
 */

public class OrderEpoint {
	/**ID号*/
	private Long id;
	/**订单编号*/
	private String orderCode;
	/**商城会员ID*/
	private Long memberId;
	/**登录名*/
	private String memberName;
	/**商品编码*/
	private String skuCode;
	/**商品名称*/
	private String skuName;
	/**尺寸*/
	private String size;
	/**颜色*/
	private String color;
	/**数量*/
	private Integer quantity;
	/**商品状态,商品状态，默认是1.待发货2.交易完成*/
	private String orderStatus;
	/**订单来源*/
	private String sourceCode;
	/**使用积分*/
	private BigDecimal	totalEpointUsed;
	/**订单备注*/
	private String memo;
	/**创建时间*/
	private Date createTime;
	/**最后一次更新时间*/
	private Date updateTime;
	/**后台操作ID*/
	private Long updateId;
	/**1.正常单，2.测试单，3.问题单*/
	private Integer orderType;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public BigDecimal getTotalEpointUsed() {
		return totalEpointUsed;
	}

	public void setTotalEpointUsed(BigDecimal totalEpointUsed) {
		this.totalEpointUsed = totalEpointUsed;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	

}
