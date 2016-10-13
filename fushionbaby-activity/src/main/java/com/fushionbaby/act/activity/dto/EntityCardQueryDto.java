package com.fushionbaby.act.activity.dto;



/***
 * 实体卡相关查询条件
 * @description 类描述...
 * @author 徐培峻
 * @date 2016年2月19日下午2:49:44
 */
public class EntityCardQueryDto {

	/**实体卡面额*/
	private  String faceMoney;
	/**状态*/
	private String status;
	/**卡号*/
	private String cardNo;
	/**卡配置（类型名称）*/
	private String configId;
	/**所属门店编码*/
	private String storeCode;
	
	/** 0 代表消费  1 代表充值 */
	private String useType;
	/**0 实体店  1 商城  2 ios 3 安卓*/
	private String useSource;
	
	
	
	
	
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public String getUseSource() {
		return useSource;
	}
	public void setUseSource(String useSource) {
		this.useSource = useSource;
	}
	public String getFaceMoney() {
		return faceMoney;
	}
	public void setFaceMoney(String faceMoney) {
		this.faceMoney = faceMoney;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	
	
}
