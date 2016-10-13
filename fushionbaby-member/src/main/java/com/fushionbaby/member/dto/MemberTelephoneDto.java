package com.fushionbaby.member.dto;

/***
 * 查询条件Dto
 * 
 * @author King 索亮
 * 
 */
public class MemberTelephoneDto {
	private String telephone;
	private String createTimeFrom;
	private String createTimeTo;

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

}
