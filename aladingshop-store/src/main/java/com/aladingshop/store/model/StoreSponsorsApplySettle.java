package com.aladingshop.store.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户结算列表,结算单
 * @author chenyingtao
 *
 *
 */
public class StoreSponsorsApplySettle {
	
	private Long id;
	private String storeCode;
	/**申请编号,申请编码,和结算统计表对应*/
	private String applyNumber;
	private Date applyTime;
	/**申请的总金额*/
	private BigDecimal applyTotalAmount;
	/**结算方式-银行卡*/
	private String settleMethod;
	/**结算状态1.待结算,未结算;2.结算中,申请中;3.已结算,结算完成*/
	private String settlementStatus;
	private Date createTime;
	private Date updateTime;
	/**门店后台操作人员ID*/
	private Long updateId;
	/**结算完成时间,结算完成更新此字段*/
	private Date settlementTime;
	private String remark;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getApplyNumber() {
		return applyNumber;
	}
	public void setApplyNumber(String applyNumber) {
		this.applyNumber = applyNumber;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public BigDecimal getApplyTotalAmount() {
		return applyTotalAmount;
	}
	public void setApplyTotalAmount(BigDecimal applyTotalAmount) {
		this.applyTotalAmount = applyTotalAmount;
	}
	public String getSettleMethod() {
		return settleMethod;
	}
	public void setSettleMethod(String settleMethod) {
		this.settleMethod = settleMethod;
	}
	public String getSettlementStatus() {
		return settlementStatus;
	}
	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
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
	public Date getSettlementTime() {
		return settlementTime;
	}
	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
