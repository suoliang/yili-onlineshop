package com.aladingshop.store.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户结算列表明细
 * @author chenyingtao
 *
 *
 */
public class StoreSponsorsSettlementDetails {
		private Long id;
		private String storeCode;
		private String orderCode;
		private Long memberId;
		/**支付方式*/
		private String paymentType;
		/**支付平台交易流水号*/
		private String tradeSerialNumber;
		/**订单金额*/
		private BigDecimal settleAmount;
		/**支付平台金额,服务费*/
		private BigDecimal platformFee;
		/**商户实收金额(订单金额-支付平台费)*/
		private BigDecimal realIncomeAmount;
		
		private Date createTime;
		/**支付时间*/
		private Date paymentCompleteTime;
		/**最后一次更新时间*/
		private Date updateTime;
		/**来源1 Android，2 IOS*/
		private String sourceCode;
		/**门店后台操作人员ID*/
		private Long updateId;
		/**每日结算序列,和结算统计表对应*/
		private String dailyNumber;
		
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

		public String getPaymentType() {
			return paymentType;
		}

		public void setPaymentType(String paymentType) {
			this.paymentType = paymentType;
		}


		public String getTradeSerialNumber() {
			return tradeSerialNumber;
		}

		public void setTradeSerialNumber(String tradeSerialNumber) {
			this.tradeSerialNumber = tradeSerialNumber;
		}

		public BigDecimal getSettleAmount() {
			return settleAmount;
		}

		public void setSettleAmount(BigDecimal settleAmount) {
			this.settleAmount = settleAmount;
		}

		public BigDecimal getPlatformFee() {
			return platformFee;
		}

		public void setPlatformFee(BigDecimal platformFee) {
			this.platformFee = platformFee;
		}

		public BigDecimal getRealIncomeAmount() {
			return realIncomeAmount;
		}

		public void setRealIncomeAmount(BigDecimal realIncomeAmount) {
			this.realIncomeAmount = realIncomeAmount;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public Date getPaymentCompleteTime() {
			return paymentCompleteTime;
		}

		public void setPaymentCompleteTime(Date paymentCompleteTime) {
			this.paymentCompleteTime = paymentCompleteTime;
		}

		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

		public String getSourceCode() {
			return sourceCode;
		}

		public void setSourceCode(String sourceCode) {
			this.sourceCode = sourceCode;
		}

		public Long getUpdateId() {
			return updateId;
		}

		public void setUpdateId(Long updateId) {
			this.updateId = updateId;
		}


		public String getDailyNumber() {
			return dailyNumber;
		}

		public void setDailyNumber(String dailyNumber) {
			this.dailyNumber = dailyNumber;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}
		
		
		
		
}
