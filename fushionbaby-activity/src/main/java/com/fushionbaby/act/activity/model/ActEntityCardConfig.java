package com.fushionbaby.act.activity.model; 

import java.math.BigDecimal;
import java.util.Date;

public class ActEntityCardConfig { 

    private String name;
    private String isDisabled;
    private Date expiration;
    private Date beginTime;
    private Long id;
    private BigDecimal sellMoney;
    private BigDecimal faceMoney;
    private Date createTime;

    public String getName(){
      return name;
    }
    public void setName(String name){
      this.name = name;
    }
    public String getIsDisabled(){
      return isDisabled;
    }
    public void setIsDisabled(String isDisabled){
      this.isDisabled = isDisabled;
    }
    public Date getExpiration(){
      return expiration;
    }
    public void setExpiration(Date expiration){
      this.expiration = expiration;
    }
    public Date getBeginTime(){
      return beginTime;
    }
    public void setBeginTime(Date beginTime){
      this.beginTime = beginTime;
    }
    public Long getId(){
      return id;
    }
    public void setId(Long id){
      this.id = id;
    }
    public BigDecimal getSellMoney(){
      return sellMoney;
    }
    public void setSellMoney(BigDecimal sellMoney){
      this.sellMoney = sellMoney;
    }
    public BigDecimal getFaceMoney(){
      return faceMoney;
    }
    public void setFaceMoney(BigDecimal faceMoney){
      this.faceMoney = faceMoney;
    }
    public Date getCreateTime(){
      return createTime;
    }
    public void setCreateTime(Date createTime){
      this.createTime = createTime;
    }

}
