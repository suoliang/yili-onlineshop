package com.aladingshop.alabao.model;

import java.util.Date;

/***
 * 阿拉宝账户转账常用联系人列表
 * @author chenyingtao
 *
 */

public class AlabaoContacts {
	
	private Long id;
	private String account;
	private String linkAccount;
	private Date createTime;
	private Date updateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getLinkAccount() {
		return linkAccount;
	}
	public void setLinkAccount(String linkAccount) {
		this.linkAccount = linkAccount;
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
