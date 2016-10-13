package com.aladingshop.alabao.model; 

import java.util.Date;

public class AlabaoBankConfig { 

    private String bankDesc;
    private Long updateId;
    private String bankName;
    private String bankIconUrl;
    private Long id;
    private Date updateTime;
    private Date createTime;
    private String bankCode;

    public String getBankDesc(){
      return bankDesc;
    }
    public void setBankDesc(String bankDesc){
      this.bankDesc = bankDesc;
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
    public String getBankIconUrl() {
		return bankIconUrl;
	}
	public void setBankIconUrl(String bankIconUrl) {
		this.bankIconUrl = bankIconUrl;
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
    public Date getCreateTime(){
      return createTime;
    }
    public void setCreateTime(Date createTime){
      this.createTime = createTime;
    }
    public String getBankCode(){
      return bankCode;
    }
    public void setBankCode(String bankCode){
      this.bankCode = bankCode;
    }

}
