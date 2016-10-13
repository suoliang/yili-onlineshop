package com.aladingshop.alabao.model; 

import java.math.BigDecimal;
import java.util.Date;

public class AlabaoRolloffConfig { 

    private BigDecimal maxMoneyLimit;
    private Long updateId;
    private String rollOffCode;
    private Long id;
    private Date updateTime;
    private int maxNumberLimit;
    private Date createTime;

    public BigDecimal getMaxMoneyLimit(){
      return maxMoneyLimit;
    }
    public void setMaxMoneyLimit(BigDecimal maxMoneyLimit){
      this.maxMoneyLimit = maxMoneyLimit;
    }
    public Long getUpdateId(){
      return updateId;
    }
    public void setUpdateId(Long updateId){
      this.updateId = updateId;
    }
    public String getRollOffCode(){
      return rollOffCode;
    }
    public void setRollOffCode(String rollOffCode){
      this.rollOffCode = rollOffCode;
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
    public int getMaxNumberLimit(){
      return maxNumberLimit;
    }
    public void setMaxNumberLimit(int maxNumberLimit){
      this.maxNumberLimit = maxNumberLimit;
    }
    public Date getCreateTime(){
      return createTime;
    }
    public void setCreateTime(Date createTime){
      this.createTime = createTime;
    }

}
