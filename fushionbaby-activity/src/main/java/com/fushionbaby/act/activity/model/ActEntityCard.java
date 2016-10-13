package com.fushionbaby.act.activity.model; 

import java.math.BigDecimal;
import java.util.Date;

public class ActEntityCard { 

	/**状态(生成之后未卖出0，已卖出1，已使用2)*/
    private String status;
    /**实体卡的配置id*/
    private Long configId;
    /**充值密码*/
    private String chargePwd;
    private Long id;
    /**面额*/
    private BigDecimal faceMoney;
    /**卡号*/
    private String cardNo;
    /**编码*/
    private String code;
    private Date createTime;
    /**余额*/
    private BigDecimal surplusMoney;
    /**实体卡归属的门店编码*/
    private String storeCode;
    /**门店名称 页面展示*/
    private String storeName;
    /**类型名称 页面展示*/
	private String configName;

	
	
    public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStatus(){
      return status;
    }
    public void setStatus(String status){
      this.status = status;
    }
    public Long getConfigId(){
      return configId;
    }
    public void setConfigId(Long configId){
      this.configId = configId;
    }
    public String getChargePwd(){
      return chargePwd;
    }
    public void setChargePwd(String chargePwd){
      this.chargePwd = chargePwd;
    }
    public Long getId(){
      return id;
    }
    public void setId(Long id){
      this.id = id;
    }
    public BigDecimal getFaceMoney(){
      return faceMoney;
    }
    public void setFaceMoney(BigDecimal faceMoney){
      this.faceMoney = faceMoney;
    }
    public String getCardNo(){
      return cardNo;
    }
    public void setCardNo(String cardNo){
      this.cardNo = cardNo;
    }
    public String getCode(){
      return code;
    }
    public void setCode(String code){
      this.code = code;
    }
    public Date getCreateTime(){
      return createTime;
    }
    public void setCreateTime(Date createTime){
      this.createTime = createTime;
    }
    public BigDecimal getSurplusMoney(){
      return surplusMoney;
    }
    public void setSurplusMoney(BigDecimal surplusMoney){
      this.surplusMoney = surplusMoney;
    }
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}

}
