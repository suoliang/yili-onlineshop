package com.aladingshop.alabao.model; 

import java.math.BigDecimal;
import java.util.Date;

public class AlabaoRollOffRecord { 

    private BigDecimal transferMoney;
    private Long memberId;
    private String rollOffAccountType;
    private Long id;
    private String account;
    private Date createTime;
    private String isSuccess;
    private String serialNum;
    private String batchNum;
    private String memo;
    
    
	
	/**本次操作前的金额*/
	private BigDecimal beforeChangeMoney;
	/**本次操作后的金额*/
	private BigDecimal afterChangeMoney;
	
	
	
	
	public BigDecimal getBeforeChangeMoney() {
		return beforeChangeMoney;
	}
	public void setBeforeChangeMoney(BigDecimal beforeChangeMoney) {
		this.beforeChangeMoney = beforeChangeMoney;
	}
	public BigDecimal getAfterChangeMoney() {
		return afterChangeMoney;
	}
	public void setAfterChangeMoney(BigDecimal afterChangeMoney) {
		this.afterChangeMoney = afterChangeMoney;
	}

    public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
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
    public String getRollOffAccountType(){
      return rollOffAccountType;
    }
    public void setRollOffAccountType(String rollOffAccountType){
      this.rollOffAccountType = rollOffAccountType;
    }
    public Long getId(){
      return id;
    }
    public void setId(Long id){
      this.id = id;
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
    public String getIsSuccess(){
      return isSuccess;
    }
    public void setIsSuccess(String isSuccess){
      this.isSuccess = isSuccess;
    }
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

}
