package com.fushionbaby.act.activity.model; 

import java.math.BigDecimal;
import java.util.Date;

/***
 * 实体卡的交易记录（使用和充值）
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月26日上午10:21:32
 */
public class ActEntityCardUseRecord { 
	/**订单号*/
    private String orderCode;
    /** 使用来源  0实体店 cms 后台、1商城网站、2IOS、3Android*/
    private String useSource;
    /** 交易的金额*/
    private BigDecimal money;
    /** 更新的id*/
    private Long updateId;
    /** 自增id*/
    private Long id;
    /** 实体卡的卡号*/
    private String cardNo;
    /** 使用的类型  1 消费  2 充值*/
    private String useType;
    /**创建时间*/
    private Date createTime;
    /**交易流水号*/
    private String serialNo;
    /** 交易的说明备注*/
    private String memo;
    /**该消费的实体卡是属于哪个门店的*/
    private String storeCode;
	/**更新者  显示用*/
    private String updateName;
    
    /**门店名称  页面显示*/
    private String storeName;
    
    
    
    public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getOrderCode(){
      return orderCode;
    }
    public void setOrderCode(String orderCode){
      this.orderCode = orderCode;
    }
    public String getUseSource(){
      return useSource;
    }
    public void setUseSource(String useSource){
      this.useSource = useSource;
    }
    public BigDecimal getMoney(){
      return money;
    }
    public void setMoney(BigDecimal money){
      this.money = money;
    }
    public Long getUpdateId(){
      return updateId;
    }
    public void setUpdateId(Long updateId){
      this.updateId = updateId;
    }
    public Long getId(){
      return id;
    }
    public void setId(Long id){
      this.id = id;
    }
    public String getCardNo(){
      return cardNo;
    }
    public void setCardNo(String cardNo){
      this.cardNo = cardNo;
    }
    public String getUseType(){
      return useType;
    }
    public void setUseType(String useType){
      this.useType = useType;
    }
    public Date getCreateTime(){
      return createTime;
    }
    public void setCreateTime(Date createTime){
      this.createTime = createTime;
    }
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
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
	   
    public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

}
