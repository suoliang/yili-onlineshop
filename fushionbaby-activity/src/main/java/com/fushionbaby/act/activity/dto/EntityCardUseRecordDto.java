package com.fushionbaby.act.activity.dto;
/***
 * 打印小票的dto
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年12月16日上午9:24:56
 */

public class EntityCardUseRecordDto {
    /** 交易的金额*/
    private String money;
    /** 实体卡的卡号*/
    private String cardNo;
    /**创建时间*/
    private String createTime;
    /**交易流水号*/
    private String serialNo;
    /** 交易的说明备注*/
    private String memo;
    /**卡的余额*/
    private String 	balance;
    
    
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	

     
	
}
