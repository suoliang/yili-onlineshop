package com.aladingshop.alabao.model; 

import java.math.BigDecimal;
import java.util.Date;

public class AlabaoTurnOut { 

	/** 转出的金额*/
    private BigDecimal transferMoney;
    /**商城 会员id*/
    private Long memberId;
    /**转出银行卡的持卡人姓名*/
    private String cardHolder;
    /**更新者的id*/
    private Long updateId;
    /** 银行名称*/
    private String bankName;
    /** 自增id*/
    private Long id;
    /** 更新时间*/
    private Date updateTime;
    /** 转出的状态*/
    private String turnOutStatus;
    /** 转出银行的 卡号*/
    private String cardNo;
    /**如意消费卡账号*/
    private String account;
    /**创建时间*/
    private Date createTime;
    /** 会员名*/
    private String memberName;
    /** 银行的支行名*/
    private String bankBranchName;
    /** 流水号,转出到银行卡和转出记录有相同标识 */
    private String serialNum;
    /** 转出备注，和转出记录表冗余 */
    private String memo;

    public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	public BigDecimal getTransferMoney(){
      return transferMoney;
    }
    public void setTransferMoney(BigDecimal transferMoney){
      this.transferMoney = transferMoney;
    }
    public Long getMemberId(){
      return memberId;
    }
    public void setMemberId(Long memberId){
      this.memberId = memberId;
    }
    public String getCardHolder(){
      return cardHolder;
    }
    public void setCardHolder(String cardHolder){
      this.cardHolder = cardHolder;
    }
    public Long getUpdateId(){
      return updateId;
    }
    public void setUpdateId(Long updateId){
      this.updateId = updateId;
    }
    public String getBankName(){
      return bankName;
    }
    public void setBankName(String bankName){
      this.bankName = bankName;
    }
    public Long getId(){
      return id;
    }
    public void setId(Long id){
      this.id = id;
    }
    public Date getUpdateTime(){
      return updateTime;
    }
    public void setUpdateTime(Date updateTime){
      this.updateTime = updateTime;
    }
    public String getTurnOutStatus(){
      return turnOutStatus;
    }
    public void setTurnOutStatus(String turnOutStatus){
      this.turnOutStatus = turnOutStatus;
    }
    public String getCardNo(){
      return cardNo;
    }
    public void setCardNo(String cardNo){
      this.cardNo = cardNo;
    }
    public String getAccount(){
      return account;
    }
    public void setAccount(String account){
      this.account = account;
    }
    public Date getCreateTime(){
      return createTime;
    }
    public void setCreateTime(Date createTime){
      this.createTime = createTime;
    }
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

}
