package com.fushionbaby.order.model;

import java.util.Date;

public class OrderTrace {
    private Long id;

    private Long memberId;

    private String orderCode;

    private String orderStatus;

    private String orderStatusDes;

    private Date createTime;
    
    /** 门店系统code，系统生成，不可修改 */
    private String storeCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public String getOrderStatusDes() {
        return orderStatusDes;
    }

    public void setOrderStatusDes(String orderStatusDes) {
        this.orderStatusDes = orderStatusDes == null ? null : orderStatusDes.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
}