package com.aladingshop.store.model;

import java.util.Date;


/**
 * 门店系统结算表
 * @author chenyingtao
 *
 *
 */
public class StoreSponsorsBank {

		private Long id;
		/**门店code*/
		private String storeCode;
		/**银行名称*/
		private String bankName;
		/**支行名称*/
		private String bankBranchName;
		/**银行所属省份*/
		private String province;
		/**银行所属市*/
		private String city;
		/**卡号*/
		private String cardNo;
		/**持卡人*/
		private String cardHolder;
		
		private Date createTime;
		private Date updateTime;
		private Long updateId;
		
		/**是否认证*/
		private String isValidate;
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
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
		public String getBankBranchName() {
			return bankBranchName;
		}
		public void setBankBranchName(String bankBranchName) {
			this.bankBranchName = bankBranchName;
		}
		public String getCardNo() {
			return cardNo;
		}
		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}
		public String getCardHolder() {
			return cardHolder;
		}
		public void setCardHolder(String cardHolder) {
			this.cardHolder = cardHolder;
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
		public String getIsValidate() {
			return isValidate;
		}
		public void setIsValidate(String isValidate) {
			this.isValidate = isValidate;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		
		
		
}
