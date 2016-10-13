package com.fushionbaby.core.model;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 订单基本表
 * @author Leon
 *
 */
public class OrderBaseUser {
	/** 订单基本id */
    private Long id;
    /** 订单编码 */
    private String orderCode;
    /** 会员id */
    private Long memberId;
    /** 会员名称 */
    private String memberName;
    /** 订单状态 */
    private String orderStatus;
    /** 订单有没有被拒绝(n没有拒绝/y用户拒绝) */
    private String isRefused;
    /** 最后收货时间（系统管理员-收货确认时间） */
    private Date lastReceiveTime;
    /** 用户取消时间 */
    private Date cancelTime;
    /** 系统自动取消时间 */
    private Date sysCancelTime;
    /** 系统取消原因 */
    private String sysCancelReason;
    /** 删除标记n未删除,y已删除 */
    private String isDelete;
    /** 订单来源 */
    private String sourceCode;
    /** 使用积分 */
    private BigDecimal totalPointUsed;
    /** 订单获得的积分 */
    private BigDecimal orderPointGained;
    /** 是否处理过积分(y是/n否) */
    private String isHandlePoint;
    /** 订单备注 */
    private String memo;
    /** 订单审核没通过原因(问题单)*/
    private String auditFailReason;
    /** 创建时间 */
    private Date createTime;
    /** 最后一次更新时间 */
    private Date updateTime;
    /** 后台操作人员ID */
    private Long updateId;
    /** 是不是拼团订单，n不是，y是拼团订单 */
    private String isGroupBuy;
    
    /** 门店系统code，系统生成，不可修改 */
    private String storeCode;
    
    /**订单是否自提标志  y自提，n否*/
    private String isPickUp;
    
    

    public String getIsPickUp() {
		return isPickUp;
	}

	public void setIsPickUp(String isPickUp) {
		this.isPickUp = isPickUp;
	}

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
        this.orderCode = orderCode == null ? null : orderCode.trim();
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
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public String getIsRefused() {
        return isRefused;
    }

    public void setIsRefused(String isRefused) {
        this.isRefused = isRefused == null ? null : isRefused.trim();
    }

    public Date getLastReceiveTime() {
        return lastReceiveTime;
    }

    public void setLastReceiveTime(Date lastReceiveTime) {
        this.lastReceiveTime = lastReceiveTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getSysCancelTime() {
        return sysCancelTime;
    }

    public void setSysCancelTime(Date sysCancelTime) {
        this.sysCancelTime = sysCancelTime;
    }

    public String getSysCancelReason() {
        return sysCancelReason;
    }

    public void setSysCancelReason(String sysCancelReason) {
        this.sysCancelReason = sysCancelReason == null ? null : sysCancelReason.trim();
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode == null ? null : sourceCode.trim();
    }

    public BigDecimal getTotalPointUsed() {
		return totalPointUsed;
	}

	public void setTotalPointUsed(BigDecimal totalPointUsed) {
		this.totalPointUsed = totalPointUsed;
	}

	public BigDecimal getOrderPointGained() {
        return orderPointGained;
    }

    public void setOrderPointGained(BigDecimal orderPointGained) {
        this.orderPointGained = orderPointGained;
    }

    public String getIsHandlePoint() {
        return isHandlePoint;
    }

    public void setIsHandlePoint(String isHandlePoint) {
        this.isHandlePoint = isHandlePoint == null ? null : isHandlePoint.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getAuditFailReason() {
        return auditFailReason;
    }

    public void setAuditFailReason(String auditFailReason) {
        this.auditFailReason = auditFailReason == null ? null : auditFailReason.trim();
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

	public String getIsGroupBuy() {
		return isGroupBuy;
	}

	public void setIsGroupBuy(String isGroupBuy) {
		this.isGroupBuy = isGroupBuy;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
    
}