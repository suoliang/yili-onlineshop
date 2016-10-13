package com.fushionbaby.act.activity.dto;

import java.math.BigDecimal;

/***
 * 回传给 调起app 关于实体卡部分服务接口的 数据类型
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月10日上午9:57:38
 */
public class EntityCardResDto {
	 /**实体卡余额金额*/
     private BigDecimal entityCardMoney;
     /** 实体卡的状态 1可用，2不可用*/
     private String status;
     /**实体卡密码*/
     private String entityCardPassword;
     /** 该实体卡的面值金额*/
     private BigDecimal faceMoney;

	public BigDecimal getFaceMoney() {
		return faceMoney;
	}
	public void setFaceMoney(BigDecimal faceMoney) {
		this.faceMoney = faceMoney;
	}
	public BigDecimal getEntityCardMoney() {
		return entityCardMoney;
	}
	public void setEntityCardMoney(BigDecimal entityCardMoney) {
		this.entityCardMoney = entityCardMoney;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEntityCardPassword() {
		return entityCardPassword;
	}
	public void setEntityCardPassword(String entityCardPassword) {
		this.entityCardPassword = entityCardPassword;
	}
     

     
	
}
