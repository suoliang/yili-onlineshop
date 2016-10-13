package com.aladingshop.alabao.model; 

import java.util.Date;

public class AlabaoAccountBank { 

	/** 会员id*/
    private Long memberId;
    /** 是否删除*/
    private String isDelete;
    /** 银行持卡人姓名*/
    private String cardHolder;
    /** 银行名称*/
    private String bankName;
    /**自增  id*/
    private Long id;
    /** 更新时间*/
    private Date updateTime;
    /**银行卡卡号*/
    private String cardNo;
    /** 如意消费卡*/
    private String account;
    /**创建时间*/
    private Date createTime;
    /**银行支行名称*/
    private String bankBranchName;
    
    private String loginName;

    
	/**是否验证过  1 未验证 2 已验证 未通过 3 已验证 已通过*/
	private String isValidate;
	
	
    /**银行卡所在省份*/
    private String province;
    /** 银行卡所在城市*/
    private String city;
    
    
	
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
	public String getIsValidate() {
		return isValidate;
	}
	public void setIsValidate(String isValidate) {
		this.isValidate = isValidate;
	}
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	public Long getMemberId(){
      return memberId;
    }
    public void setMemberId(Long memberId){
      this.memberId = memberId;
    }
    public String getIsDelete(){
      return isDelete;
    }
    public void setIsDelete(String isDelete){
      this.isDelete = isDelete;
    }
    public String getCardHolder(){
      return cardHolder;
    }
    public void setCardHolder(String cardHolder){
      this.cardHolder = cardHolder;
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
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}
