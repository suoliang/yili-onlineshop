package com.fushionbaby.order.model;

import java.util.Date;
/**
 * 订单物流信息表
 * @author Leon
 *
 */
public class OrderTrans {
	/** 订单物流信息id */
    private Long id;
    /** 会员id */
    private Long memberId;
    /** 订单编码 */
    private String orderCode;
    /** 物流状态 */
    private String transStatus;
    /** 物流单号 */
    private String transCode;
    /** 物流公司名称 */
    private String transName;
    /** 卖家发货时间 */
    private Date deliveryTime;
    /** 送货时段 */
    private String sendDate;
    /** 创建时间 */
    private Date createTime;
    /** 最后一次更新时间 */
    private Date updateTime;
    /** 后台操作人员id */
    private Long updateId;

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

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus == null ? null : transStatus.trim();
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode == null ? null : transCode.trim();
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName == null ? null : transName.trim();
    }

    public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate == null ? null : sendDate.trim();
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

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
}