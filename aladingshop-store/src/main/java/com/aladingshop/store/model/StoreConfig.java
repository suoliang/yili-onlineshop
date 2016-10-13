package com.aladingshop.store.model;


/**
 * 门店配置表
 * @author chenyingtao
 *
 *
 */
public class StoreConfig {
	
	private Long id;
	
	/**门店code*/
	private String storeCode;
	
	/**营业开始时间*/
	private  String businessStartTime;
	
	/**营业结束时间*/
	private  String businessEndTime;
	
	/**低于免运费的标准收取的运费*/
	private  Integer freightAmount;
	
	/**免运费的标准*/
	private  Integer freeFreightAmount;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getBusinessStartTime() {
		return businessStartTime;
	}
	public void setBusinessStartTime(String businessStartTime) {
		this.businessStartTime = businessStartTime;
	}
	public String getBusinessEndTime() {
		return businessEndTime;
	}
	public void setBusinessEndTime(String businessEndTime) {
		this.businessEndTime = businessEndTime;
	}
	public Integer getFreightAmount() {
		return freightAmount;
	}
	public void setFreightAmount(Integer freightAmount) {
		this.freightAmount = freightAmount;
	}
	public Integer getFreeFreightAmount() {
		return freeFreightAmount;
	}
	public void setFreeFreightAmount(Integer freeFreightAmount) {
		this.freeFreightAmount = freeFreightAmount;
	}
	
	
	
	
}
