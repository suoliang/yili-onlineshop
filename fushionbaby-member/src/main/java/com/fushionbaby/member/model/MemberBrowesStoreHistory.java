package com.fushionbaby.member.model;

import java.util.Date;

/***
 * 会员浏览门店记录
 * @author chenyingtao
 *
 */
public class MemberBrowesStoreHistory {

		private Long id;
		private Long memberId;
		private String memberAccount;
		private String storeCode;
		private Integer browesCount;
		private Date createTime;
		private Date updateTime;
		
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
		public String getMemberAccount() {
			return memberAccount;
		}
		public void setMemberAccount(String memberAccount) {
			this.memberAccount = memberAccount;
		}
		public String getStoreCode() {
			return storeCode;
		}
		public void setStoreCode(String storeCode) {
			this.storeCode = storeCode;
		}
		public Integer getBrowesCount() {
			return browesCount;
		}
		public void setBrowesCount(Integer browesCount) {
			this.browesCount = browesCount;
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
		
		
		
		
}
