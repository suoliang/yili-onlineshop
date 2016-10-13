package com.fushionbaby.cms.dto;

/**
 * 
 * @author chenyingtao
 * @description 门店系统查询条件
 * 
 */
public class SysmgrStoreApplyDto {
	
	/**根据用户名查询*/
	private String name;
	/**根据电话号码查询*/
	private String phone;
	/**根据城市查询*/
	private String city;
	
	/**是否处理*/
	private String isDeal;
	
	/**根据创建时间起点查询*/
	private String createTimeFrom;
	/**根据创建时间截止点查询*/
	private String createTimeTo;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCreateTimeFrom() {
		return createTimeFrom;
	}
	public void setCreateTimeFrom(String createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}
	public String getCreateTimeTo() {
		return createTimeTo;
	}
	public void setCreateTimeTo(String createTimeTo) {
		this.createTimeTo = createTimeTo;
	}
	public String getIsDeal() {
		return isDeal;
	}
	public void setIsDeal(String isDeal) {
		this.isDeal = isDeal;
	}
	
	
}
