package com.aladingshop.alabao.model;

import java.math.BigDecimal;
import java.util.Date;

/***
 * @description 如意宝账户转出限制
 * @author 索亮
 * @date 2015年11月16日下午2:13:22
 */
public class AlabaoLimit {
	/**转出限制id*/
	private Long id;
	/**账户*/
	private String account;
	/**用户单次转出限额*/
	private BigDecimal moneyLimit;
	/**用户每天转出次数限制*/
	private Integer numberLimit;
	/**用户的如意宝账号等级*/
	private String accountLevel;
	/**描述*/
    private String remark;
    /**创建时间*/
    private Date createTime;
    /**修改时间*/
    private Date updateTime;
    /**最后一次操作账号id*/
    private Long createId;
    
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
	public BigDecimal getMoneyLimit() {
		return moneyLimit;
	}
	public void setMoneyLimit(BigDecimal moneyLimit) {
		this.moneyLimit = moneyLimit;
	}
	public Integer getNumberLimit() {
		return numberLimit;
	}
	public void setNumberLimit(Integer numberLimit) {
		this.numberLimit = numberLimit;
	}
	public String getAccountLevel() {
		return accountLevel;
	}
	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
    
}
